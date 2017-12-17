package app.ie.fitnesstracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class LoggedInActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn1, btn2;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        mAuth = FirebaseAuth.getInstance();

        //declare activities buttons
        btn1 = (Button) findViewById(R.id.newWorkoutButton);
        btn2 = (Button) findViewById(R.id.viewWorkoutsButton);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.newWorkoutButton:
                Intent intent1 = new Intent(this, NewWorkout.class);
                startActivity(intent1);
                break;
            case R.id.viewWorkoutsButton:
                Intent intent2 = new Intent(this, ViewWorkouts.class);
                startActivity(intent2);
                break;
            default:
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuLogout:
                mAuth.signOut();
                Toast.makeText(this, "Logging Out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}