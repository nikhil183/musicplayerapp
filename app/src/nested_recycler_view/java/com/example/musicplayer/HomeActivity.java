package com.example.musicplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

    Toolbar toolbar;
    ArtistAdapter artistAdapter;
    public static final int REQUEST_CODE = 1;
    public static Map<String, ArrayList<MusicFile>> artistWiseSongs;
    public static List<String> artists = new ArrayList<>();
    RecyclerView rvArtist;
    ImageView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initViews();
        getPermission();
        setValuesInRecyclerView();
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        search = findViewById(R.id.search);
        search.setOnClickListener(this);
        rvArtist = findViewById(R.id.rvArtistList);
        rvArtist.setHasFixedSize(true);
    }

    private void getPermission() {
        if( ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(HomeActivity.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
        }

        else {
            readAllMusicFiles(this);
        }
    }



    private void setValuesInRecyclerView() {
        //if(musicFileList.size() > 0) {
            artistAdapter = new ArtistAdapter(getApplicationContext(), artistList());
            rvArtist.setAdapter(artistAdapter);
            rvArtist.setLayoutManager(new LinearLayoutManager(this));
        //}
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE) {
            if( grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(HomeActivity.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
            }

            else {
                readAllMusicFiles(this);
            }
        }
    }

    public void readAllMusicFiles(Context context) {
        ArrayList<MusicFile> songList = new ArrayList<>();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        String[] projection = { MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ALBUM_ID};

        Cursor cursor = context.getContentResolver().query(uri, projection,
                null, null, MediaStore.Audio.Media.ARTIST);

        artistWiseSongs = new HashMap<>();

        if ( cursor != null ) {
            while( cursor.moveToNext()) {
                addToSongList(context, cursor);
            }
        }
    }

    private void addToSongList(Context context, Cursor cursor) {
        String album = cursor.getString(0);
        String artist = cursor.getString(1);
        String data = cursor.getString(2);
        String title = cursor.getString(3);
        Long albumId = cursor.getLong(4);

        byte[] songImage = getSongImage(data);
        MusicFile musicFile = new MusicFile(album, artist, data, title, songImage);

        artist = processArtist(artist);
        if(artistWiseSongs.containsKey(artist)) {
            artistWiseSongs.get(artist).add(musicFile);
        }

        else {
            artists.add(artist);
            artistWiseSongs.put(artist, new ArrayList<>());
            artistWiseSongs.get(artist).add(musicFile);
        }
    }

    private byte[] getSongImage(String uri) {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(uri);
        byte[] art = mediaMetadataRetriever.getEmbeddedPicture();
        mediaMetadataRetriever.release();
        return art;
    }

    private String processArtist(String artist) {
        return artist.split(",")[0];
    }

    private List<Artist> artistList() {
        List<Artist> artistList = new ArrayList<>();
        Collections.sort(artists);
        for(int i=0; i<artists.size(); i++) {
            artistList.add(new Artist(artists.get(i), songList(i)));
        }
        return artistList;
    }

    private List<MusicFile> songList(int i) {
        List<MusicFile> musicFileList = new ArrayList<>();
        return artistWiseSongs.get(artists.get(i));
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }
}