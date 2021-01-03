package um.tds.nappmusic.domain;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import um.tds.nappmusic.dao.DaoException;

class CatalogTests {
  private static SongCatalog songCatalog;
  private static List<Song> fakeSongs;

  private void assertCollectionIsContained(List<Song> lhs, List<Song> rhs) {
    for (Song lsong : lhs) {
      if (!rhs.contains(lsong)) {
        System.err.println(
            "lhs: " + lhs.stream().map(song -> song.getTitle()).collect(Collectors.joining(" ")));
        ;
        System.err.println(
            "rhs: " + rhs.stream().map(song -> song.getTitle()).collect(Collectors.joining(" ")));
        ;
        fail("Right hand side collection does not contain " + lsong.getTitle());
      }
    }
  }

  @BeforeAll
  static void fakeData() {
    try {
      // These tests assume that the database is empty because of the singleton
      songCatalog = SongCatalog.getSingleton();
    } catch (DaoException e) {
      e.printStackTrace();
      fail("Failed SongCatalog initialization");
      return;
    }

    fakeSongs = new ArrayList<>();
    fakeSongs.add(
        new Song(
            "Title0",
            "Author0",
            Arrays.asList("Style0", "Style1", "Style2"),
            "/home/useredsa/Music/song0.mp4",
            0));
    fakeSongs.add(
        new Song(
            "Title1",
            "Author1",
            Arrays.asList("Style1", "Style2", "Style3"),
            "/home/useredsa/Music/song1.mp4",
            10));
    fakeSongs.add(
        new Song(
            "Title2",
            "Author2",
            Arrays.asList("Style2", "Style3", "Style4"),
            "/home/useredsa/Music/song2.mp4",
            20));
    fakeSongs.add(
        new Song(
            "Title3",
            "Author3",
            Arrays.asList("Style3", "Style4", "Style5"),
            "/home/useredsa/Music/song3.mp4",
            30));
    fakeSongs.add(
        new Song(
            "Title4",
            "Author4",
            Arrays.asList("Style4", "Style5", "Style0"),
            "/home/useredsa/Music/song4.mp4",
            40));
    fakeSongs.add(
        new Song(
            "Title5",
            "Author5",
            Arrays.asList("Style5", "Style0", "Style1"),
            "/home/useredsa/Music/song5.mp4",
            50));
    fakeSongs.add(
        new Song(
            "Title6",
            "Author6",
            Arrays.asList("Style6", "Style0", "Style1"),
            "/home/useredsa/Music/song6.mp4",
            60));
    fakeSongs.add(
        new Song(
            "Title7",
            "Author7",
            Arrays.asList("Style7", "Style0", "Style1"),
            "/home/useredsa/Music/song7.mp4",
            70));
    fakeSongs.add(
        new Song(
            "Title8",
            "Author8",
            Arrays.asList("Style8", "Style0", "Style1"),
            "/home/useredsa/Music/song8.mp4",
            80));
    fakeSongs.add(
        new Song(
            "Title9",
            "Author9",
            Arrays.asList("Style9", "Style0", "Style1"),
            "/home/useredsa/Music/song9.mp4",
            90));
    fakeSongs.add(
        new Song(
            "Title10",
            "Author10",
            Arrays.asList("Style10", "Style0", "Style1"),
            "/home/useredsa/Music/song10.mp4",
            100));
    fakeSongs.forEach(s -> songCatalog.addSong(s));
  }

  @AfterAll
  static void removeFakeData() {
    fakeSongs.forEach(s -> songCatalog.removeSong(s));
  }

  @Test
  void checkGetSongs() {
    List<Song> expected = fakeSongs;
    List<Song> returned = songCatalog.getAllSongs();
    assertCollectionIsContained(expected, returned);
  }

  @ParameterizedTest
  @ValueSource(strings = {"Author0", "Author1", "Author2", "Author3", "Author4", "Author5"})
  void getSongsByAuthor(String author) {
    List<Song> expected =
        fakeSongs.stream()
            .filter(song -> song.getAuthor().equals(author))
            .collect(Collectors.toList());
    List<Song> returned = songCatalog.getSongsByAuthor(author).getSongs();
    assertCollectionIsContained(expected, returned);
    assertCollectionIsContained(returned, expected);
  }

  @ParameterizedTest
  @ValueSource(strings = {"Title", "Title0", "itle5"})
  void checkSearchSongsByTitle(String pattern) {
    List<Song> expected =
        fakeSongs.stream()
            .filter(song -> song.getTitle().contains(pattern))
            .collect(Collectors.toList());
    List<Song> returned = songCatalog.searchSongsByTitle(pattern).getSongs();
    assertCollectionIsContained(expected, returned);
  }

  @ParameterizedTest
  @CsvSource(value = {"Title, Author0, Style0", "Title, Author, Style1", "itle5, Auth, Style1"})
  void checkSearchSongsBy(String titleSubstring, String authorSubstring, String style) {
    List<Song> expected =
        fakeSongs.stream()
            .filter(
                song ->
                    song.getStyles().stream().anyMatch(s -> s.equals(style))
                        && song.getAuthor().contains(authorSubstring)
                        && song.getTitle().contains(titleSubstring))
            .collect(Collectors.toList());
    List<Song> returned =
        songCatalog.searchSongsBy(titleSubstring, authorSubstring, style).getSongs();
    assertCollectionIsContained(expected, returned);
  }

  @Test
  void checkGetMostPlayedSongs() {
    List<Song> expected =
        fakeSongs.subList(
            Math.max(0, fakeSongs.size() - SongCatalog.MOST_PLAYED_SIZE), fakeSongs.size());
    List<Song> returned = songCatalog.getMostPlayedSongs().getSongs();
    assertCollectionIsContained(expected, returned);
  }
}
