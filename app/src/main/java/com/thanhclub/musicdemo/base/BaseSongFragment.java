package com.thanhclub.musicdemo.base;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.thanhclub.musicdemo.R;
import com.thanhclub.musicdemo.adapter.SongAdpater;
import com.thanhclub.musicdemo.interfaces.OnItemClickListener;
import com.thanhclub.musicdemo.interfaces.OnLongItemClickListener;
import com.thanhclub.musicdemo.manager.DatabaseManager;
import com.thanhclub.musicdemo.manager.SongManager;
import com.thanhclub.musicdemo.model.Song;
import com.thanhclub.musicdemo.services.PlaySongService;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseSongFragment extends Fragment implements OnItemClickListener, OnLongItemClickListener {
    public static final String ACTION_START_SERVICE = "ACTION_START_SERVICE";
    protected View view;
    protected RecyclerView recycler;
    protected SongAdpater songAdpater;
    protected RecyclerView.LayoutManager layoutManager;
    protected SongManager songManager;
    protected List<Song> songs;
    protected DatabaseManager databaseManager;
    protected PlaySongService service;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initView();
        initAdapterSong();
        initService();
        super.onViewCreated(view, savedInstanceState);
    }

    protected void initService() {
        Intent intent = new Intent(getActivity(), PlaySongService.class);
        intent.setAction(ACTION_START_SERVICE);
        getActivity().startService(intent);
        getActivity().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    protected void initView() {
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recycler = (RecyclerView) view.findViewById(R.id.rc_view);
        recycler.setLayoutManager(layoutManager);
        songManager = new SongManager(getActivity());
        songs = new ArrayList<>();
        databaseManager = new DatabaseManager(getActivity());
    }

    protected abstract void initAdapterSong();

    @Override
    public abstract void onRecyclerItemClick(int posion);

    @Override
    public abstract void OnLongItemClick(int postion);

    protected ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            PlaySongService.PlayBinder binder = (PlaySongService.PlayBinder) iBinder;
            service = binder.getPlaySongService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unbindService(serviceConnection);
    }
}
