package um.tds.nappmusic.gui.lists;

import java.awt.event.MouseListener;
import java.util.List;
import java.util.Optional;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;
import javax.swing.ListSelectionModel;
import um.tds.nappmusic.domain.Playlist;

public class PlaylistJList {
  private JList<String> list;
  private PlaylistListModel listModel;
  private JPopupMenu rightClickMenu;
  private JMenu addToPlaylistMenu;

  public PlaylistJList(List<Playlist> playlists, JPopupMenu rightClickMenu) {
    this.listModel = new PlaylistListModel(playlists);
    this.list = new JList<String>(listModel);
    this.list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    this.rightClickMenu = rightClickMenu;
    // this.addToPlaylistMenu = new JMenu("Añadir a playlist");
    // this.rightClickMenu.add(addToPlaylistMenu);
  }

  public PlaylistJList(List<Playlist> playlists) {
    this(playlists, new JPopupMenu());
  }

  public Optional<Playlist> getSelectedPlaylist() {
    List<Playlist> playlists = listModel.getPlaylistList();
    int selIdx = list.getSelectedIndex();
    if (selIdx != -1) {
      return Optional.of(playlists.get(selIdx));
    }
    return Optional.empty();
  }

  public void addMouseListener(MouseListener listener) {
    list.addMouseListener(listener);
  }

  public JList<String> getList() {
    return list;
  }

  public void setPlaylists(List<Playlist> playlists) {
    listModel.setPlaylistList(playlists);
    list.updateUI();
  }
}
