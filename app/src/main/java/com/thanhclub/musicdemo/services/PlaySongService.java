package com.thanhclub.musicdemo.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RemoteViews;

import com.thanhclub.musicdemo.R;
import com.thanhclub.musicdemo.manager.SongPlayerManager;
import com.thanhclub.musicdemo.model.Song;
import com.thanhclub.musicdemo.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class PlaySongService extends Service {
    private static final int REQUEST_CODE_PAUSE = 101;
    private static final int REQUEST_CODE_PREVIOUS = 102;
    private static final int REQUEST_CODE_NEXT = 103;
    private static final String ACTION_NEXT = "ACTION_NEXT";
    private static final String ACTION_PRE = "ACTION_PRE";
    private static final String ACTION_PAUSE = "ACTION_PAUSE";
    private final int ID_NOTIFICATION = 196;
    private SongPlayerManager playerManager;
    private List<Song> songs;
    private Notification notifi;
    private Notification.Builder notificationBuilder;
    private RemoteViews remoteView;
    private int position;


    @Override
    public void onCreate() {
        super.onCreate();
        if (playerManager == null) {
            playerManager = new SongPlayerManager();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = "";
        action = intent.getAction();
        switch (action) {
            case ACTION_NEXT:
                if (position == songs.size() - 1) {
                    position = 0;
                } else {
                    position++;
                }
                playSong(position);
                break;
            case ACTION_PAUSE:
                if (playerManager.getState() == SongPlayerManager.PLAYER_PAUSE) {
                    resumeSong();
                } else {
                    pauseSong();
                }
                updateNotification();
                break;
            case ACTION_PRE:
                if (position == 0) {
                    position = songs.size() - 1;
                } else {
                    position--;
                }
                playSong(position);
                break;
        }
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new PlayBinder();
    }

    public class PlayBinder extends Binder {
        public PlaySongService getPlaySongService() {
            return PlaySongService.this;
        }
    }

    public void initSong(List<Song> songs) {
        this.songs = songs;
    }

    public void playSong(int position) {
        playerManager.setup(songs.get(position).getData());
        createNotification(songs.get(position).getTitle());
        this.position = position;
    }

    public void pauseSong() {
        playerManager.pause();
    }

    public void resumeSong() {
        playerManager.resume();
    }

    private void createNotification(String title) {
        remoteView = new RemoteViews(getPackageName(), R.layout.layout_player_song);
        remoteView.setTextViewText(R.id.title_notification, title);

        Intent intentService = new Intent();
        intentService.setAction(ACTION_NEXT);
        intentService.setClass(this, PlaySongService.class);
        PendingIntent peServiceNext = PendingIntent.getService(this, REQUEST_CODE_NEXT, intentService,
                PendingIntent.FLAG_UPDATE_CURRENT);
        remoteView.setOnClickPendingIntent(R.id.btn_next_notification, peServiceNext);

        intentService.setAction(ACTION_PRE);
        intentService.setClass(this, PlaySongService.class);
        PendingIntent peServicePre = PendingIntent.getService(this, REQUEST_CODE_PREVIOUS, intentService,
                PendingIntent.FLAG_UPDATE_CURRENT);
        remoteView.setOnClickPendingIntent(R.id.btn_pre_notification, peServicePre);

        intentService.setAction(ACTION_PAUSE);
        intentService.setClass(this, PlaySongService.class);
        PendingIntent peServicePause = PendingIntent.getService(this, REQUEST_CODE_PAUSE, intentService,
                PendingIntent.FLAG_UPDATE_CURRENT);
        remoteView.setOnClickPendingIntent(R.id.btn_play_notification, peServicePause);

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent =
                PendingIntent.getActivities(this, (int) System.currentTimeMillis(), new Intent[]{intent}, 0);
        notificationBuilder = new Notification.Builder(getApplicationContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notifi = notificationBuilder
                    .setSmallIcon(R.drawable.ic_song)
                    .setContentIntent(pendingIntent)
                    .setContent(remoteView)
                    .setDefaults(Notification.FLAG_NO_CLEAR)
                    .build();
        }
        startForeground(ID_NOTIFICATION, notifi);
    }

    private void updateNotification() {
        if (playerManager.getState() == SongPlayerManager.PLAYER_PLAYING) {
            remoteView.setImageViewResource(R.id.btn_play_notification, R.drawable.ic_pause);
        } else if (playerManager.getState() == SongPlayerManager.PLAYER_PAUSE) {
            remoteView.setImageViewResource(R.id.btn_play_notification, R.drawable.ic_play);
        }
        startForeground(ID_NOTIFICATION, notifi);
    }
}
