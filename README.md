# Multimedia Applications Development

#### Emer Rutherford - 20067351

## Fitness Tracker App

For this module I have developed a native Android app that allows the user to track and record their
workouts by the date, time and duration of the workout.

### Prerequisites

The log in details for the app are
```
Email:      ewrutherford95@gmail.com
Password:   password
```

### Authentication

I used the Firebase Authentication feature to set up user logins with email and password. Only predetermined users have access
to the activities past the login screen which keeps each users details safe and secure. An example can be seen below

```
mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth
            firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" +
                    user.getUid());
                }
                else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
```

### Data Persistence

For data handling I used SQLite to create the database needed to store the workouts, an example of which can be seen below.

```
@Override
    public void onCreate(SQLiteDatabase db)
    {
        String createTable = "CREATE TABLE " + TABLE_NAME + "("
        + COL1 + " TEXT, " + COL2 + " TEXT, " + COL3 + " TEXT);";
        db.execSQL(createTable);
    }
```
An example of the SQLite workout_table can be seen below.

| Date          | Time          | Duration |
|:------------: |:-------------:| :-----:  |
| 21/12/18      | 21:00         |    20    |
| 13/03/16      | 14:26         |    55    |
| 25/09/17      | 09:02         |    15    |

## Git Project

The app can be found on GitHub by following the link below

[Fitness Tracker](https://github.com/eeswr/FitnessTracker)
