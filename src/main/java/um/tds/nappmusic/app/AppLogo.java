package um.tds.nappmusic.app;

import javax.swing.ImageIcon;

public class AppLogo {
  private static final String LOGO_PATH = App.RESOURCES_PATH + "Logo.png";

  public static ImageIcon get() {
    return new ImageIcon(LOGO_PATH);
  }
}
