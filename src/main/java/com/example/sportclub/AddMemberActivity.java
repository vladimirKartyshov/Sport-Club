package com.example.sportclub;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

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
    }
}
