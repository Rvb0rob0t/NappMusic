package um.tds.nappmusic.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import um.tds.nappmusic.dao.DaoException;
import um.tds.nappmusic.dao.DaoFactory;

public class SongCatalog {
  public static final int MOST_PLAYED_SIZE = 10;
  private static SongCatalog singleton = null;
  private DaoFactory factory;
  private HashMap<String, ArrayList<Song>> songsByAuthor;

  public static SongCatalog getSingleton() {
    if (singleton == null) {
      singleton = new SongCatalog();
    }
    return singleton;
  }

  private SongCatalog() {
    try {
      factory = DaoFactory.getSingleton();
      List<Song> songs = factory.getSongDao().getAll();
      songsByAuthor = new HashMap<String, ArrayList<Song>>();
      for (Song song : songs) {
        songsByAuthor.computeIfAbsent(song.getAuthor(), k -> new ArrayList<Song>()).add(song);
      }
    } catch (DaoException e) {
      // TODO what do we do?
      e.printStackTrace();
    }
  }

  public List<Song> getAllSongs() {
    List<Song> all = new LinkedList<Song>();
    for (ArrayList<Song> authorSongs : songsByAuthor.values()) {
      all.addAll(authorSongs);
    }
    return all;
  }

  public Playlist getSongsByAuthor(String author) {
    return new Playlist("Songs of " + author, new ArrayList<Song>(songsByAuthor.get(author)));
  }

  public Song getSong(String title, String author) {
    Optional<Song> result =
        songsByAuthor.get(author).stream().filter(s -> s.getTitle().equals(title)).findAny();
    return result.orElse(null);
  }

  public Playlist searchSongsByTitle(String pattern) {
    ArrayList<Song> result =
        getAllSongs().stream()
            .filter(song -> (song.getTitle().contains(pattern)))
            .collect(Collectors.toCollection(ArrayList::new));
    return new Playlist("Search Results for " + pattern, result);
  }

  public List<Playlist> searchSongsByAuthor(String pattern) {
    ArrayList<Playlist> result = new ArrayList<Playlist>();
    for (HashMap.Entry<String, ArrayList<Song>> entry : songsByAuthor.entrySet()) {
      String author = entry.getKey();
      if (author.contains(pattern)) {
        result.add(new Playlist("Songs of " + author, new ArrayList<Song>(entry.getValue())));
      }
    }
    return result;
  }

  public Playlist searchSongsBy(String titleSubstring, String authorSubstring, String style) {
    ArrayList<Song> result =
        getAllSongs().stream()
            .filter(
                song ->
                    song.getStyles().stream().anyMatch(s -> s.equals(style))
                        && song.getAuthor().contains(authorSubstring)
                        && song.getTitle().contains(titleSubstring))
            .collect(Collectors.toCollection(ArrayList::new));

    return new Playlist("Search Results", result);
  }

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

  /**
   * Get the {@value #MOST_PLAYED_SIZE} most played songs.
   *
   * @return A playlist with the {@value #MOST_PLAYED_SIZE} most played songs
   */
  public Playlist getMostPlayedSongs() {
    ArrayList<Song> result =
        getAllSongs().stream()
            .sorted((song1, song2) -> (song2.getNumPlays() - song1.getNumPlays()))
            .limit(MOST_PLAYED_SIZE)
            .collect(Collectors.toCollection(ArrayList::new));

    return new Playlist("Most played songs", result);
  }
}
