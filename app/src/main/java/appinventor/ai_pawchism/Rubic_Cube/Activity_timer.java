package appinventor.ai_pawchism.Rubic_Cube;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import java.util.Locale;

public class Activity_timer extends AppCompatActivity {
    String startedLanguage;
    TextView textTimer;
    long startTime = 0L;
    long timeInMilis = 0L;
    long updateTime = 0L;
    long timeSwapBuf = 0L;
    String testTextActionDown = "test action down";
    String testTextActionUp = "test action UP";
    String testTextOnClick = "test on click";
    Handler customHandler = new Handler();
    int timerStatus = 0;

    Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {
            timeInMilis = SystemClock.uptimeMillis()-startTime;
            updateTime = timeSwapBuf+timeInMilis;
            int secs = (int) (updateTime/1000);
            int mins = secs/60;
            secs%=60;
            int milliseconds = (int) (updateTime%1000);
            textTimer.setText(""+mins+":"+String.format("%2d",secs)+":"+String.format("%3d",milliseconds));
            customHandler.postDelayed(this, 0);
        }
    };

    Runnable startCountingTime = new Runnable(){
        @Override
        public void run(){
            textTimer.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.timer_text_ready)));
        }
    };
    Runnable stopCountingTime = new Runnable() {
        @Override
        public void run() {
            Toast.makeText(getApplicationContext(), "Stop timer ", Toast.LENGTH_LONG).show();
            /*customHandler.removeCallbacks(startCountingTime);
            int secs = (int) (updateTime/1000);
            int mins = secs/60;
            secs%=60;
            int milliseconds = (int) (updateTime%1000);
            textTimer.setText(""+mins+":"+String.format("%2d",secs)+":"+String.format("%3d",milliseconds));
            */
        }
    };

    Runnable resetTimer = new Runnable() {
        @Override
        public void run() {
            Toast.makeText(getApplicationContext(), "Action Up 1 ", Toast.LENGTH_LONG).show();
        }
    };

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        loadLocale();
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        startedLanguage = prefs.getString("My_Lang",""); //read the language in which the activity was created
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        PublisherAdView publisherAdView = (PublisherAdView) findViewById(R.id.publisherAdView);
        PublisherAdRequest adRequest = new PublisherAdRequest.Builder().build();
        publisherAdView.loadAd(adRequest);

        LinearLayout toInflateUpBar = (LinearLayout) findViewById(R.id.frame_up_bar_timer);
        getLayoutInflater().inflate(R.layout.fragment_up_bar, toInflateUpBar);

        final LinearLayout timerButton = (LinearLayout) findViewById(R.id.timer_field);
        textTimer = (TextView) findViewById(R.id.text_timer);


        timerButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        if (timerStatus==0) {
                            textTimer.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.timer_text_praparing)));
                            customHandler.postDelayed(startCountingTime, 500);
                        }
                        if (timerStatus==1){
                            //customHandler.postDelayed(stopCountingTime, 0);
                            customHandler.removeCallbacks(updateTimerThread);
                        }
                        return true;

                    case  MotionEvent.ACTION_MOVE:
                        return false;
                        //textTimer.setTextColor(getResources().getColor(android.R.color.holo_green_light));

                    case MotionEvent.ACTION_UP:
                        if (timerStatus==0) {
                            startTime = SystemClock.uptimeMillis();
                            customHandler.postDelayed(updateTimerThread, 0);
                            textTimer.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.timer_text_ongoing)));
                            Toast.makeText(getApplicationContext(), "Action Up 0 ", Toast.LENGTH_LONG).show();
                            timerStatus=1;
                        }
                        if (timerStatus==1){
                            customHandler.postDelayed(resetTimer,500);
                        }
                        return true;
                        //textTimer.setText(testTextActionUp);
                }
                return false;
            }
        });


    }

    public void loadLocale(){
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_Lang","");
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration configuration = getBaseContext().getResources().getConfiguration();
        configuration.setLocale(locale);
        getBaseContext().getResources().updateConfiguration(configuration,getBaseContext().getResources().getDisplayMetrics());
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_Lang","");
        if(!language.equals(startedLanguage)){ //check weather language is changed
            recreate();
            startedLanguage = language;
        }
    }
}
