package appinventor.ai_pawchism.Rubic_Cube;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Timer_Score {
    String id;
    String score;
    String date;
    String cube;

    public Timer_Score(String id, String score, String date, String cube) {
        this.id = id;
        this.score = score;
        this.date = date;
        this.cube = cube;
    }

    public String getId() {
        return id;
    }

    public String getScore() {
        return score;
    }


    public String getDate() {
        return date;
    }

    public String getCube() {
        return cube;
    }

    public Date getDateInDateForm(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date1 = new Date();
        try {
            date1 = sdf.parse(date);
        } catch (ParseException e){
            e.printStackTrace();
        }
        return date1;
    }
}
