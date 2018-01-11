package com.thanhclub.musicdemo.manager;


import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.thanhclub.musicdemo.model.Song;

import java.util.ArrayList;

public class SongManager {
    private Cursor cursor;
    private Context context;

    public SongManager(Context context) {
        this.context = context;
    }

    public ArrayList<Song> getSongs() {
        cursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Audio.Media.TITLE + " ASC");
        ArrayList<Song> songs = new ArrayList<>();
        if (cursor == null || cursor.getCount() == 0) {
            return null;
        }
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String id = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
            String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
            int duration = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
            String data = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            String singer = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
            songs.add(new Song(id, title, singer, data, duration));
            cursor.moveToNext();
        }
        cursor.close();
        return songs;
    }
}
