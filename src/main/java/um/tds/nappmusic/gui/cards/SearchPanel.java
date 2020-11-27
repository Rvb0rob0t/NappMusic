package um.tds.nappmusic.gui.cards;

import java.awt.BorderLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class SearchPanel extends JPanel {
  private JTextField txtBuscar;

  /**
   * .
   */
  public SearchPanel() {
    super(new BorderLayout());

    JPanel fieldsPanel = new JPanel();
    this.add(fieldsPanel, BorderLayout.NORTH);

    txtBuscar = new JTextField();
    txtBuscar.setText("Buscar...");
    fieldsPanel.add(txtBuscar);
    txtBuscar.setColumns(10);

    JComboBox genreComboBox = new JComboBox();
    genreComboBox.setModel(
        new DefaultComboBoxModel(new String[] {"Rock", "Indie", "Pop", "Dance / Electronic"}));
    fieldsPanel.add(genreComboBox);

    JTable searchTable = new JTable();
    searchTable
        .setModel(new DefaultTableModel(new Object[][] {{"Torero", "Emilio Domínguez Sánchez"}},
            new String[] {"Título", "Intérprete"}));
    searchTable.setEnabled(false);

    JScrollPane scrollPane = new JScrollPane(searchTable);
    this.add(scrollPane, BorderLayout.CENTER);
  }
}
