package com.thanhclub.musicdemo.manager;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.thanhclub.musicdemo.model.Song;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    public static final String DATABASE_NAME = "FavoriteSong.db";
    public static final String ID = "ID";
    public static final String TITLE = "TITLE";
    public static final String SINGER = "SINGER";
    public static final String DATA = "DATA";
    public static final String DURATION = "DURATION";
    public static final String TABLE_NAME = "Favorite";
    public static final String PATH = Environment.getDataDirectory().getAbsolutePath() + "/data/com.thanhclub.musicdemo/";
    private Context context;
    private SQLiteDatabase sqLiteDatabase;

    public DatabaseManager(Context context) {
        this.context = context;
        createDatabase();
    }

    private void createDatabase() {
        if (sqLiteDatabase == null) {
            sqLiteDatabase = SQLiteDatabase.openDatabase(PATH + DATABASE_NAME, null, SQLiteDatabase.CREATE_IF_NECESSARY);
            String sql = "create table if not exists " + TABLE_NAME + "(";
            sql += ID + " TEXT primary key,";
            sql += TITLE + " TEXT ,";
            sql += SINGER + " TEXT,";
            sql += DATA + " TEXT,";
            sql += DURATION + " INTEGER)";
            sqLiteDatabase.execSQL(sql);
        }
    }

    private void openDatabase() {
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            sqLiteDatabase = SQLiteDatabase.openDatabase(PATH + DATABASE_NAME, null, SQLiteDatabase.OPEN_READWRITE);
        }
    }

    private void closeDatabase() {
        if (sqLiteDatabase != null || sqLiteDatabase.isOpen()) {
            sqLiteDatabase.close();
        }
    }

    public void insertFavorite(Song song) {
        openDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID, song.getId());
        contentValues.put(TITLE, song.getTitle());
        contentValues.put(SINGER, song.getSinger());
        contentValues.put(DATA, song.getData());
        contentValues.put(DURATION, song.getDuration());
        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        closeDatabase();
    }

    public void deleteSong(String id) {
        openDatabase();
        sqLiteDatabase.delete(TABLE_NAME, ID + "=?", new String[]{id});
        closeDatabase();
    }

    public List<Song> getFavoriteSong() {
        openDatabase();
        List<Song> songs = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (cursor.getCount() == 0 || cursor == null) {
            return null;
        }
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String id = cursor.getString(cursor.getColumnIndex(ID));
            String title = cursor.getString(cursor.getColumnIndex(TITLE));
            String singer = cursor.getString(cursor.getColumnIndex(SINGER));
            String data = cursor.getString(cursor.getColumnIndex(DATA));
            int duration = cursor.getInt(cursor.getColumnIndex(DURATION));
            songs.add(new Song(id, title, singer, data, duration));
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return songs;
    }
}
