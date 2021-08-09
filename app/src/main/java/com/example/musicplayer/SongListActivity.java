package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class SongListActivity extends AppCompatActivity {

    Toolbar toolbar;
    ArrayList<MusicFile> songList = new ArrayList<>();
    RecyclerView rvSongList;
    SongAdapter songAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);

        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        rvSongList = findViewById(R.id.rvSongList);
        rvSongList.setHasFixedSize(true);

        Intent intent = getIntent();
        String artist = intent.getStringExtra("artist");
        songList = HomeActivity.artistWiseSongs.get(artist);

        songAdapter = new SongAdapter(songList, getApplicationContext(), artist);
        rvSongList.setAdapter(songAdapter);
        rvSongList.setLayoutManager(new LinearLayoutManager(this));
    }
}