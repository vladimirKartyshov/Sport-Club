package com.example.sportclub;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sportclub.db.ClubSportContract;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    TextView dataTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataTextView = findViewById(R.id.dataTextView);

        FloatingActionButton floatingActionButton =
                findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddMemberActivity.class);
                startActivity(intent);
            }
        });

        }

    @Override
    protected void onStart() {
        super.onStart();
        displayData();
    }

    private void displayData(){
        String[] projection = {
                ClubSportContract.MemberEntry._ID,
                ClubSportContract.MemberEntry.COLUMN_FIRST_NAME,
                ClubSportContract.MemberEntry.COLUMN_LAST_NAME,
                ClubSportContract.MemberEntry.COLUMN_GENDER,
                ClubSportContract.MemberEntry.COLUMN_SPORT
        };

        Cursor cursor = getContentResolver().query(
                ClubSportContract.MemberEntry.CONTENT_URI,
                projection,
                null,
                null,
                null
        );
        dataTextView.setText("All members\n\n");
        dataTextView.append(ClubSportContract.MemberEntry._ID + " " +
                        ClubSportContract.MemberEntry.COLUMN_FIRST_NAME + " " +
                ClubSportContract.MemberEntry.COLUMN_LAST_NAME + " " +
                ClubSportContract.MemberEntry.COLUMN_GENDER+ " " +
                ClubSportContract.MemberEntry.COLUMN_SPORT
                );

        int idIndex = cursor.getColumnIndex(ClubSportContract.MemberEntry._ID);
        int idFirstName = cursor.getColumnIndex(ClubSportContract.MemberEntry.COLUMN_FIRST_NAME);
        int idLastName = cursor.getColumnIndex(ClubSportContract.MemberEntry.COLUMN_LAST_NAME);
        int idGender = cursor.getColumnIndex(ClubSportContract.MemberEntry.COLUMN_GENDER);
        int idSport = cursor.getColumnIndex(ClubSportContract.MemberEntry.COLUMN_SPORT);

        while (cursor.moveToNext()){
            int currentId = cursor.getInt(idIndex);
            String currentFirstName = cursor.getString(idFirstName);
            String currentLastName = cursor.getString(idLastName);
            int currentGender = cursor.getInt(idGender);
            String currentSport = cursor.getString(idSport);

            dataTextView.append("\n" +
                    currentId + " " +
                    currentFirstName + " " +
                    currentLastName + " " +
                    currentGender + " " +
                    currentSport);
        }

    }
}
