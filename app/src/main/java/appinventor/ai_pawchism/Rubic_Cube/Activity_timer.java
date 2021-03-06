package appinventor.ai_pawchism.Rubic_Cube;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Activity_timer extends AppCompatActivity {
    String startedLanguage;
    TextView textTimer;
    long startTime = 0L;
    long timeInMilis = 0L;
    Handler customHandler = new Handler();
    int timerStatus = 0;

    Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {
            timeInMilis = SystemClock.uptimeMillis()-startTime;
            int secs = (int) (timeInMilis/1000);
            int mins = secs/60;
            secs%=60;
            int milliseconds = (int) (timeInMilis%1000);
            String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d:%02d", mins, secs, milliseconds);
            textTimer.setText(timeFormatted);
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
            timeInMilis = 0L;
            int secs = (int) (timeInMilis/1000);
            int mins = secs/60;
            secs%=60;
            int milliseconds = (int) (timeInMilis%1000);
            String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d:%03d", mins, secs, milliseconds);
            textTimer.setText(timeFormatted);
            textTimer.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.timer_text_stop)));

        }
    };

    Runnable longPress = new Runnable() {
        @Override
        public void run() {

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

        ListView listView = (ListView) findViewById(R.id.listViewMain);
        final Timer_DataBaseHelper db = new Timer_DataBaseHelper(this);
        final ArrayList<Timer_Score> timerArrayList = new ArrayList<>();
        final Timer_ScoreAdapter adapter = new Timer_ScoreAdapter(Activity_timer.this, R.layout.layout_adapter_list_timer, timerArrayList);

        Cursor cursor = db.getDataFromDb();
        if (cursor.getCount()>0){
            while (cursor.moveToNext()) {
                String id = (cursor.getString(0) + "\n");
                String time = ( cursor.getString(1) + "\n");
                String date = ( cursor.getString(2) + "\n");
                String cube = ("CUBE: " + cursor.getString(3) + "\n");

                Timer_Score timerItem = new Timer_Score(id, time, date, cube);
                timerArrayList.add(timerItem);
            }
        } else {
            String id = "1";
            String time = "0";
            String date = "DATE: ";
            String cube = "CUBE: ";

            Timer_Score timerItem = new Timer_Score(id, time, date, cube);
            timerArrayList.add(timerItem);
        }

        LinearLayout toInflateUpBar = (LinearLayout) findViewById(R.id.frame_up_bar_timer);
        getLayoutInflater().inflate(R.layout.fragment_up_bar, toInflateUpBar);

        //final LinearLayout timerButton = (LinearLayout) findViewById(R.id.timer_field);
        final ConstraintLayout timerButton = (ConstraintLayout) findViewById(R.id.constraint_timer_field);
        textTimer = (TextView) findViewById(R.id.text_timer);
        textTimer.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.timer_text_stop)));


        timerButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        if (timerStatus==0) {
                            return false;
                        }
                        if (timerStatus==2){
                            //customHandler.postDelayed(stopCountingTime, 0);
                            customHandler.removeCallbacks(updateTimerThread);

                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                            String strDate = sdf.format(new Date());
                            db.addData(textTimer.getText().toString(), strDate, "3x3x3");
                            Toast.makeText(getApplicationContext(),textTimer.getText().toString(), Toast.LENGTH_LONG).show();
                            return false;
                        }


                    case  MotionEvent.ACTION_MOVE:
                        return false;

                    case MotionEvent.ACTION_UP:
                        if (timerStatus==1) {
                            startTime = SystemClock.uptimeMillis();
                            customHandler.postDelayed(updateTimerThread, 0);
                            //textTimer.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.timer_text_ongoing)));
                            timerStatus=2;
                            return true;
                        }

                }
                return false;
            }
        });

        timerButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (timerStatus==0){
                    //Toast.makeText(getApplicationContext(), "OnLongClick 0", Toast.LENGTH_LONG).show();
                    customHandler.postDelayed(startCountingTime, 0);
                    timerStatus=1;
                }
                if (timerStatus==2){
                    customHandler.postDelayed(resetTimer, 0);
                    timerStatus=1;
                }

                return true;
            }
        });

        listView.setAdapter(adapter);
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
