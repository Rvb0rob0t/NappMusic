package um.tds.nappmusic.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import um.tds.nappmusic.dao.Dao;
import um.tds.nappmusic.dao.DaoException;
import um.tds.nappmusic.dao.DaoFactory;

public class SongCatalog {
  public static final int MOST_PLAYED_SIZE = 10;
  private static SongCatalog singleton = null;
  private List<Song> songList;
  private HashMap<String, ArrayList<Song>> songsByAuthor;
  private HashMap<String, Integer> numSongsPerStyle;

  public static SongCatalog getSingleton() throws DaoException {
    if (singleton == null) {
      singleton = new SongCatalog();
    }
    return singleton;
  }

  private SongCatalog() throws DaoException {
    songList = new LinkedList<Song>();
    songsByAuthor = new HashMap<String, ArrayList<Song>>();
    numSongsPerStyle = new HashMap<String, Integer>();

    Dao<Song> songDao = DaoFactory.getSingleton().getSongDao();
    List<Song> songs = songDao.getAll();
    songs.forEach(song -> this.addSong(song));
  }

  public List<Song> getAllSongs() {
    return songList;
  }

  public List<String> getAllStyles() {
    return numSongsPerStyle.keySet().stream().collect(Collectors.toList());
  }

  public Playlist getSongsByAuthor(String author) {
    return new Playlist("Songs of " + author, songsByAuthor.get(author));
  }

  public Song getSong(String title, String author) {
    ArrayList<Song> songs = songsByAuthor.get(author);
    if (songs == null) {
      System.out.println(author + ": " + title);
      return null;
    }
    Optional<Song> result = songs.stream().filter(s -> s.getTitle().equals(title)).findAny();
    return result.orElse(null);
  }

  public Playlist searchSongsByTitle(String pattern) {
    List<Song> result =
        getAllSongs().stream()
            .filter(song -> (song.getTitle().contains(pattern)))
            .collect(Collectors.toCollection(ArrayList::new));
    return new Playlist("Search Results for " + pattern, result);
  }

  public List<Playlist> searchSongsByAuthor(String pattern) {
    List<Playlist> result = new ArrayList<Playlist>();
    for (HashMap.Entry<String, ArrayList<Song>> entry : songsByAuthor.entrySet()) {
      String author = entry.getKey();
      if (author.contains(pattern)) {
        result.add(new Playlist("Songs of " + author, entry.getValue()));
      }
    }
    return result;
  }

  public Playlist searchSongsByTitleAndAuthor(String titleSubstring, String authorSubstring) {
    List<Song> result =
        getAllSongs().stream()
            .filter(
                song ->
                    song.getAuthor().contains(authorSubstring)
                        && song.getTitle().contains(titleSubstring))
            .collect(Collectors.toCollection(ArrayList::new));
    return new Playlist("Search Results", result);
  }

  public Playlist searchSongsBy(String titleSubstring, String authorSubstring, String style) {
    List<Song> result =
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
    songList.add(song);
    songsByAuthor.computeIfAbsent(song.getAuthor(), k -> new ArrayList<Song>()).add(song);
    song.getStyles()
        .forEach(
            style -> {
              int currentNum = numSongsPerStyle.computeIfAbsent(style, k -> 0);
              numSongsPerStyle.put(style, currentNum + 1);
            });
  }

  public void removeSong(Song song) {
    songList.remove(song);
    List<Song> sameAuthor = songsByAuthor.get(song.getAuthor());
    sameAuthor.remove(song);
    if (sameAuthor.isEmpty()) {
      songsByAuthor.remove(song.getAuthor());
    }
    song.getStyles()
        .forEach(
            style -> {
              int currentNum = numSongsPerStyle.get(style);
              if (currentNum <= 1) {
                numSongsPerStyle.remove(style);
              } else {
                numSongsPerStyle.put(style, currentNum - 1);
              }
            });
  }

  /**
   * Get the {@value #MOST_PLAYED_SIZE} most played songs.
   *
   * @return A playlist with the {@value #MOST_PLAYED_SIZE} most played songs
   */
  public Playlist getMostPlayedSongs() {
    List<Song> result =
        getAllSongs().stream()
            .sorted((song1, song2) -> (song2.getNumPlays() - song1.getNumPlays()))
            .limit(MOST_PLAYED_SIZE)
            .collect(Collectors.toCollection(ArrayList::new));

    return new Playlist("Most played songs", result);
  }
}
