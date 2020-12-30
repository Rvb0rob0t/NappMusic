package um.tds.nappmusic.gui.lists;

import java.util.List;
import javax.swing.AbstractListModel;
import um.tds.nappmusic.domain.Playlist;

public class PlaylistListModel extends AbstractListModel<String> {

  private static final long serialVersionUID = -6915387928992573480L;

  private List<Playlist> playlists;

  public PlaylistListModel(List<Playlist> contents) {
    this.playlists = contents;
  }

  @Override
  public int getSize() {
    return playlists.size();
  }

  @Override
  public String getElementAt(int index) {
    return playlists.get(index).getName();
  }

  public List<Playlist> getPlaylistList() {
    return playlists;
  }

  public void setPlaylistList(List<Playlist> playlists) {
    this.playlists = playlists;
  }
}
