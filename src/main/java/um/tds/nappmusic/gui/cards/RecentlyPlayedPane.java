package um.tds.nappmusic.gui.cards;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import um.tds.nappmusic.controller.Controller;
import um.tds.nappmusic.domain.Playlist;
import um.tds.nappmusic.gui.MusicPlayer;
import um.tds.nappmusic.gui.tables.PlaylistTable;

public class RecentlyPlayedPane {
  private JPanel mainPanel;
  private JScrollPane scrollPane;
  private PlaylistTable songTable;

  /**
   * .
   *
   * @param musicPlayer
   */
  public RecentlyPlayedPane(MusicPlayer musicPlayer) {
    Playlist recentPlaylist = Controller.getSingleton().getUserRecentlyPlayedSongs();
    mainPanel = new JPanel(new BorderLayout());
    JScrollPane scrollPane = new JScrollPane();
    songTable = new PlaylistTable(musicPlayer, recentPlaylist);

    mainPanel.add(scrollPane, BorderLayout.CENTER);
    scrollPane.setViewportView(songTable.getTable());
  }

  public JPanel getPanel() {
    return mainPanel;
  }

  public void revalidate() {
    Playlist recentPlaylist = Controller.getSingleton().getUserRecentlyPlayedSongs();
    songTable.setPlaylist(recentPlaylist);
    scrollPane.setViewportView(songTable.getTable());
    mainPanel.revalidate();
  }
}
