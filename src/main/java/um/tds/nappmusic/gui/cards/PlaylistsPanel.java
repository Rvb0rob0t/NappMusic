package um.tds.nappmusic.gui.cards;

import java.awt.BorderLayout;
import javax.swing.JList;
import javax.swing.JPanel;
import um.tds.nappmusic.gui.MusicPlayer;

@SuppressWarnings("serial")
public class PlaylistsPanel extends JPanel {

  /**
   * .
   *
   * @param musicPlayer
   */
  public PlaylistsPanel(MusicPlayer musicPlayer) {
    JPanel playlistsPanel = new JPanel(new BorderLayout());

    JList list = new JList();
    playlistsPanel.add(list, BorderLayout.WEST);
  }
}
