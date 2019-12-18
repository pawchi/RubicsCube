package appinventor.ai_pawchism.Rubic_Cube;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Activity_show_timer_list extends AppCompatActivity {


    private final Timer_DataBaseHelper db = new Timer_DataBaseHelper(this);
    private final ArrayList<Timer_Score> timerArrayList = new ArrayList<>();
    private Timer_ScoreAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_list);

        final ListView listView = (ListView) findViewById(R.id.listViewScores);
        showDataFromDB(db, timerArrayList);
        adapter = new Timer_ScoreAdapter(Activity_show_timer_list.this, R.layout.layout_adapter_list_timer, timerArrayList);
        listView.setAdapter(adapter);
        Button deleteBtn = findViewById(R.id.button_delete_scores);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Activity_show_timer_list.this);
                alertDialogBuilder.setMessage(R.string.confirm_delete_score);
                alertDialogBuilder.setCancelable(true);
                alertDialogBuilder.setPositiveButton(
                        R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Set<Timer_Score> items = adapter.getItemsToRemove();
                                if (items.size() > 0) {
                                    removeDataFromDB(items);
                                }
                            }
                        }
                );

                alertDialogBuilder.setNegativeButton(
                        R.string.not, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }
                );
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });

    }

    private void removeDataFromDB(Set<Timer_Score> items) {
        List<String> idsToRemove = new ArrayList<>();
        for (Timer_Score score : items) {
            idsToRemove.add(score.id.replace("\n", ""));
        }
        boolean removed = db.deleteDataFromDBbyIDs(idsToRemove);
        if (removed) {
            timerArrayList.removeAll(items);
            adapter.notifyDataSetChanged();
        }
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, Activity_timer.class));
    }
}

