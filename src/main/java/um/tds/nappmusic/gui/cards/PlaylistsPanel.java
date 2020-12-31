package um.tds.nappmusic.gui.cards;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Optional;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import um.tds.nappmusic.controller.Controller;
import um.tds.nappmusic.domain.Playlist;
import um.tds.nappmusic.domain.Song;
import um.tds.nappmusic.gui.MusicPlayer;
import um.tds.nappmusic.gui.lists.PlaylistJList;
import um.tds.nappmusic.gui.notifier.GuiNotifier;
import um.tds.nappmusic.gui.notifier.PlaylistListListener;
import um.tds.nappmusic.gui.notifier.PlaylistListener;
import um.tds.nappmusic.gui.tables.PlaylistTable;

public class PlaylistsPanel extends MouseAdapter implements PlaylistListener, PlaylistListListener {
  private MusicPlayer musicPlayer;

  private JPanel mainPanel;
  private JPanel leftPanel;
  private JPanel rightPanel;
  private JScrollPane leftPanelScroll;
  private JScrollPane rightPanelScroll;

  private PlaylistJList playlistsList;
  private PlaylistTable playlistTable;

  private JLabel leftPaneLbl;

  public PlaylistsPanel(MusicPlayer musicPlayer) {
    this.musicPlayer = musicPlayer;
    mainPanel = new JPanel(new BorderLayout(10, 10));
    leftPanel = new JPanel(new BorderLayout(10, 10));
    rightPanel = new JPanel(new BorderLayout(10, 10));
    leftPanelScroll = new JScrollPane();
    rightPanelScroll = new JScrollPane();
    leftPaneLbl = new JLabel("Your Playlists");

    GuiNotifier.INSTANCE.addPlaylistListListener(this);
    List<Playlist> userPlaylists = Controller.getSingleton().getUserPlaylists();
    playlistsList = new PlaylistJList(userPlaylists);
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
    if (!selected.isPresent()) {
      return;
    }

    Playlist playlist = selected.get();
    if (playlistTable == null) {
      JPopupMenu rightClickMenu = new JPopupMenu();
      JMenuItem removeItem = new JMenuItem("Remove from this playlist");
      rightClickMenu.add(removeItem);
      playlistTable = new PlaylistTable(musicPlayer, playlist, rightClickMenu);
      removeItem.addActionListener(
          event -> {
            Optional<Song> song = playlistTable.getSelectedSong();
            if (song.isPresent()) {
              Controller.getSingleton().removeFromPlaylist(playlist, song.get());
            }
          });
    } else {
      GuiNotifier.INSTANCE.removePlaylistListener(playlistTable.getDisplayedPlaylist(), this);
      playlistTable.setPlaylist(playlist);
    }
    GuiNotifier.INSTANCE.addPlaylistListener(playlist, this);
    rightPanelScroll.setViewportView(playlistTable.getTable());
    mainPanel.revalidate();
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    if (SwingUtilities.isLeftMouseButton(e)) {
      updateDisplayedList(playlistsList.getSelectedPlaylist());
    }
  }

  public JPanel getPanel() {
    return mainPanel;
  }

  public void revalidate() {
    playlistsList.setPlaylists(Controller.getSingleton().getUserPlaylists());
    updateDisplayedList(playlistsList.getSelectedPlaylist());
  }

  @Override
  public void playlistModified() {
    revalidate();
  }

  @Override
  public void playlistListModified() {
    revalidate();
  }
}
