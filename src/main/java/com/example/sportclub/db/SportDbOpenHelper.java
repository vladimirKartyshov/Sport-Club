package com.example.sportclub.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SportDbOpenHelper extends SQLiteOpenHelper {

    public SportDbOpenHelper(Context context) {
        super(context, ClubSportContract.DATABASE_NAME,
                null, ClubSportContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //создаем таблицу
        String CREATE_MEBERS_TABLE = "CREATE TABLE " + ClubSportContract.MemberEntry.TABLE_NAME + "("
                + ClubSportContract.MemberEntry._ID + " INTEGER PRIMARY KEY,"
                + ClubSportContract.MemberEntry.COLUMN_FIRST_NAME + " TEXT,"
                + ClubSportContract.MemberEntry.COLUMN_LAST_NAME + " TEXT,"
                + ClubSportContract.MemberEntry.COLUMN_GENDER + " INTEGER NOT NULL,"
                + ClubSportContract.MemberEntry.COLUMN_SPORT + " TEXT" + ")";
        db.execSQL(CREATE_MEBERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ClubSportContract.DATABASE_NAME);
        onCreate(db);
    }
}
