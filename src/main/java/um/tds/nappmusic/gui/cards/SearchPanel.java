package um.tds.nappmusic.gui.cards;

import java.awt.BorderLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import um.tds.nappmusic.gui.tables.SongsTableModel;

@SuppressWarnings("serial")
public class SearchPanel extends JPanel {
  private static final int FIELD_WIDTH = 10;
  private JTextField searchText;

  /** . */
  public SearchPanel() {
    super(new BorderLayout());

    JPanel fieldsPanel = new JPanel();

    searchText = new JTextField("Buscar...", FIELD_WIDTH);
    fieldsPanel.add(searchText);

    JComboBox<String> genreComboBox = new JComboBox<String>();
    genreComboBox.setModel(
        new DefaultComboBoxModel<String>(
            new String[] {"Rock", "Indie", "Pop", "Dance / Electronic"}));
    fieldsPanel.add(genreComboBox);

    this.add(fieldsPanel, BorderLayout.NORTH);

    JTable searchTable = new JTable();
    searchTable.setModel(
        new SongsTableModel(new String[][] {{"Torero", "Emilio Domínguez Sánchez"}}));
    searchTable.setEnabled(false);
    JScrollPane scrollPane = new JScrollPane(searchTable);
    this.add(scrollPane, BorderLayout.CENTER);
  }
}
