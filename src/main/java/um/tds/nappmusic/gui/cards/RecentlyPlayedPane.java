package um.tds.nappmusic.gui.cards;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import um.tds.nappmusic.gui.MusicPlayer;

@SuppressWarnings("serial")
public class RecentlyPlayedPane extends JScrollPane {

  /**
   * .
   *
   * @param musicPlayer
   */
  public RecentlyPlayedPane(MusicPlayer musicPlayer) {
    JTable table = new JTable();
    this.setViewportView(table);
  }
}
