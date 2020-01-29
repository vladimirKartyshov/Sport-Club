package com.example.sportclub.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class SportContentProvider extends ContentProvider {

    SportDbOpenHelper dbOpenHelper;

    private static final int MEMBERS = 111;
    private static final int MEMBER_ID = 222;

    // Creates a UriMatcher object.
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // в статическом блоке код срабатывает без создания обЪекта класса (content provider)
    static {
        // для работы со всей таблицей
        uriMatcher.addURI(ClubSportContract.AUTHORITY,ClubSportContract.PATH_MEMBERS, MEMBERS);

        //для работы с одной строкой
        uriMatcher.addURI(ClubSportContract.AUTHORITY,ClubSportContract.PATH_MEMBERS + "/#",MEMBER_ID);
    }

    @Override
    public boolean onCreate() {
        dbOpenHelper = new SportDbOpenHelper(getContext());
        return false;
    }

    //параметр projection это название столбцов
    @Override
    public Cursor query(Uri uri, String[] projection,
                        String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        //тк метод query возвращает тип cursor, то создаем обЪект класса Cursor
        Cursor cursor;

        int match = uriMatcher.match(uri);//возвращает введенный uri

        switch (match){
            case MEMBERS:
                cursor = db.query(ClubSportContract.MemberEntry.TABLE_NAME,projection,selection,
                        selectionArgs,null,null,sortOrder);
                break;

                //устанавливаем selection(отбор) "_id=?" вместо вопроса в SQL коде будут передо-ся аргументы selectionArgs
                //selectionArgs(аргументы отбора) = новый массив ContentUris его метод parseId(uri)преобразует
                // последний сегмент после последнего / в числовой
            case MEMBER_ID:
                selection = ClubSportContract.MemberEntry._ID + "=?";//выбираем запись по столбцу ID
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(ClubSportContract.MemberEntry.TABLE_NAME,projection,selection,
                        selectionArgs,null,null,sortOrder);
                break;

                default:
                    throw new IllegalArgumentException("Can't query incorrect URI" + uri);
        }
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        String firstName = values.getAsString(ClubSportContract.MemberEntry.COLUMN_FIRST_NAME);
        if (firstName == null){
            throw new IllegalArgumentException("You have to input first name");
        }

        String lastName = values.getAsString(ClubSportContract.MemberEntry.COLUMN_LAST_NAME);
        if (lastName == null){
            throw new IllegalArgumentException("You have to input last name");
        }

        Integer gender = values.getAsInteger(ClubSportContract.MemberEntry.COLUMN_GENDER);
        if (gender  == null || !(gender == ClubSportContract.MemberEntry.GENDER_UNKNOWN || gender ==
                ClubSportContract.MemberEntry.GENDER_MALE || gender == ClubSportContract.MemberEntry.GENDER_FEMALE)){
            throw new IllegalArgumentException("You have to input correct gender");
        }

        String sport = values.getAsString(ClubSportContract.MemberEntry.COLUMN_SPORT);
        if (sport == null){
            throw new IllegalArgumentException("You have to input sport name");
        }

        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();

        int match = uriMatcher.match(uri);

        switch (match) {
            case MEMBERS:
           long id =  db.insert(ClubSportContract.MemberEntry.TABLE_NAME,null,values);
           if (id == -1){
               Log.e("InsertMethod", "Insertion of data in the table failed for" + uri);
               return null;
           }

           return ContentUris.withAppendedId(uri,id);

            default:
                throw new IllegalArgumentException("Insertion of data in the table failed for" + uri);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();

        int match = uriMatcher.match(uri);//возвращает введенный uri

        switch (match){
            case MEMBERS:

                return db.delete(ClubSportContract.MemberEntry.TABLE_NAME, selection, selectionArgs);

            case MEMBER_ID:
                selection = ClubSportContract.MemberEntry._ID + "=?";//выбираем запись по столбцу ID
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                return db.delete(ClubSportContract.MemberEntry.TABLE_NAME, selection, selectionArgs);

            default:
                throw new IllegalArgumentException("Can't delete this URI" + uri);
        }

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        if (values.containsKey(ClubSportContract.MemberEntry.COLUMN_FIRST_NAME)){
            String firstName = values.getAsString(ClubSportContract.MemberEntry.COLUMN_FIRST_NAME);
            if (firstName == null){
                throw new IllegalArgumentException("You have to input first name");
            }
        }

        if (values.containsKey(ClubSportContract.MemberEntry.COLUMN_LAST_NAME)){
            String lastName = values.getAsString(ClubSportContract.MemberEntry.COLUMN_LAST_NAME);
            if (lastName == null){
                throw new IllegalArgumentException("You have to input last name");
            }
        }

        if (values.containsKey(ClubSportContract.MemberEntry.COLUMN_GENDER)){
            Integer gender = values.getAsInteger(ClubSportContract.MemberEntry.COLUMN_GENDER);
            if (gender  == null || !(gender == ClubSportContract.MemberEntry.GENDER_UNKNOWN || gender ==
                    ClubSportContract.MemberEntry.GENDER_MALE || gender == ClubSportContract.MemberEntry.GENDER_FEMALE)){
                throw new IllegalArgumentException("You have to input correct gender");
            }
        }

        if (values.containsKey(ClubSportContract.MemberEntry.COLUMN_SPORT)){

            String sport = values.getAsString(ClubSportContract.MemberEntry.COLUMN_SPORT);
            if (sport == null){
                throw new IllegalArgumentException("You have to input sport name");
            }
        }

        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();

        int match = uriMatcher.match(uri);//возвращает введенный uri

        switch (match){
            case MEMBERS:

                return db.update(ClubSportContract.MemberEntry.TABLE_NAME, values, selection, selectionArgs);

            case MEMBER_ID:
                selection = ClubSportContract.MemberEntry._ID + "=?";//выбираем запись по столбцу ID
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                return db.update(ClubSportContract.MemberEntry.TABLE_NAME, values, selection, selectionArgs);

            default:
                throw new IllegalArgumentException("Can't update this URI" + uri);
        }
    }

    @Override
    public String getType(Uri uri) {

        int match = uriMatcher.match(uri);//возвращает введенный uri

        switch (match){
            case MEMBERS:

                return ClubSportContract.MemberEntry.CONTENT_MULTIPLE_ITEMS;

            case MEMBER_ID:


                return ClubSportContract.MemberEntry.CONTENT_SINGLE_ITEM;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }
}
