package app.ie.fitnesstracker;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static app.ie.fitnesstracker.DatabaseHelper.TABLE_NAME;

public class ShowSingleWorkout extends AppCompatActivity {

        String IDholder;
        TextView id, date, time, duration;
        DatabaseHelper mDatabaseHelper;
        Cursor cursor;
        Button Delete, Edit;
        SQLiteDatabase SQLITEDATABASE;
        String SQLiteDataBaseQueryHolder;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_show_single_workout);

            id = (TextView) findViewById(R.id.textViewId);
            date = (TextView) findViewById(R.id.textViewDate);
            time = (TextView) findViewById(R.id.textViewTime);
            duration = (TextView) findViewById(R.id.textViewDuration);

            Delete = (Button)findViewById(R.id.buttonDelete);
            Edit = (Button)findViewById(R.id.buttonEdit);

            mDatabaseHelper = new DatabaseHelper(this);

            Delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    OpenSQLiteDataBase();

                    SQLiteDataBaseQueryHolder = "DELETE FROM workout_table WHERE id = "+IDholder+"";;

                    SQLITEDATABASE.execSQL(SQLiteDataBaseQueryHolder);

                    SQLITEDATABASE.close();

                    Toast.makeText(ShowSingleWorkout.this,"Workout Deleted", Toast.LENGTH_LONG).show();

                    finish();

                }
            });

           Edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(getApplicationContext(),EditSingleWorkout.class);

                    intent.putExtra("EditDate", IDholder);

                    startActivity(intent);

                }
            });

        }

        @Override
        protected void onResume() {

            ShowSingleRecordInTextView();

            super.onResume();
        }

        public void ShowSingleRecordInTextView() {
            SQLITEDATABASE = mDatabaseHelper.getWritableDatabase();

            IDholder = getIntent().getStringExtra("ListViewClickedItemValue");

            cursor = SQLITEDATABASE.rawQuery("SELECT * FROM workout_table WHERE id = " + IDholder + "", null);

            if (cursor.moveToFirst()) {

                do {
                    id.setText(cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_ID)));
                    date.setText(cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL1)));
                    time.setText(cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL2)));
                    duration.setText(cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL3)));
                }
                while (cursor.moveToNext());

                cursor.close();

            }
        }

        public void OpenSQLiteDataBase(){

            SQLITEDATABASE = openOrCreateDatabase(TABLE_NAME, Context.MODE_PRIVATE, null);

        }

    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

}
