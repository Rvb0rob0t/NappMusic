package um.tds.nappmusic.gui.cards;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class RecentlyPlayedPane extends JScrollPane {

  /**
   * .
   */
  public RecentlyPlayedPane() {
    JTable table = new JTable();
    table.setModel(new DefaultTableModel(new Object[][] {{"Torero", "Emilio Domínguez Sánchez"}},
        new String[] {"Título", "Intérprete"}));
    table.setEnabled(false);
    this.setViewportView(table);
  }
}
