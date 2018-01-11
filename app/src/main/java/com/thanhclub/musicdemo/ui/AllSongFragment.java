package com.thanhclub.musicdemo.ui;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.thanhclub.musicdemo.R;
import com.thanhclub.musicdemo.adapter.SongAdpater;
import com.thanhclub.musicdemo.base.BaseSongFragment;
import com.thanhclub.musicdemo.model.Song;

import java.util.List;

public class AllSongFragment extends BaseSongFragment {
    public static final String ACTION_ADD_FAVORITE = "com.thanhclub.musicdemo.ADD_FAVORITE";
    public static final String KEY_FAVORITE = "com.thanhclub.musicdemo.KEY_FAVORITE";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_songs, container, false);
        return view;
    }

    @Override
    protected void initAdapterSong() {
        songs = songManager.getSongs();
        songAdpater = new SongAdpater(getActivity(), songs);
        recycler.setAdapter(songAdpater);
        songAdpater.setOnItemClickListener(this);
        songAdpater.setOnLongItemClickListener(this);
    }

    @Override
    public void onRecyclerItemClick(int posion) {
        Toast.makeText(getActivity(), "click" + songs.get(posion).getTitle(), Toast.LENGTH_SHORT).show();
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

    @Override
    public void onResume() {
        super.onResume();
        Log.e("ALLSONG", "resume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("ALLSONG", "pause");
    }
}
