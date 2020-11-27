package um.tds.nappmusic.gui.cards;

import java.awt.BorderLayout;
import javax.swing.JList;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PlaylistsPanel extends JPanel {

  /**
   * .
   */
  public PlaylistsPanel() {
    JPanel playlistsPanel = new JPanel(new BorderLayout());

    JList list = new JList();
    playlistsPanel.add(list, BorderLayout.WEST);
  }
}
