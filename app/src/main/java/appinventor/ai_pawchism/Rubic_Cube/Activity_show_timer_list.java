package appinventor.ai_pawchism.Rubic_Cube;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Activity_show_timer_list extends AppCompatActivity  {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_list);

        final ListView listView = (ListView) findViewById(R.id.listViewScores);
        final Timer_DataBaseHelper db = new Timer_DataBaseHelper(this);
        final ArrayList<Timer_Score> timerArrayList = new ArrayList<>();
        showDataFromDB(db, timerArrayList);
        final Timer_ScoreAdapter adapter = new Timer_ScoreAdapter(Activity_show_timer_list.this, R.layout.layout_adapter_list_timer, timerArrayList);
        listView.setAdapter(adapter);

    }

    public void showDataFromDB(Timer_DataBaseHelper db, ArrayList<Timer_Score> timerArrayList){
        Cursor cursor = db.getDataFromDb();
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
        } else {
            String id = "1";
            String time = "0";
            String date = "DATE: ";
            String cube = "CUBE: ";

            Timer_Score timerItem = new Timer_Score(id, time,date, cube);
            timerArrayList.add(timerItem);
        }
    }
}
