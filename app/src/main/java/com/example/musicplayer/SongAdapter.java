package com.example.musicplayer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {

    private final List<MusicFile> musicFileList;
    private Context context;
    private String artistName;

    public SongAdapter(List<MusicFile> musicFileList, Context context, String artistName) {
        this.musicFileList = musicFileList;
        this.context = context;
        this.artistName = artistName;
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_items, parent, false);
        return new SongAdapter.SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
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

    public class SongViewHolder extends RecyclerView.ViewHolder {
        TextView tvSongTitle;
        ImageView ivSongImage;
        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSongTitle = itemView.findViewById(R.id.tvSongTitle);
            ivSongImage = itemView.findViewById(R.id.ivSongImage);
        }
    }
}
