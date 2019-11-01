package appinventor.ai_pawchism.Rubic_Cube;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Timer_DataBaseHelper extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "cube_scores";

    public Timer_DataBaseHelper(Context context){
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, SCORE TEXT, DATE TEXT, CUBE TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public boolean addData(String score,String date, String cube){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("SCORE", score);
        contentValues.put("DATE", date);
        contentValues.put("CUBE", cube);

        if (db.insert(TABLE_NAME, null, contentValues)==-1){
            return false;
        } else {
            return true;
        }
    }

    public Cursor getDataFromDb(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);

        return data;
    }

    public boolean deleteDataFromDB(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        //db.execSQL("delete from " + TABLE_NAME);

        return true;
    }
}
