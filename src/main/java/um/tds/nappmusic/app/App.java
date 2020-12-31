package um.tds.nappmusic.app;

import java.awt.EventQueue;
import java.awt.Font;
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

  private static void setFonts() {
    UIManager.put("Button.font", new Font("Tahoma", Font.PLAIN, 12));
    UIManager.put("ToggleButton.font", new Font("Tahoma", Font.PLAIN, 12));
    UIManager.put("RadioButton.font", new Font("Tahoma", Font.PLAIN, 12));
    UIManager.put("CheckBox.font", new Font("Tahoma", Font.PLAIN, 12));
    UIManager.put("ColorChooser.font", new Font("Tahoma", Font.PLAIN, 12));
    UIManager.put("ComboBox.font", new Font("Tahoma", Font.PLAIN, 12));
    UIManager.put("Label.font", new Font("Tahoma", Font.PLAIN, 12));
    UIManager.put("List.font", new Font("Tahoma", Font.PLAIN, 12));
    UIManager.put("MenuBar.font", new Font("Tahoma", Font.PLAIN, 12));
    UIManager.put("MenuItem.font", new Font("Tahoma", Font.PLAIN, 12));
    UIManager.put("RadioButtonMenuItem.font", new Font("Tahoma", Font.PLAIN, 12));
    UIManager.put("CheckBoxMenuItem.font", new Font("Tahoma", Font.PLAIN, 12));
    UIManager.put("Menu.font", new Font("Tahoma", Font.PLAIN, 12));
    UIManager.put("PopupMenu.font", new Font("Tahoma", Font.PLAIN, 12));
    UIManager.put("OptionPane.font", new Font("Tahoma", Font.PLAIN, 12));
    UIManager.put("Panel.font", new Font("Tahoma", Font.PLAIN, 12));
    UIManager.put("ProgressBar.font", new Font("Tahoma", Font.PLAIN, 12));
    UIManager.put("ScrollPane.font", new Font("Tahoma", Font.PLAIN, 12));
    UIManager.put("Viewport.font", new Font("Tahoma", Font.PLAIN, 12));
    UIManager.put("TabbedPane.font", new Font("Tahoma", Font.PLAIN, 12));
    UIManager.put("Table.font", new Font("Tahoma", Font.PLAIN, 12));
    UIManager.put("TableHeader.font", new Font("Tahoma", Font.PLAIN, 12));
    UIManager.put("TextField.font", new Font("Tahoma", Font.PLAIN, 12));
    UIManager.put("PasswordField.font", new Font("Tahoma", Font.PLAIN, 12));
    UIManager.put("TextArea.font", new Font("Tahoma", Font.PLAIN, 12));
    UIManager.put("TextPane.font", new Font("Tahoma", Font.PLAIN, 12));
    UIManager.put("EditorPane.font", new Font("Tahoma", Font.PLAIN, 12));
    UIManager.put("TitledBorder.font", new Font("Tahoma", Font.PLAIN, 12));
    UIManager.put("ToolBar.font", new Font("Tahoma", Font.PLAIN, 12));
    UIManager.put("ToolTip.font", new Font("Tahoma", Font.PLAIN, 12));
    UIManager.put("Tree.font", new Font("Tahoma", Font.PLAIN, 12));
  }

  /** Launch the application. */
  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      setFonts();
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
