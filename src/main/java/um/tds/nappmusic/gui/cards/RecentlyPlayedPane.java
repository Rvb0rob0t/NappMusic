package um.tds.nappmusic.gui.cards;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import um.tds.nappmusic.gui.tables.SongsTableModel;

@SuppressWarnings("serial")
public class RecentlyPlayedPane extends JScrollPane {

  /** . */
  public RecentlyPlayedPane() {
    JTable table = new JTable();
    table.setModel(new SongsTableModel(new String[][] {{"Torero", "Emilio Domínguez Sánchez"}}));
    this.setViewportView(table);
  }
}
