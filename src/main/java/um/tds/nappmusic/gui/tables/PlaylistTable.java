package um.tds.nappmusic.gui.tables;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import um.tds.nappmusic.controller.Controller;
import um.tds.nappmusic.domain.Playlist;
import um.tds.nappmusic.domain.Song;
import um.tds.nappmusic.gui.MusicPlayer;

public class PlaylistTable extends MouseAdapter {
  private JTable table;
  private MusicPlayer musicPlayer;
  private JPopupMenu rightClickMenu;
  private JMenu addToPlaylistMenu;

  public PlaylistTable(MusicPlayer musicPlayer, Playlist playlist, JPopupMenu rightClickMenu) {
    this.table = new JTable(new PlaylistTableModel(playlist));
    this.table.addMouseListener(this);
    this.musicPlayer = musicPlayer;

    this.rightClickMenu = rightClickMenu;
    this.addToPlaylistMenu = new JMenu("Añadir a playlist");
    this.rightClickMenu.add(addToPlaylistMenu);
  }

  public PlaylistTable(MusicPlayer musicPlayer, Playlist playlist) {
    this(musicPlayer, playlist, new JPopupMenu());
  }

  public JTable getTable() {
    return table;
  }

  public void setPlaylist(Playlist playlist) {
    // TODO Check the table model type
    ((PlaylistTableModel) table.getModel()).setPlaylist(playlist);
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    Playlist playlist =
        ((PlaylistTableModel) table.getModel()).getPlaylist(); // TODO Check the table model type
    int row = table.rowAtPoint(e.getPoint());
    Song song = playlist.getSong(row);

    if (SwingUtilities.isLeftMouseButton(e)) {
      musicPlayer.play(playlist, row);
    } else if (SwingUtilities.isRightMouseButton(e)) {
      addAllPlaylistsAsItems(song);
      rightClickMenu.show(e.getComponent(), e.getX(), e.getY());
    }
  }

  private void addAllPlaylistsAsItems(Song song) {
    addToPlaylistMenu.removeAll();
    Controller.getSingleton()
        .getUserPlaylists()
        .forEach(
            playlist -> {
              JMenuItem playlistItem = new JMenuItem(playlist.getName());
              playlistItem.addActionListener(
                  e -> {
                    // TODO Controller.getSingleton().addToPlaylist(playlist, song);
                  });
              addToPlaylistMenu.add(playlistItem);
            });
    JMenuItem newPlaylistItem = new JMenuItem("Nueva playlist");
    newPlaylistItem.addActionListener(
        e -> {
          // TODO Controller.getSingleton().createPlaylist();
        });
  }
}
