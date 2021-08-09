package com.example.musicplayer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MyViewHolder> {

    private final List<MusicFile> musicFileList;
    private Context context;
    private String artistName;

    MusicAdapter(Context context, List<MusicFile> musicFileList, String artistName) {
        this.context = context;
        this.musicFileList = musicFileList;
        this.artistName = artistName;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvSongTitle.setText(musicFileList.get(position).getTitle());
        System.out.println(musicFileList.get(position).getTitle());

        byte[] image = musicFileList.get(position).getImage();
        if(image!=null) {
            Glide.with(context).asBitmap().load(image).into(holder.ivSongImage);
        }
        else {
            holder.ivSongImage.setImageResource(R.drawable.ic_baseline_music_note);
        }

        //Todo: move to detail activity on item click

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("artist", artistName);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return musicFileList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvSongTitle;
        ImageView ivSongImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSongTitle = itemView.findViewById(R.id.tvSongTitle);
            ivSongImage = itemView.findViewById(R.id.ivSongImage);
        }
    }
}
