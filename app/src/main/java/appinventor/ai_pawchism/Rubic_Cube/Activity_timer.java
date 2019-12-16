package appinventor.ai_pawchism.Rubic_Cube;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Activity_timer extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String startedLanguage;
    TextView textTimer;
    long startTime = 0L;
    long timeInMilis = 0L;
    Handler customHandler = new Handler();
    int timerStatus = 0;
    String cubeType = "3x3";
    TextView bestScore, worstScore, averageFrom5, averageFrom10, averageFrom20, averageFrom50;

    Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {
            timeInMilis = SystemClock.uptimeMillis() - startTime;
            int mins = (int) timeInMilis / 60000;
            int secs = (int) timeInMilis % 60000 / 1000;
            int milliseconds = (int) timeInMilis % 60000 / 10;
            String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d:%02d", mins, secs, milliseconds % 100);
            textTimer.setText(timeFormatted);
            customHandler.postDelayed(this, 0);
        }
    };

    Runnable resetTimer = new Runnable() {
        @Override
        public void run() {
            timeInMilis = 0L;
            int secs = (int) (timeInMilis / 1000);
            int mins = secs / 60;
            secs %= 60;
            int milliseconds = (int) (timeInMilis % 1000);
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
                startActivity(new Intent(getApplicationContext(), Activity_StartPage.class));
            }
        });
        ImageView settings = (ImageView) findViewById(R.id.settings_imageview);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Activity_Settings.class));
            }
        });

        Button showScores = (Button) findViewById(R.id.show_scores_button);
        showScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Activity_show_timer_list.class));
            }
        });

        //Scores displayed in the bottom of screen
        bestScore = (TextView) findViewById(R.id.best_score);
        worstScore = (TextView) findViewById(R.id.worst_score);
        averageFrom5 = (TextView) findViewById(R.id.average5_score);
        averageFrom10 = (TextView) findViewById(R.id.average10_score);
        averageFrom20 = (TextView) findViewById(R.id.average20_score);
        averageFrom50 = (TextView) findViewById(R.id.average50_score);

        //Spinner - choose cube type
        Spinner spinner = findViewById(R.id.timer_spinner);
        ArrayAdapter<CharSequence> adapterSpinner = ArrayAdapter.createFromResource(this, R.array.spinner_choose_cube, android.R.layout.simple_spinner_item);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterSpinner);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(this);

        //DB****************************************************************
        //ImageView deleteItem = (ImageView) findViewById(R.id.timer_delete_item);
        final ListView listView = (ListView) findViewById(R.id.listViewMain);
        final Timer_DataBaseHelper db = new Timer_DataBaseHelper(this);
        final ArrayList<Timer_Score> timerArrayList = new ArrayList<>();
        final Timer_ScoreAdapter adapter = new Timer_ScoreAdapter(Activity_timer.this, R.layout.layout_adapter_list_timer, timerArrayList);

        //final ConstraintLayout timerButton = (ConstraintLayout) findViewById(R.id.constraint_timer_field);
        final View timerButton = (View) findViewById(R.id.timer_click_area);
        textTimer = (TextView) findViewById(R.id.text_timer);
        textTimer.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.timer_text_stop)));

        timerButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (timerStatus == 0) {
                            textTimer.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.timer_text_praparing)));
                            return false;
                        }
                        if (timerStatus == 2) {

                            customHandler.removeCallbacks(updateTimerThread);
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                            String strDate = sdf.format(new Date());
                            db.addData(textTimer.getText().toString(), strDate, cubeType);
                            textTimer.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.timer_text_stop)));
                            return false;
                        }
                    case MotionEvent.ACTION_MOVE:
                        return false;
                    case MotionEvent.ACTION_UP:
                        if (timerStatus == 0) {
                            textTimer.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.timer_text_stop)));
                            return false;
                        }
                        if (timerStatus == 1) {
                            startTime = SystemClock.uptimeMillis();
                            customHandler.postDelayed(updateTimerThread, 0);

                            timerStatus = 2;
                            return true;
                        }
                        if (timerStatus == 2) {
                            updateDataFromDB(timerArrayList);
                            listView.invalidateViews();
                            listView.refreshDrawableState();

                            showScores(timerArrayList);

                            timerStatus = 0;
                            break;
                        }
                }
                return false;
            }
        });

        timerButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (timerStatus == 0) {
                    customHandler.postDelayed(resetTimer, 0);
                    textTimer.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.timer_text_ready)));
                    timerStatus = 1;
                }
                return true;
            }
        });
        showDataFromDB(db, timerArrayList);
        //Show scores
        showScores(timerArrayList);
    }

    public void showScores(ArrayList<Timer_Score> timerArrayList){
        TimerAvarages results = avarages(timerArrayList);
        bestScore.setText(results.getMin());
        worstScore.setText(results.getMax());
        averageFrom5.setText(results.getAvg5());
        averageFrom10.setText(results.getAvg10());
        averageFrom20.setText(results.getAvg20());
        averageFrom50.setText(results.getAvg50());
    }


    public void showDataFromDB(Timer_DataBaseHelper db, ArrayList<Timer_Score> timerArrayList) {
        Cursor cursor = db.getDataFromDb();
        if (cursor.getCount() > 0) {
            cursor.moveToLast();
            do {
                String id = (cursor.getString(0) + "\n");
                String time = (cursor.getString(1) + "\n");
                String date = (cursor.getString(2) + "\n");
                String cube = (cursor.getString(3) + "\n");

                Timer_Score timerItem = new Timer_Score(id, time, date, cube);
                timerArrayList.add(timerItem);
            } while (cursor.moveToPrevious());
        } else {
            String id = "1";
            String time = "0";
            String date = "DATE: ";
            String cube = "CUBE: ";

            Timer_Score timerItem = new Timer_Score(id, time, date, cube);
            timerArrayList.add(timerItem);
        }
    }

    public void updateDataFromDB(ArrayList<Timer_Score> timerArrayList) {
        timerArrayList.clear();
        Timer_DataBaseHelper db1 = new Timer_DataBaseHelper(this);
        Cursor cursor = db1.getDataFromDb();

        if (cursor.getCount() > 0) {
            cursor.moveToLast();
            do {
                String id = (cursor.getString(0) + "\n");
                String time = (cursor.getString(1) + "\n");
                String date = (cursor.getString(2) + "\n");
                String cube = (cursor.getString(3) + "\n");

                Timer_Score timerItem = new Timer_Score(id, time, date, cube);
                timerArrayList.add(timerItem);
            } while (cursor.moveToPrevious());
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        cubeType = adapterView.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public TimerAvarages avarages(ArrayList<Timer_Score> timerArrayList){
        int worstScore = 0;
        int bestScore = 0;
        int avg5 = 0;
        int avg10 = 0;
        int avg20 = 0;
        int avg50 = 0;
        int sum5 = 0;
        int sum10 = 0;
        int sum20 = 0;
        int sum50 = 0;
        int numberOfScores_3x3 = 0;
        int numberOfScores_2x2 = 0;
        int itemNumber_3x3 = 0;
        int itemNumber_2x2 = 0;

        if (timerArrayList.isEmpty()) {
            return new TimerAvarages("N/A","N/A","N/A","N/A","N/A","N/A");
        } else {
            for (Timer_Score score : timerArrayList){
                if (score.cube.equals("3x3\n")){
                    numberOfScores_3x3 += 1;
                }
                if (score.cube.equals("2x2\n")){
                    numberOfScores_2x2 += 1;
                }
            }
            for (Timer_Score score : timerArrayList) {

                if (score.cube.equals(cubeType + "\n")) {
                    itemNumber_3x3 += 1;
                    int time = parseScoreToMillis(score.getScore());
                    if (bestScore == 0) {
                        bestScore = time;
                    } else if (time < bestScore) {
                        bestScore = time;
                    }
                    if (time > worstScore) {
                        worstScore = time;
                    }
                    if (numberOfScores_3x3 >= 5 && itemNumber_3x3<=5) {
                        sum5 += time;
                    }
                    if (numberOfScores_3x3 >= 10 && itemNumber_3x3<=10) {
                        sum10 += time;
                    }
                    if (numberOfScores_3x3 >= 20 && itemNumber_3x3<=20) {
                        sum20 += time;
                    }
                    if (numberOfScores_3x3 >= 50 && itemNumber_3x3<=50) {
                        sum50 += time;
                    }
                }


                avg5 = sum5 / 5;
                avg10 = sum10 / 10;
                avg20 = sum20 / 20;
                avg50 = sum50 / 50;
            }

                return new TimerAvarages(parseMillisToString(worstScore), parseMillisToString(bestScore), parseMillisToString(avg5), parseMillisToString(avg10),
                        parseMillisToString(avg20), parseMillisToString(avg50));
            }
    }

    public int parseScoreToMillis(String score){
        if (score.isEmpty() || score.equals("0")){
            return 0;
        } else {
            int millis = Integer.parseInt(score.substring(6, 8));
            int sec = Integer.parseInt(score.substring(3, 5)) * 100;
            int hours = Integer.parseInt(score.substring(0, 2)) * 100 * 60;
            return hours + sec + millis;
        }
    }

    public String parseMillisToString(int scoreInMillis){
        int hoursBack = scoreInMillis/100/60;
        int secBack = (scoreInMillis-(hoursBack*100*60))/100;
        int millisBack = scoreInMillis-((hoursBack*100*60)+(secBack*100));

        return String.format(Locale.getDefault(), "%02d:%02d:%02d", hoursBack, secBack, millisBack);
    }
    //to test something
    //public static void main(String[] args){


        //System.out.print("");
    //}
}