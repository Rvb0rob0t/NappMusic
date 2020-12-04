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

  public boolean add(Song cancion) {
    return songs.add(cancion);
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public ArrayList<Song> getSongs() {
    return songs;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setSongs(List<Song> songs) {
    this.songs = new ArrayList(songs);
  }
}
