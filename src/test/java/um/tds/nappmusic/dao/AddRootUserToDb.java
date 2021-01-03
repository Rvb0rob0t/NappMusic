package um.tds.nappmusic.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import um.tds.nappmusic.domain.Playlist;
import um.tds.nappmusic.domain.Song;
import um.tds.nappmusic.domain.User;

public class AddRootUserToDb {
  private static DaoFactory factory;

  private static Song fakeSong() {
    List<String> styles = new ArrayList<>(Arrays.asList("Pop", "Rock", "Jazz"));
    return new Song("fake title", "fake author", styles, "NO_FILE_PATH", 0);
  }

  private static Playlist fakePlaylist() {
    List<Song> songs = new ArrayList<>();
    songs.add(fakeSong());
    return new Playlist("Root's playlist 0", songs);
  }

  public static void main(String[] args) throws DaoException {
    factory = DaoFactory.getSingleton();
    User user =
        new User(
            "Benito",
            "Camelas",
            LocalDate.parse("2012-12-12"),
            "mail@edsa.resolucion.xyz",
            "root",
            "root",
            true);
    user.getPlaylists().add(fakePlaylist());
    user.updateRecent(fakeSong());
    Dao<User> userDao = factory.getUserDao();
    userDao.register(user);
  }
}
