package um.tds.nappmusic.gui.cards;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class HomePanel extends JPanel {

  /**
   * .
   */
  public HomePanel() {
    this.setLayout(new BorderLayout());
    JLabel homeLabel = new JLabel("Bienvenidos a NappMusic");
    homeLabel.setFont(new Font("Arial", Font.PLAIN, 30));
    homeLabel.setHorizontalAlignment(JLabel.CENTER);
    homeLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
    this.add(homeLabel);
  }
}
