package um.tds.nappmusic.gui.cards;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import um.tds.nappmusic.gui.MusicPlayer;

public class RecentlyPlayedPane {
  JScrollPane scrollPane;

  /**
   * .
   *
   * @param musicPlayer
   */
  public RecentlyPlayedPane(MusicPlayer musicPlayer) {
    JTable table = new JTable();
    scrollPane = new JScrollPane(table);
  }

  public JScrollPane getScrollPane() {
    return scrollPane;
  }
}
