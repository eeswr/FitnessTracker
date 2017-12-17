package app.ie.fitnesstracker;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

public class ViewWorkouts extends AppCompatActivity {

private static final String TAG = "ViewWorkouts";

    DatabaseHelper mDatabaseHelper;
    SQLiteDatabase SQLITEDATABASE;
    SQLiteListAdapter ListAdapter;
    Cursor cursor;
    ListView LISTVIEW;

    ArrayList<String> ID_ArrayList = new ArrayList<String>();
    ArrayList<String> DATE_ArrayList = new ArrayList<String>();
    ArrayList<String> TIME_ArrayList = new ArrayList<String>();
    ArrayList<String> DURATION_ArrayList = new ArrayList<String>();

    ArrayList<String> ListViewClickItemArray = new ArrayList<String>();
    String TempHolder;

    Button DeleteAll;
    String SQLiteDataBaseQueryHolder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_workouts);

        LISTVIEW = (ListView) findViewById(R.id.workoutList);
        mDatabaseHelper = new DatabaseHelper(this);

        DeleteAll = (Button)findViewById(R.id.deleteAllButton);

        LISTVIEW.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // TODO Auto-generated method stub

                Intent intent = new Intent(getApplicationContext(),ShowSingleWorkout.class);

                intent.putExtra("ListViewClickedItemValue", ListViewClickItemArray.get(position).toString());

                startActivity(intent);

            }
        });

        DeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SQLiteDataBaseQueryHolder = "DELETE FROM workout_table";;

                SQLITEDATABASE.execSQL(SQLiteDataBaseQueryHolder);

                SQLITEDATABASE.close();

                Toast.makeText(ViewWorkouts.this,"All Workouts Deleted", Toast.LENGTH_LONG).show();

                finish();

            }
        });

    }

    @Override
    protected void onResume() {

        ShowSQLiteDBdata() ;

        super.onResume();
    }

    private void ShowSQLiteDBdata() {
        Log.d(TAG, "populateListView: Displaying data in the ListView");

        SQLITEDATABASE = mDatabaseHelper.getWritableDatabase();

        cursor = SQLITEDATABASE.rawQuery("SELECT * FROM workout_table", null);

        ID_ArrayList.clear();
        DATE_ArrayList.clear();
        TIME_ArrayList.clear();
        DURATION_ArrayList.clear();

        if (cursor.moveToFirst()) {
            do {
                ID_ArrayList.add(cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_ID)));
                ListViewClickItemArray.add(cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL_ID)));
                DATE_ArrayList.add(cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL1)));
                TIME_ArrayList.add(cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL2)));
                DURATION_ArrayList.add(cursor.getString(cursor.getColumnIndex(mDatabaseHelper.COL3)));

            } while (cursor.moveToNext());
        }

        ListAdapter = new SQLiteListAdapter(ViewWorkouts.this,
                ID_ArrayList,
                DATE_ArrayList,
                TIME_ArrayList,
                DURATION_ArrayList
        );

        LISTVIEW.setAdapter(ListAdapter);
        cursor.close();


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuLogout:
                Toast.makeText(this, "Logging Out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
