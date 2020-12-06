package um.tds.nappmusic.gui.tables;

import javax.swing.table.AbstractTableModel;
import um.tds.nappmusic.domain.Playlist;

@SuppressWarnings("serial")
public class PlaylistTableModel extends AbstractTableModel {

  private static final int COLUMNS = 2;
  private static final int TITLE_COLUMN = 0;
  private static final int AUTHOR_COLUMN = 1;
  private static final String TITLE_COLUMN_NAME = "Título";
  private static final String AUTHOR_COLUMN_NAME = "Intérprete";

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
        // TODO Shouldn't reach here
        return "";
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
        // TODO Shouldn't reach here
        return "";
    }
  }
}