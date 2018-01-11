package com.thanhclub.musicdemo.ui;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.thanhclub.musicdemo.R;
import com.thanhclub.musicdemo.adapter.SongAdpater;
import com.thanhclub.musicdemo.base.BaseSongFragment;

import java.util.ArrayList;
import java.util.List;


public class AllSongFragment extends BaseSongFragment {
    public static final String ACTION_ADD_FAVORITE = "com.thanhclub.musicdemo.ADD_FAVORITE";
    public static final String KEY_FAVORITE = "com.thanhclub.musicdemo.KEY_FAVORITE";
    public static final int REQUEST_READ_EXTERNAL = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initPermission();
        view = inflater.inflate(R.layout.fragment_songs, container, false);
        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_EXTERNAL: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults.length == 1) {
                    songs = songManager.getSongs();
                    initAdapterSong();
                } else {
                    songs = null;
                    initPermission();
                }
                break;
            }

        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void initPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL);
            }
        }
    }

    @Override
    protected void initAdapterSong() {
        songAdpater = new SongAdpater(getActivity(), songs);
        recycler.setAdapter(songAdpater);
        songAdpater.setOnItemClickListener(this);
        songAdpater.setOnLongItemClickListener(this);
    }

    @Override
    public void onRecyclerItemClick(int posion) {
        service.initSong(songs);
        service.playSong(posion);
    }

    @Override
    public void OnLongItemClick(int postion) {
        createDialog("Bạn có muốn thêm " + songs.get(postion).getTitle() + " vào danh sách yêu thích", postion);
    }

    private void createDialog(String content, final int position) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setCancelable(true);
        alert.setTitle("Thông báo");
        alert.setMessage(content);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                databaseManager.insertFavorite(songs.get(position));
                songAdpater.notifyDataSetChanged();
                dialogInterface.dismiss();
                Intent intent = new Intent();
                intent.setAction(ACTION_ADD_FAVORITE);
                intent.putExtra(KEY_FAVORITE, "ok");
                getActivity().sendBroadcast(intent);
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
}
