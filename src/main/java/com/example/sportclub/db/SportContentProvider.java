package com.example.sportclub.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.widget.Toast;

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
                    Toast.makeText(getContext(),"Incorrect URI", Toast.LENGTH_LONG);
                    throw new IllegalArgumentException("Can't query incorrect URI" + uri);
        }
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }
}
