package app.ie.fitnesstracker;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

import static app.ie.fitnesstracker.DatabaseHelper.TABLE_NAME;


/**
 * Created by ewrutherford95 on 13/12/2017.
 */

public class EditSingleWorkout extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "EditWorkout";

        Button btnDatePicker, btnTimePicker, saveButton;
        EditText txtDate, txtTime, durationMins;
        private int mYear, mMonth, mDay, mHour, mMinute;

        Button update;
        SQLiteDatabase SQLITEDATABASE;
        DatabaseHelper mDatabaseHelper;
        Cursor cursor;
        String IDholder;
        String SQLiteDataBaseQueryHolder;
        SQLiteDatabase sqLiteDatabaseObj;



    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_edit_single_workout);

            btnDatePicker=(Button)findViewById(R.id.btn_date);
            btnTimePicker=(Button)findViewById(R.id.btn_time);
            txtDate=(EditText)findViewById(R.id.in_date);
            txtTime=(EditText)findViewById(R.id.in_time);
            durationMins=(EditText)findViewById(R.id.durationMins);
            saveButton =(Button)findViewById(R.id.saveButton);

            btnDatePicker.setOnClickListener(this);
            btnTimePicker.setOnClickListener(this);

            mAuth = FirebaseAuth.getInstance();
            mDatabaseHelper = new DatabaseHelper(this);

            update = (Button) findViewById(R.id.buttonUpdate);

            mDatabaseHelper = new DatabaseHelper(this);

            mAuthListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if (user != null) {
                        // User is signed in
                        Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    }
                    else {
                        // User is signed out
                        Log.d(TAG, "onAuthStateChanged:signed_out");
                    }
                }
            };

            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String GetDate = txtDate.getText().toString() ;
                    String GetTime = txtTime.getText().toString();
                    String GetDuration = durationMins.getText().toString();

                    OpenSQLiteDataBase();

                    SQLiteDataBaseQueryHolder = "UPDATE " + mDatabaseHelper.TABLE_NAME + " SET "+mDatabaseHelper.COL1+" = '"+GetDate+"' , "+mDatabaseHelper.COL2+" = '"+GetTime+"' , "+mDatabaseHelper.COL3+" = '"+GetDuration+"' WHERE id = " + IDholder + "";

                    SQLITEDATABASE.execSQL(SQLiteDataBaseQueryHolder);

                    SQLITEDATABASE.close();

                    Toast.makeText(EditSingleWorkout.this,"Workout Updated", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "DATA" +SQLiteDataBaseQueryHolder);
                    finish();
                }
            });

        }

    @Override
    public void onClick(View v) {

        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DialogTheme,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == btnTimePicker) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, R.style.DialogTheme,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    }


    @Override
        protected void onResume() {

            ShowSRecordInEditText();

            super.onResume();
        }

        public void ShowSRecordInEditText() {

            SQLITEDATABASE = mDatabaseHelper.getWritableDatabase();

            IDholder = getIntent().getStringExtra("EditID");

            cursor = SQLITEDATABASE.rawQuery("SELECT * FROM workout_table WHERE id = " + IDholder + "", null);

            if (cursor.moveToFirst()) {

                do {
                    txtDate.setText(cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL1)));
                    txtTime.setText(cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL2)));
                    durationMins.setText(cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL3)));
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
