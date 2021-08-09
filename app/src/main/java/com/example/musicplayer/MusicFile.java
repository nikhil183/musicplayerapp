package com.example.musicplayer;

import android.graphics.Bitmap;

public class MusicFile {
    private String path;
    private String title;
    private String artist;
    private String album;
    private byte[] image;

    public MusicFile(String album, String artist, String path, String title, byte[] image) {
        this.path = path;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.image = image;
    }

    public byte[] getImage() {
        return image;
    }

    public String getPath() {
        return path;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

}
