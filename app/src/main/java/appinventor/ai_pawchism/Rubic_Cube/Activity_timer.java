package appinventor.ai_pawchism.Rubic_Cube;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
            //int secs = (int) (timeInMilis/1000);
            //int mins = secs/60;
            //secs%=60;
            //int milliseconds = (int) (timeInMilis%1000);
            int mins = (int) timeInMilis / 60000;
            int secs = (int) timeInMilis % 60000 / 1000;
            int milliseconds = (int) timeInMilis % 60000 / 10;
            String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d:%02d", mins, secs, milliseconds%100);
            textTimer.setText(timeFormatted);
            customHandler.postDelayed(this, 0);
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
            String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d:%02d", mins, secs, milliseconds);
            textTimer.setText(timeFormatted);
            textTimer.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.timer_text_ready)));

        }
    };

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        PublisherAdView publisherAdView = (PublisherAdView) findViewById(R.id.publisherAdView);
        PublisherAdRequest adRequest = new PublisherAdRequest.Builder().build();
        publisherAdView.loadAd(adRequest);

        LinearLayout toInflateUpBar = (LinearLayout) findViewById(R.id.frame_up_bar_timer);
        getLayoutInflater().inflate(R.layout.fragment_up_bar, toInflateUpBar);

        ImageView backButton = (ImageView) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
        ImageView settings = (ImageView) findViewById(R.id.settings_imageview);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Settings.class));
            }
        });


        //DB****************************************************************
        final ListView listView = (ListView) findViewById(R.id.listViewMain);
        final Timer_DataBaseHelper db = new Timer_DataBaseHelper(this);
        //db.deleteDataFromDB();
        final ArrayList<Timer_Score> timerArrayList = new ArrayList<>();
        final Timer_ScoreAdapter adapter = new Timer_ScoreAdapter(Activity_timer.this, R.layout.layout_adapter_list_timer, timerArrayList);

        //final ConstraintLayout timerButton = (ConstraintLayout) findViewById(R.id.constraint_timer_field);
        final View timerButton = (View) findViewById(R.id.timer_click_area);
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

                            customHandler.removeCallbacks(updateTimerThread);
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                            String strDate = sdf.format(new Date());
                            db.addData(textTimer.getText().toString(),strDate, "3x3");
                            textTimer.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.timer_text_stop)));
                            //adapter.notifyDataSetChanged();
                            //listView.invalidateViews();
                            //listView.refreshDrawableState();
                            return false;
                        }
                    case  MotionEvent.ACTION_MOVE:
                        return false;
                    case MotionEvent.ACTION_UP:
                        if (timerStatus==1) {
                            startTime = SystemClock.uptimeMillis();
                            customHandler.postDelayed(updateTimerThread, 0);

                            timerStatus=2;
                            return true;
                        }
                        if (timerStatus==2){
                            updateDataFromDB(timerArrayList);
                            listView.invalidateViews();
                            listView.refreshDrawableState();
                            timerStatus=3;
                            break;
                        }
                }
                return false;
            }
        });

        timerButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (timerStatus==0){
                    textTimer.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.timer_text_ready)));
                    timerStatus=1;
                }
                if (timerStatus==3){
                    customHandler.postDelayed(resetTimer, 0);
                    timerStatus=1;
                }
                return true;
            }
        });
        showDataFromDB(db, timerArrayList);
        listView.setDivider(null); //makes horizontal lines invisible
        listView.setAdapter(adapter);
    }


    public void showDataFromDB(Timer_DataBaseHelper db, ArrayList<Timer_Score> timerArrayList){
        Cursor cursor = db.getDataFromDb();
        if (cursor.getCount()>0){
            cursor.moveToLast();
            while (cursor.moveToPrevious()) {
                String id = (cursor.getString(0) + "\n");
                String time = (cursor.getString(1) + "\n");
                String date = (cursor.getString(2) + "\n");
                String cube = (cursor.getString(3) + "\n");

                Timer_Score timerItem = new Timer_Score(id, time,date,cube);
                timerArrayList.add(timerItem);
            }
        } else {
            String id = "1";
            String time = "0";
            String date = "DATE: ";
            String cube = "CUBE: ";

            Timer_Score timerItem = new Timer_Score(id, time,date, cube);
            timerArrayList.add(timerItem);
        }
    }

    public void updateDataFromDB(ArrayList<Timer_Score> timerArrayList){
        timerArrayList.clear();
        Timer_DataBaseHelper db1 = new Timer_DataBaseHelper(this);
        Cursor cursor = db1.getDataFromDb();

        if (cursor.getCount()>0){
            cursor.moveToLast();
            do {
                String id = (cursor.getString(0) + "\n");
                String time = (cursor.getString(1) + "\n");
                String date = (cursor.getString(2) + "\n");
                String cube = (cursor.getString(3) + "\n");

                Timer_Score timerItem = new Timer_Score(id, time,date,cube);
                timerArrayList.add(timerItem);
            } while (cursor.moveToPrevious());
        }
    }
}
