package um.tds.nappmusic.domain;

import java.util.ArrayList;
import java.util.List;
import um.tds.nappmusic.dao.Identifiable;

public class Playlist implements Identifiable {
  private int id;
  private String name;
  private ArrayList<Song> songs;

  public Playlist() {}

  public Playlist(String name, ArrayList<Song> songs) {
    this.name = name;
    this.songs = songs;
  }

  public boolean add(Song song) {
    return songs.add(song);
  }

  public boolean remove(Song song) {
    return songs.remove(song);
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
