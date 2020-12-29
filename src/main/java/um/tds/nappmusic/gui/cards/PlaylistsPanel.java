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

public class PlaylistsPanel extends MouseAdapter {
  private Controller controller;

  private MusicPlayer musicPlayer;

  private JPanel mainPanel;
  private JPanel leftPanel;
  private JPanel rightPanel;
  private JScrollPane leftPanelScroll;
  private JScrollPane rightPanelScroll;

  private PlaylistJList playlistsList;
  private PlaylistTable playlistTable;

  private JLabel leftPaneLbl;

  /**
   * .
   *
   * @param musicPlayer
   * @param controller
   */
  public PlaylistsPanel(Controller controller, MusicPlayer musicPlayer) {
    this.controller = controller;

    this.musicPlayer = musicPlayer;
    mainPanel = new JPanel(new BorderLayout(10, 10));
    leftPanel = new JPanel(new BorderLayout(10, 10));
    rightPanel = new JPanel(new BorderLayout(10, 10));
    leftPanelScroll = new JScrollPane();
    rightPanelScroll = new JScrollPane();
    List<Playlist> userPlaylists = controller.getUserPlaylists();
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

  private void updateDisplayedList(Optional<Playlist> selected) {
    Playlist playlist =
        selected.isPresent() ? selected.get() : new Playlist("No Playlist Selected");
    if (playlistTable == null) {
      playlistTable = new PlaylistTable(controller, musicPlayer, playlist);
      playlistTable.addNewPlaylistsListener(() -> revalidate());
    } else {
      playlistTable.setPlaylist(playlist);
    }
    rightPanelScroll.setViewportView(playlistTable.getTable());
    mainPanel.revalidate();
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

  public void revalidate() {
    playlistsList.setPlaylists(controller.getUserPlaylists());
    updateDisplayedList(playlistsList.getSelectedPlaylist());
  }
}
