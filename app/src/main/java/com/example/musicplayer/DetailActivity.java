package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView ivSongImage;
    private TextView tvSongName, tvArtistName;
    private FloatingActionButton fbPlay;
    private int position;
    private ArrayList<MusicFile> musicFileList;
    private Uri uri;
    public static MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initViews();

        position = getIntent().getIntExtra("position", -1);
        musicFileList = HomeActivity.artistWiseSongs.get(getIntent().getStringExtra("artist"));
        setViews();

        if(musicFileList != null) {
            fbPlay.setImageResource(R.drawable.ic_baseline_pause);
            uri = Uri.parse(musicFileList.get(position).getPath());
        }

        if(mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        mediaPlayer = MediaPlayer.create(this, uri);
        startService(new Intent(this, MusicService.class));
    }

    private void initViews() {
        ivSongImage = findViewById(R.id.ivSongImage);
        tvSongName = findViewById(R.id.tvSong);
        tvArtistName = findViewById(R.id.tvArtist);
        fbPlay = findViewById(R.id.fbPlay);
        fbPlay.setOnClickListener(this);
    }

    private void setViews() {
        tvSongName.setText(musicFileList.get(position).getTitle());
        tvArtistName.setText(musicFileList.get(position).getArtist());
        byte[] image = musicFileList.get(position).getImage();
        if(image!=null) {
            Glide.with(getApplicationContext()).asBitmap().load(image).into(ivSongImage);
        }
        else {
            ivSongImage.setImageResource(R.drawable.ic_baseline_music_note);
        }
    }

    @Override
    public void onClick(View v) {
        if( mediaPlayer.isPlaying() ) {
            fbPlay.setImageResource(R.drawable.ic_baseline_play);
            mediaPlayer.pause();
        }
        else {
            fbPlay.setImageResource(R.drawable.ic_baseline_pause);
            mediaPlayer.start();
        }
    }
}