package com.thanhclub.musicdemo.base;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thanhclub.musicdemo.R;
import com.thanhclub.musicdemo.adapter.SongAdpater;
import com.thanhclub.musicdemo.interfaces.OnItemClickListener;
import com.thanhclub.musicdemo.interfaces.OnLongItemClickListener;
import com.thanhclub.musicdemo.manager.DatabaseManager;
import com.thanhclub.musicdemo.manager.SongManager;
import com.thanhclub.musicdemo.model.Song;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseSongFragment extends Fragment implements OnItemClickListener, OnLongItemClickListener {
    protected View view;
    protected RecyclerView recycler;
    protected SongAdpater songAdpater;
    protected RecyclerView.LayoutManager layoutManager;
    protected SongManager songManager;
    protected List<Song> songs;
    protected DatabaseManager databaseManager;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initView();
        initAdapterSong();
        super.onViewCreated(view, savedInstanceState);
    }

    protected void initView() {
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recycler = (RecyclerView)  view.findViewById(R.id.rc_view);
        recycler.setLayoutManager(layoutManager);
        songManager = new SongManager(getActivity());
        songs = new ArrayList<>();
        databaseManager = new DatabaseManager(getActivity());
    }

    protected abstract void initAdapterSong();


    @Override
    public abstract void onRecyclerItemClick(int posion);


}
