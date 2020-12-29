package um.tds.nappmusic.gui.cards;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import um.tds.nappmusic.controller.Controller;
import um.tds.nappmusic.domain.Playlist;
import um.tds.nappmusic.gui.MusicPlayer;
import um.tds.nappmusic.gui.tables.PlaylistTable;

public class RecentlyPlayedPanel {
  private JPanel mainPanel;
  private JScrollPane scrollPane;
  private PlaylistTable songTable;

  /**
   * .
   *
   * @param musicPlayer
   */
  public RecentlyPlayedPanel(MusicPlayer musicPlayer) {
    Playlist recentPlaylist = Controller.getSingleton().getUserRecentlyPlayedSongs();
    mainPanel = new JPanel(new BorderLayout());
    scrollPane = new JScrollPane();
    songTable = new PlaylistTable(musicPlayer, recentPlaylist);
    scrollPane.setViewportView(songTable.getTable());
    mainPanel.add(scrollPane, BorderLayout.CENTER);
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