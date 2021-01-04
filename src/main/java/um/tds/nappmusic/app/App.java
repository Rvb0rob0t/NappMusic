package um.tds.nappmusic.app;

import java.awt.EventQueue;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import um.tds.nappmusic.controller.Controller;
import um.tds.nappmusic.dao.DaoException;
import um.tds.nappmusic.dao.DaoFactory;
import um.tds.nappmusic.domain.Song;
import um.tds.nappmusic.domain.SongCatalog;
import um.tds.nappmusic.domain.User;
import um.tds.nappmusic.domain.UserCatalog;
import um.tds.nappmusic.gui.LoginWindow;

public class App {
  public static final String RESOURCES_PATH = "src/main/resources/";
  public static final String NAME = "NappMusic";
  public static final int MINIMUM_AGE = 10;
  public static final int BASE_PRICE = 1899; // 18.99

  public static void initializeState() throws DaoException {
    List<User> users = DaoFactory.getSingleton().getUserDao().getAll();
    List<Song> songs = DaoFactory.getSingleton().getSongDao().getAll();
    UserCatalog userCatalog = new UserCatalog(users);
    SongCatalog songCatalog = new SongCatalog(songs);
    DaoFactory factory = DaoFactory.getSingleton();
    Controller.getSingleton(userCatalog, songCatalog, factory);
  }

  /** Launch the application. */
  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      AppFonts.setFonts();
    } catch (Throwable e) {
      e.printStackTrace();
    }
    EventQueue.invokeLater(
        () -> {
          try {
            initializeState();
          } catch (DaoException e) {
            JOptionPane.showMessageDialog(
                null,
                "Initialization error",
                "The database is not available at the moment",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
          }

          try {
            LoginWindow window = new LoginWindow();
            window.showWindow();
          } catch (Exception e) {
            e.printStackTrace();
          }
        });
  }
}
