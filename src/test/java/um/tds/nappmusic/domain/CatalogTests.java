package um.tds.nappmusic.domain;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CatalogTests {
  private SongCatalog songCatalog;
  private ArrayList<Song> fakeSongs;

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

  @BeforeEach
  void fakeData() {
    songCatalog = SongCatalog.getSingleton();

    fakeSongs = new ArrayList();
    fakeSongs.add(
        new Song(
            "Title0",
            "Author0",
            new ArrayList<String>(Arrays.asList("Style0", "Style1", "Style2")),
            "/home/useredsa/Music/song0.mp4",
            0));
    fakeSongs.add(
        new Song(
            "Title1",
            "Author1",
            new ArrayList<String>(Arrays.asList("Style1", "Style2", "Style3")),
            "/home/useredsa/Music/song1.mp4",
            10));
    fakeSongs.add(
        new Song(
            "Title2",
            "Author2",
            new ArrayList<String>(Arrays.asList("Style2", "Style3", "Style4")),
            "/home/useredsa/Music/song2.mp4",
            20));
    fakeSongs.add(
        new Song(
            "Title3",
            "Author3",
            new ArrayList<String>(Arrays.asList("Style3", "Style4", "Style5")),
            "/home/useredsa/Music/song3.mp4",
            30));
    fakeSongs.add(
        new Song(
            "Title4",
            "Author4",
            new ArrayList<String>(Arrays.asList("Style4", "Style5", "Style0")),
            "/home/useredsa/Music/song4.mp4",
            40));
    fakeSongs.add(
        new Song(
            "Title5",
            "Author5",
            new ArrayList<String>(Arrays.asList("Style5", "Style0", "Style1")),
            "/home/useredsa/Music/song5.mp4",
            50));

    fakeSongs.forEach(s -> songCatalog.addSong(s));
  }

  @AfterEach
  void removeFakeData() {
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
}
