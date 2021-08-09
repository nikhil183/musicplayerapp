package com.example.musicplayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder> {

    private List<Artist> artistList;
    private Context context;

    public ArtistAdapter(Context context, List<Artist> artistList) {
        this.artistList = artistList;
        this.context = context;
    }

    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.artist_list, parent, false);
        return new ArtistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistViewHolder holder, int position) {
        Artist artist = artistList.get(position);
        holder.tvArtistName.setText(artist.getArtistName());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(holder.rvSongList.getContext(), LinearLayoutManager.HORIZONTAL, false);

        MusicAdapter musicAdapter = new MusicAdapter(context, artist.getMusicFiles(), artist.getArtistName());
        holder.rvSongList.setLayoutManager(layoutManager);
        holder.rvSongList.setAdapter(musicAdapter);
        holder.rvSongList.setHasFixedSize(true);
    }

    @Override
    public int getItemCount() {
        return artistList.size();
    }

    public class ArtistViewHolder extends RecyclerView.ViewHolder {

        private TextView tvArtistName;
        private RecyclerView rvSongList;

        public ArtistViewHolder(View view) {
            super(view);

            tvArtistName = view.findViewById(R.id.tvArtist);
            rvSongList = view.findViewById(R.id.rvSongList);
        }
    }
}
