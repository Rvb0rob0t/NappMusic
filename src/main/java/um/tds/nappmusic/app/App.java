package um.tds.nappmusic.app;

import java.awt.EventQueue;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import um.tds.nappmusic.controller.Controller;
import um.tds.nappmusic.dao.DaoException;
import um.tds.nappmusic.dao.DaoFactory;
import um.tds.nappmusic.domain.SongCatalog;
import um.tds.nappmusic.domain.UserCatalog;
import um.tds.nappmusic.gui.LoginWindow;

public class App {
  public static final String RESOURCES_PATH = "src/main/resources/";
  public static final String NAME = "NappMusic";
  public static final int MINIMUM_AGE = 10;
  public static final int BASE_PRICE = 1899; // 18.99

  /** Launch the application. */
  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Throwable e) {
      e.printStackTrace();
    }
    EventQueue.invokeLater(
        () -> {
          try {
            UserCatalog userCatalog = UserCatalog.getSingleton();
            SongCatalog songCatalog = SongCatalog.getSingleton();
            DaoFactory factory = DaoFactory.getSingleton();
            Controller.getSingleton(userCatalog, songCatalog, factory);
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
