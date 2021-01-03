package um.tds.nappmusic.gui.tables;

import javax.swing.table.AbstractTableModel;
import um.tds.nappmusic.domain.Playlist;

public class PlaylistTableModel extends AbstractTableModel {

  private static final long serialVersionUID = -3070085429813305603L;

  private static final int COLUMNS = 2;
  private static final int TITLE_COLUMN = 0;
  private static final int AUTHOR_COLUMN = 1;
  private static final String TITLE_COLUMN_NAME = "Title";
  private static final String AUTHOR_COLUMN_NAME = "Artist";

  private Playlist playlist;

  public PlaylistTableModel(Playlist playlist) {
    this.playlist = playlist;
  }

  public Playlist getPlaylist() {
    return playlist;
  }

  public void setPlaylist(Playlist playlist) {
    this.playlist = playlist;
  }

  @Override
  public boolean isCellEditable(int row, int column) {
    return false;
  }

  @Override
  public int getRowCount() {
    return playlist.getSize();
  }

  @Override
  public int getColumnCount() {
    return COLUMNS;
  }

  @Override
  public String getColumnName(int column) {
    switch (column) {
      case TITLE_COLUMN:
        return TITLE_COLUMN_NAME;
      case AUTHOR_COLUMN:
        return AUTHOR_COLUMN_NAME;
      default:
        throw new IndexOutOfBoundsException("column not in range for " + this.getClass().getName());
    }
  }

  @Override
  public Object getValueAt(int row, int column) {
    switch (column) {
      case TITLE_COLUMN:
        return playlist.getSong(row).getTitle();
      case AUTHOR_COLUMN:
        return playlist.getSong(row).getAuthor();
      default:
        throw new IndexOutOfBoundsException("column not in range for " + this.getClass().getName());
    }
  }
}
