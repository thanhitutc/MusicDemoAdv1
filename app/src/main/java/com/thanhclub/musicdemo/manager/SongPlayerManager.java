package com.thanhclub.musicdemo.manager;


import android.media.AudioManager;
import android.media.MediaPlayer;

import java.io.IOException;

public class SongPlayerManager {
    public static final int PLAYER_IDEL = -1;
    public static final int PLAYER_PREPARED = 0;
    public static final int PLAYER_PLAYING = 1;
    public static final int PLAYER_PAUSE = 2;
    public static final int PLAYER_STOP = 3;
    private MediaPlayer mediaPlayer;
    private int state;

    public SongPlayerManager() {
        state = PLAYER_IDEL;
    }

    public void setup(String path) {
        try {
            state = PLAYER_IDEL;
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(path);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnPreparedListener(prepared);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private MediaPlayer.OnPreparedListener prepared = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mediaPlayer) {
            state = PLAYER_PREPARED;
            play();
        }
    };

    public void play() {
        if (state == PLAYER_PREPARED) {
            mediaPlayer.start();
        }
        state = PLAYER_PLAYING;
    }

    public void pause() {
        if (state == PLAYER_PLAYING) {
            mediaPlayer.pause();
            state = PLAYER_PAUSE;
        }
    }

    public void resume() {
        if (state == PLAYER_PAUSE) {
            mediaPlayer.start();
            state = PLAYER_PLAYING;
        }
    }

    public int getState() {
        return state;
    }
}
