package um.tds.nappmusic.app;

import java.awt.Font;
import javax.swing.UIManager;

public class AppFonts {

  public static final Font APP_NAME_FONT = new Font("Tahoma", Font.PLAIN, 20);
  public static final Font CARD_SELECTOR_FONT = new Font("Arial Black", Font.BOLD, 16);
  public static final Font HOMEPAGE_FONT = new Font("Arial", Font.PLAIN, 30);

  public static void setFonts() {
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
}
