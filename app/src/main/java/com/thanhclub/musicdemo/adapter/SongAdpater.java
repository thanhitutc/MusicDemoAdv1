package com.thanhclub.musicdemo.adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.thanhclub.musicdemo.R;
import com.thanhclub.musicdemo.interfaces.OnItemClickListener;
import com.thanhclub.musicdemo.interfaces.OnLongItemClickListener;
import com.thanhclub.musicdemo.manager.DatabaseManager;
import com.thanhclub.musicdemo.model.Song;

import java.util.List;

import static com.thanhclub.musicdemo.R.drawable.favotite;

public class SongAdpater extends RecyclerView.Adapter<SongAdpater.SongViewHolder> {
    private List<Song> songs;
    private Context context;
    private OnItemClickListener onItemClickListener;
    private OnLongItemClickListener onLongItemClickListener;
    private LayoutInflater inflater;
    private DatabaseManager databaseManager;

    public SongAdpater(Context context, List<Song> songs) {
        this.songs = songs;
        this.context = context;
        inflater = LayoutInflater.from(context);
        databaseManager = new DatabaseManager(context);
    }


    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_song, parent, false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SongViewHolder holder, int position) {
        holder.txtTitle.setText(songs.get(position).getTitle());
        holder.txtSinger.setText(songs.get(position).getSinger());
    }

    @Override
    public int getItemCount() {
        if (songs == null) {
            return 0;
        }
        return songs.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnLongItemClickListener(OnLongItemClickListener onLongItemClickListener) {
        this.onLongItemClickListener = onLongItemClickListener;
    }

    public class SongViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle;
        private TextView txtSinger;
        // private ImageView btnFav;

        public SongViewHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.txt_title);
            txtSinger = (TextView) itemView.findViewById(R.id.txt_singer);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onRecyclerItemClick(getAdapterPosition());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    onLongItemClickListener.OnLongItemClick(getAdapterPosition());
                    return true;
                }
            });
        }


    }
}
