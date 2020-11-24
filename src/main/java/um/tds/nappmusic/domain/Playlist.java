package um.tds.nappmusic.domain;

import java.util.ArrayList;

public class Playlist {
    private String name;
    private ArrayList<Song> songs;

    public Playlist(String name, ArrayList<Song> songs) {
        this.name = name;
        this.songs = songs;
    }

    public boolean add(Song cancion) {
        return songs.add(cancion);
    }

    public String getName() {
        return name;
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public void setName(String name) {
        this.name = name;
    }
}

