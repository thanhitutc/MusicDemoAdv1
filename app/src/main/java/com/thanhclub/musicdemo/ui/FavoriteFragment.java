package com.thanhclub.musicdemo.ui;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thanhclub.musicdemo.R;
import com.thanhclub.musicdemo.adapter.SongAdpater;
import com.thanhclub.musicdemo.base.BaseSongFragment;

public class FavoriteFragment extends BaseSongFragment {
    private BroadcastReceiver broadcastReceiver;
    private IntentFilter intentFilter;

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(broadcastReceiver, intentFilter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_favorite_song, container, false);
        receiveBroadCast();
        return view;
    }

    private void receiveBroadCast() {
        intentFilter = new IntentFilter();
        intentFilter.addAction(AllSongFragment.ACTION_ADD_FAVORITE);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(AllSongFragment.ACTION_ADD_FAVORITE)) {
                    initAdapterSong();
                }
            }
        };
    }

    @Override
    protected void initAdapterSong() {
        songs = databaseManager.getFavoriteSong();
        songAdpater = new SongAdpater(getActivity(), songs);
        recycler.setAdapter(songAdpater);
        songAdpater.setOnLongItemClickListener(this);
        songAdpater.setOnItemClickListener(this);
    }

    @Override
    public void onRecyclerItemClick(int posion) {
        service.initSong(songs);
        service.playSong(posion);
    }

    @Override
    public void OnLongItemClick(int postion) {
        createDialog("Bạn muốn xóa " + songs.get(postion).getTitle() + " khỏi danh sách yêu thích?", postion);
    }

    private void createDialog(String content, final int position) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setCancelable(true);
        alert.setTitle("Thông báo");
        alert.setMessage(content);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                databaseManager.deleteSong(songs.get(position).getId());
                songs.remove(position);
                songAdpater.notifyDataSetChanged();
                dialogInterface.dismiss();
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alert.create().show();
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(broadcastReceiver);
    }
}
