package um.tds.nappmusic.gui.tables;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Optional;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import um.tds.nappmusic.controller.Controller;
import um.tds.nappmusic.domain.Playlist;
import um.tds.nappmusic.domain.Song;
import um.tds.nappmusic.gui.MusicPlayer;
import um.tds.nappmusic.gui.notifier.GuiNotifier;

public class PlaylistTable extends MouseAdapter {
  private JTable table;
  private MusicPlayer musicPlayer;

  private JPopupMenu rightClickMenu;
  private JMenu addToPlaylistMenu;
  private JMenuItem newPlaylistItem;

  public PlaylistTable(MusicPlayer musicPlayer, Playlist playlist, JPopupMenu rightClickMenu) {
    this.table = new JTable(new PlaylistTableModel(playlist));
    this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    this.table.addMouseListener(this);

    this.musicPlayer = musicPlayer;

    this.rightClickMenu = rightClickMenu;
    this.addToPlaylistMenu = new JMenu("Add to playlist");
    this.newPlaylistItem = new JMenuItem("New playlist");
    this.newPlaylistItem.addActionListener(
        e -> {
          String newPlaylistName = JOptionPane.showInputDialog("Name of the new playlist");
          if (newPlaylistName != null && newPlaylistName.length() > 0) {
            int row = table.getSelectedRow();
            if (row != -1) {
              Playlist created = Controller.getSingleton().createPlaylist(newPlaylistName);
              Song song = getDisplayedPlaylist().getSong(row);
              Controller.getSingleton().addToPlaylist(created, song);
              GuiNotifier.INSTANCE.notifyPlaylistListListeners();
            }
          }
        });
    this.addToPlaylistMenu.add(newPlaylistItem);
    this.rightClickMenu.add(addToPlaylistMenu);
  }

  public PlaylistTable(MusicPlayer musicPlayer, Playlist playlist) {
    this(musicPlayer, playlist, new JPopupMenu());
  }

  public Optional<Song> getSelectedSong() {
    Playlist playlist = ((PlaylistTableModel) table.getModel()).getPlaylist();
    int selIdx = table.getSelectedRow();
    if (selIdx != -1) {
      return Optional.of(playlist.getSong(selIdx));
    }
    return Optional.empty();
  }

  public JTable getTable() {
    return table;
  }

  public void setPlaylist(Playlist playlist) {
    ((PlaylistTableModel) table.getModel()).setPlaylist(playlist);
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    Playlist playlist = ((PlaylistTableModel) table.getModel()).getPlaylist();
    int row = table.rowAtPoint(e.getPoint());
    if (row != -1) {
      Song song = playlist.getSong(row);
      if (SwingUtilities.isLeftMouseButton(e)) {
        musicPlayer.play(playlist, row);
      } else if (SwingUtilities.isRightMouseButton(e)) {
        // Set the song as selected to operate on it with new lists
        table.setRowSelectionInterval(row, row);
        addAllPlaylistsAsMenuItems(song);
        rightClickMenu.show(e.getComponent(), e.getX(), e.getY());
      }
    }
  }

  private void addAllPlaylistsAsMenuItems(Song song) {
    addToPlaylistMenu.removeAll();
    Controller.getSingleton()
        .getUserPlaylists()
        .forEach(
            playlist -> {
              JMenuItem playlistItem = new JMenuItem(playlist.getName());
              playlistItem.addActionListener(
                  e -> {
                    Controller.getSingleton().addToPlaylist(playlist, song);
                    GuiNotifier.INSTANCE.notifyPlaylistListeners(playlist);
                  });
              addToPlaylistMenu.add(playlistItem);
            });
    addToPlaylistMenu.add(newPlaylistItem);
  }

  public Playlist getDisplayedPlaylist() {
    return ((PlaylistTableModel) table.getModel()).getPlaylist();
  }
}
