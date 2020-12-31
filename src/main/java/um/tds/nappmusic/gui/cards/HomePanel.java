package um.tds.nappmusic.gui.cards;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import um.tds.nappmusic.app.App;
import um.tds.nappmusic.app.AppFonts;

public class HomePanel {
  JPanel mainPanel;

  /** . */
  public HomePanel() {
    mainPanel = new JPanel(new BorderLayout());
    JLabel homeLabel = new JLabel("Welcome to " + App.NAME);
    homeLabel.setFont(AppFonts.HOMEPAGE_FONT);
    homeLabel.setHorizontalAlignment(JLabel.CENTER);
    homeLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
    mainPanel.add(homeLabel);
  }

  public JPanel getPanel() {
    return mainPanel;
  }
}
