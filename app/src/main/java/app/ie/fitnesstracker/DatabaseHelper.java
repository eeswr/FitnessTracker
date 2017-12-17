package app.ie.fitnesstracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.R.attr.id;

/**
 * Created by ewrutherford95 on 04/11/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    public static final String TABLE_NAME = "workout_table";
    public static final String COL_ID = "id";
    public static final String COL1 = "date";
    public static final String COL2 = "time";
    public static final String COL3 = "duration";

    public DatabaseHelper(Context context)
    {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String createTable = "CREATE TABLE " + TABLE_NAME + "(" +COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + COL1 + " TEXT, " + COL2 + " TEXT, " + COL3 + " TEXT);";
        db.execSQL(createTable);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData( String date, String time, String duration){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL1, date);
        values.put(COL2, time);
        values.put(COL3, duration);

        Log.d(TAG, "addData: Adding to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, values);

        if (result == -1)
        {
            return false;
        }
        else {
            return true;
        }
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }
}
