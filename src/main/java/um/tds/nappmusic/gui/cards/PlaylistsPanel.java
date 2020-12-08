package um.tds.nappmusic.gui.cards;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Optional;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import um.tds.nappmusic.controller.Controller;
import um.tds.nappmusic.domain.Playlist;
import um.tds.nappmusic.gui.MusicPlayer;
import um.tds.nappmusic.gui.lists.PlaylistJList;
import um.tds.nappmusic.gui.tables.PlaylistTable;

@SuppressWarnings("serial")
public class PlaylistsPanel extends MouseAdapter {
  private MusicPlayer musicPlayer;
  private JPanel mainPanel;
  private JPanel leftPanel;
  private JPanel rightPanel;
  private JScrollPane leftPanelScroll;
  private JScrollPane rightPanelScroll;
  private PlaylistJList playlistsList;
  private JLabel leftPaneLbl;

  /**
   * .
   *
   * @param musicPlayer
   */
  public PlaylistsPanel(MusicPlayer musicPlayer) {
    this.musicPlayer = musicPlayer;
    mainPanel = new JPanel(new BorderLayout(10, 10));
    leftPanel = new JPanel(new BorderLayout(10, 10));
    rightPanel = new JPanel(new BorderLayout(10, 10));
    leftPanelScroll = new JScrollPane();
    rightPanelScroll = new JScrollPane();
    List<Playlist> userPlaylists = Controller.getSingleton().getUserPlaylists();
    playlistsList = new PlaylistJList(userPlaylists);
    leftPaneLbl = new JLabel("Your Playlists");

    leftPanelScroll.setViewportView(playlistsList.getList());
    playlistsList.addMouseListener(this);
    updateDisplayedList(Optional.empty());

    leftPanel.add(leftPaneLbl, BorderLayout.NORTH);
    leftPanel.add(leftPanelScroll, BorderLayout.CENTER);
    rightPanel.add(rightPanelScroll, BorderLayout.CENTER);
    mainPanel.add(leftPanel, BorderLayout.WEST);
    mainPanel.add(rightPanel, BorderLayout.CENTER);
  }

  public void updateDisplayedList(Optional<Playlist> selected) {
    Playlist playlist =
        selected.isPresent() ? selected.get() : new Playlist("No Playlist Selected");
    PlaylistTable searchTable = new PlaylistTable(musicPlayer, playlist);
    rightPanelScroll.setViewportView(searchTable.getTable());
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    if (SwingUtilities.isLeftMouseButton(e)) {
      updateDisplayedList(playlistsList.getSelectedPlaylist());
    } else if (SwingUtilities.isRightMouseButton(e)) {
      // TODO do we want functionality?
    }
  }

  public JPanel getPanel() {
    return mainPanel;
  }
}
