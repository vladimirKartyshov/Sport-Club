package com.example.sportclub;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sportclub.db.ClubSportContract;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    ListView dataListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataListView = findViewById(R.id.dataListView);

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
        MemberCursorAdapter cursorAdapter = new MemberCursorAdapter(this,cursor,false);
        dataListView.setAdapter(cursorAdapter);
    }
}
