package com.example.musicplayer;

import java.util.List;

public class Artist {
    private String artistName;
    private List<MusicFile> musicFileList;

    public Artist(String artistName, List<MusicFile> musicFileList) {
        this.artistName = artistName;
        this.musicFileList = musicFileList;
    }

    public String getArtistName() {
        return artistName;
    }

    public List<MusicFile> getMusicFiles() {
        return musicFileList;
    }
}


