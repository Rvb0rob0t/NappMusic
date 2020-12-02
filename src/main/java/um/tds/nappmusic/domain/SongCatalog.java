package um.tds.nappmusic.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import um.tds.nappmusic.dao.DaoException;
import um.tds.nappmusic.dao.DaoFactory;

public class SongCatalog {
  private static SongCatalog singleton = null;
  private DaoFactory factory;
  private HashMap<String, ArrayList<Song>> songsByAuthor;

  public static SongCatalog getSingleton() {
    if (singleton == null) singleton = new SongCatalog();
    return singleton;
  }

  private SongCatalog() {
    try {
      factory = DaoFactory.getSingleton();
      List<Song> songs = factory.getSongDao().getAll();
      songsByAuthor = new HashMap();
      for (Song song : songs) {
        songsByAuthor.computeIfAbsent(song.getAuthor(), k -> new ArrayList()).add(song);
      }
    } catch (DaoException e) {
      // TODO what do we do?
      e.printStackTrace();
    }
  }

  public List<Song> getAllSongs() {
    List<Song> all = new LinkedList();
    for (ArrayList<Song> authorSongs : songsByAuthor.values()) {
      all.addAll(authorSongs);
    }
    return all;
  }

  public Playlist getSongsByAuthor(String author) {
    return new Playlist("Songs of " + author, new ArrayList<Song>(songsByAuthor.get(author)));
  }

  public Playlist searchSongsByTitle(String pattern) {
    ArrayList<Song> result =
        getAllSongs().stream()
            .filter(song -> (song.getTitle().contains(pattern)))
            .collect(Collectors.toCollection(ArrayList::new));
    return new Playlist("Search Results for " + pattern, result);
  }

  public List<Playlist> searchSongsByAuthor(String pattern) {
    ArrayList<Playlist> result = new ArrayList();
    for (HashMap.Entry<String, ArrayList<Song>> entry : songsByAuthor.entrySet()) {
      String author = entry.getKey();
      if (author.contains(pattern)) {
        result.add(new Playlist("Songs of " + author, new ArrayList<Song>(entry.getValue())));
      }
    }
    return result;
  }

  // TODO songs to dao?
  public void addSong(Song song) {
    songsByAuthor.computeIfAbsent(song.getAuthor(), k -> new ArrayList<Song>()).add(song);
  }

  public void removeSong(Song song) {
    ArrayList<Song> sameAuthor = songsByAuthor.get(song.getAuthor());
    sameAuthor.remove(song);
    if (sameAuthor.isEmpty()) {
      songsByAuthor.remove(song.getAuthor());
    }
  }
}
