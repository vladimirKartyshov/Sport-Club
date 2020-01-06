package com.example.sportclub;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import java.util.ArrayList;

public class AddMemberActivity extends AppCompatActivity {

    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText groupEditText;
    private Spinner genderSpinner;
    private int gender = 0;
    private ArrayAdapter spinnerAdapter;
    private ArrayList spinnerArraylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);

        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        groupEditText = findViewById(R.id.groupEditText);
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
                        gender = 1;
                    } else if (selectedGender.equals("Female")) {
                        gender = 2;
                    } else {
                        gender = 0;
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                gender = 0;
            }
        });
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
                return true;
            case R.id.delete_member:
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
