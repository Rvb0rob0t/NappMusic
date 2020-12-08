package um.tds.nappmusic.dao.tds;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import um.tds.nappmusic.dao.DaoException;
import um.tds.nappmusic.dao.Identifiable;
import um.tds.nappmusic.domain.Playlist;
import um.tds.nappmusic.domain.Song;
import um.tds.nappmusic.domain.User;
import um.tds.nappmusic.domain.discounts.NoDiscount;

class PoolTests {
  private DaoFactory factory;

  void assertListsIdsMatch(List<? extends Identifiable> lhs, List<? extends Identifiable> rhs) {
    assertEquals(lhs.size(), rhs.size());
    for (int i = 0; i < lhs.size(); i++) {
      assertEquals(lhs.get(i).getId(), rhs.get(i).getId());
    }
  }

  @BeforeEach
  void initAll() {
    factory = new DaoFactory();
  }

  @Test
  void checkUserPool() {
    User user = new User();
    user.setName("Alberto");
    user.setSurname("Robles");
    user.setBirthDate(LocalDate.parse("1999-08-18"));
    user.setEmail("albertor@um.es");
    user.setUsername("Albertoc");
    user.setPassword("1234");
    user.setPremium(false);
    user.setDiscount(new NoDiscount());
    user.setPlaylists(new ArrayList<Playlist>());
    user.setRecent(new Playlist("Recent"));

    Pool<User> userDao = (Pool) factory.getUserDao();
    userDao.register(user);
    // The object is now in the pool, clear it
    // to try retrieving it from the database
    userDao.clear();
    try {
      User retrieved = userDao.get(user.getId());

      assertEquals(user.getName(), retrieved.getName());
      assertEquals(user.getSurname(), retrieved.getSurname());
      assertEquals(user.getBirthDate(), retrieved.getBirthDate());
      assertEquals(user.getEmail(), retrieved.getEmail());
      assertEquals(user.getUsername(), retrieved.getUsername());
      assertEquals(user.getPassword(), retrieved.getPassword());
      assertEquals(user.isPremium(), retrieved.isPremium());
      assertListsIdsMatch(user.getPlaylists(), retrieved.getPlaylists());
      assertEquals(user.getRecent().getId(), retrieved.getRecent().getId());
      userDao.delete(user);
    } catch (DaoException e) {
      e.printStackTrace();
      fail("Failed retrieving user");
    }
  }

  @Test
  void checkSongPool() {
    Song song = new Song();
    song.setTitle("SSNII");
    song.setAuthor("Ca Papanate");
    song.setStyles(new ArrayList<String>(Arrays.asList("Absolutely", "Original")));
    song.setFilePath("/home/useredsa/dotfiles/.config/kak/kakrc");
    song.setNumPlays(10);

    Pool<Song> songDao = (Pool) factory.getSongDao();
    songDao.register(song);
    songDao.clear();
    try {
      Song retrieved = songDao.get(song.getId());

      assertEquals(song.getTitle(), retrieved.getTitle());
      assertEquals(song.getAuthor(), retrieved.getAuthor());
      assertEquals(song.getStyles(), retrieved.getStyles());
      assertEquals(song.getFilePath(), retrieved.getFilePath());
      assertEquals(song.getNumPlays(), retrieved.getNumPlays());
      songDao.delete(song);
    } catch (DaoException e) {
      e.printStackTrace();
      fail("Failed retrieving song");
    }
  }

  @Test
  void checkPlaylistPool() {
    Song song = new Song();
    song.setTitle("SSNII");
    song.setAuthor("Ca Papanate");
    song.setStyles(new ArrayList<String>(Arrays.asList("Absolutely", "Original")));
    song.setFilePath("/home/useredsa/dotfiles/.config/kak/kakrc");
    song.setNumPlays(10);

    Playlist playlist = new Playlist();
    playlist.setName("Fall Out Boy");
    playlist.setSongs(Arrays.asList(song));

    Pool<Playlist> playlistDao = (Pool) factory.getPlaylistDao();
    playlistDao.register(playlist);
    playlistDao.clear();
    try {
      Playlist retrieved = playlistDao.get(playlist.getId());

      assertEquals(playlist.getName(), retrieved.getName());
      assertEquals(playlist.getSize(), retrieved.getSize());
      for (int i = 0; i < playlist.getSize(); i++) {
        assertEquals(playlist.getSong(i), retrieved.getSong(i));
      }
      assertListsIdsMatch(playlist.getSongs(), retrieved.getSongs());

      playlistDao.delete(playlist);
      Pool<Song> songDao = (Pool) factory.getSongDao();
      songDao.delete(song);
    } catch (DaoException e) {
      e.printStackTrace();
      fail("Failed retrieving playlist");
    }
  }
}
