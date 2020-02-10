package com.example.sportclub;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.example.sportclub.db.ClubSportContract;

import java.util.ArrayList;

public class AddMemberActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int EDIT_MEMBER_LOADER = 111;
    Uri currentMemberUri;

    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText sportEditText;
    private Spinner genderSpinner;
    private int gender = 0;
    private ArrayAdapter spinnerAdapter;
    private ArrayList spinnerArraylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);

        Intent intent = getIntent();

        currentMemberUri = intent.getData();

        if (currentMemberUri == null){
            setTitle("Add a Member");
        }else {
            setTitle("Edit the Member");
        }

        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        sportEditText = findViewById(R.id.sportEditText);
        genderSpinner = findViewById(R.id.genderSpinner);

        //это динамический метод тк мы можем сколько угодно добавлять эл-ов в arraylist
        // те опций spinner.При фиксиров-м кол-ве исп-ся статический способ

//        spinnerArraylist = new ArrayList();
//        spinnerArraylist.add("Unknow");
//        spinnerArraylist.add("Male");
//        spinnerArraylist.add("Female");
//
//        spinnerAdapter = new ArrayAdapter(this,
//                android.R.layout.simple_spinner_item, spinnerArraylist);
//        // создаем вид каждого эл-та spinner
//        spinnerAdapter.setDropDownViewResource(
//                android.R.layout.simple_spinner_dropdown_item);
//        //устанавливаем нашему спинеру этот адаптор
//        genderSpinner.setAdapter(spinnerAdapter);

         //===============================================================
         //===============================================================

        //статический способ, где кол-во эл-в фиксировано в спинаре
        // для начала создается нов ресурс файл arrays в values

        spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_gender, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(spinnerAdapter);

        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                String selectedGender = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selectedGender)) {
                    if (selectedGender.equals("Male")) {
                        gender = ClubSportContract.MemberEntry.GENDER_MALE;
                    } else if (selectedGender.equals("Female")) {
                        gender = ClubSportContract.MemberEntry.GENDER_FEMALE;
                    } else {
                        gender = ClubSportContract.MemberEntry.GENDER_UNKNOWN;
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                gender = 0;
            }
        });

        getSupportLoaderManager().initLoader(EDIT_MEMBER_LOADER,null,this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_member_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_member:
                insertMember();
                return true;
            case R.id.delete_member:
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void insertMember(){

        String firstName = firstNameEditText.getText().toString().trim();//trim-убирает пробелы в нач и конц строки
        String lastName = lastNameEditText.getText().toString().trim();
        String sport = sportEditText.getText().toString().trim();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ClubSportContract.MemberEntry.COLUMN_FIRST_NAME,firstName);
        contentValues.put(ClubSportContract.MemberEntry.COLUMN_LAST_NAME,lastName);
        contentValues.put(ClubSportContract.MemberEntry.COLUMN_SPORT,sport);
        contentValues.put(ClubSportContract.MemberEntry.COLUMN_GENDER,gender);

        //для того чтобы поместить contentValues должны использовать ContentResolver
        ContentResolver contentResolver = getContentResolver();
        Uri uri = contentResolver.insert(ClubSportContract.MemberEntry.CONTENT_URI,contentValues);

        if (uri==null){
            Toast.makeText(this,"Insertion of data in the table",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,"Data saved",Toast.LENGTH_LONG).show();
        }

    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {

        String [] projection = {
                ClubSportContract.MemberEntry._ID,
                ClubSportContract.MemberEntry.COLUMN_FIRST_NAME,
                ClubSportContract.MemberEntry.COLUMN_LAST_NAME,
                ClubSportContract.MemberEntry.COLUMN_GENDER,
                ClubSportContract.MemberEntry.COLUMN_SPORT
        };

        return  new CursorLoader(this,
                currentMemberUri,
                projection,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        if (cursor.moveToFirst()){
            int firstNameColumnIndex = cursor.getColumnIndex
                    (ClubSportContract.MemberEntry.COLUMN_FIRST_NAME);

            int lastNameColumnIndex = cursor.getColumnIndex
                    (ClubSportContract.MemberEntry.COLUMN_LAST_NAME);

            int genderColumnIndex = cursor.getColumnIndex
                    (ClubSportContract.MemberEntry.COLUMN_GENDER);

            int sportColumnIndex = cursor.getColumnIndex
                    (ClubSportContract.MemberEntry.COLUMN_SPORT);

            String firstName = cursor.getString(firstNameColumnIndex);
            String lastName = cursor.getString(lastNameColumnIndex);
            int gender = cursor.getInt(genderColumnIndex);
            String sport = cursor.getString(sportColumnIndex);

            firstNameEditText.setText(firstName);
            lastNameEditText.setText(lastName);
            sportEditText.setText(sport);

            switch (gender){
                case ClubSportContract.MemberEntry.GENDER_MALE:
                    genderSpinner.setSelection(1);
                    break;
                case ClubSportContract.MemberEntry.GENDER_FEMALE:
                    genderSpinner.setSelection(2);
                    break;
                case ClubSportContract.MemberEntry.GENDER_UNKNOWN:
                    genderSpinner.setSelection(0);
                    break;
            }
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
