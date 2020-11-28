package um.tds.nappmusic.gui.tables;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class SongsTableModel extends AbstractTableModel {

  private String[] columnNames;
  private String[][] data;

  public SongsTableModel(String[][] data) {
    this.columnNames = new String[] {"Título", "Intérprete"};
    this.data = data;
  }

  /**
   * Tell how many rows are shown.
   */
  public int getRowCount() {
    return data.length;
  }

  /**
   * Tell how many columns are shown.
   */
  public int getColumnCount() {
    return columnNames.length;
  }

  /**
   * Tell how to show the columns name.
   * @param column The column to be shown
   */
  public String getColumnName(int column) {
    return columnNames[column];
  }

  /**
   * Tell how to show an element of the table.
   * @param row The row in which the element is located
   * @param column The column in which the element is located
   */
  public Object getValueAt(int row, int column) {
    return data[row][column];
  }
}
