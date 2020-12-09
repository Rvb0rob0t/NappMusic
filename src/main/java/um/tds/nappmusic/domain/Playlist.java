package um.tds.nappmusic.domain;

import java.util.ArrayList;
import java.util.List;
import um.tds.nappmusic.dao.Identifiable;

public class Playlist implements Identifiable {
  public static final int RECENTLY_PLAYED_LIST_SIZE = 10;

  private int id;
  private String name;
  private ArrayList<Song> songs;

  public Playlist() {}

  public Playlist(String name) {
    this.name = name;
    this.songs = new ArrayList<Song>();
  }

  public Playlist(String name, ArrayList<Song> songs) {
    this.name = name;
    this.songs = songs;
  }

  public int size() {
    return songs.size();
  }

  public boolean add(Song song) {
    return songs.add(song);
  }

  public void add(int index, Song song) {
    songs.add(index, song);
  }

  public boolean remove(Song song) {
    return songs.remove(song);
  }

  public void remove(int index) {
    songs.remove(index);
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int getSize() {
    return songs.size();
  }

  public ArrayList<Song> getSongs() {
    return songs;
  }

  public Song getSong(int index) {
    return songs.get(index);
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setSongs(List<Song> songs) {
    this.songs = new ArrayList<Song>(songs);
  }
}
