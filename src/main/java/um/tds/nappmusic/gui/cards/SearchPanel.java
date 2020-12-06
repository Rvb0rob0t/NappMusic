package um.tds.nappmusic.gui.cards;

import java.awt.BorderLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import um.tds.nappmusic.controller.Controller;
import um.tds.nappmusic.domain.Playlist;
import um.tds.nappmusic.gui.MusicPlayer;
import um.tds.nappmusic.gui.tables.PlaylistTable;

@SuppressWarnings("serial")
public class SearchPanel extends JPanel {
  private static final String TITLE_FIELD_NAME = "Título";
  private static final String AUTHOR_FIELD_NAME = "Intérprete";
  private static final int FIELD_WIDTH = 10;
  private JTextField titleField;
  private JTextField authorField;
  private JComboBox<String> styleComboBox;

  private JScrollPane scrollPane;

  /** . */
  public SearchPanel(MusicPlayer musicPlayer) {
    super(new BorderLayout());

    JPanel fieldsPanel = new JPanel();

    titleField = new JTextField(TITLE_FIELD_NAME, FIELD_WIDTH);
    titleField.addFocusListener(
        new FocusListener() {
          @Override
          public void focusLost(final FocusEvent e) {}

          @Override
          public void focusGained(final FocusEvent e) {
            titleField.selectAll();
          }
        });
    fieldsPanel.add(titleField);

    authorField = new JTextField(AUTHOR_FIELD_NAME, FIELD_WIDTH);
    authorField.addFocusListener(
        new FocusListener() {
          @Override
          public void focusLost(final FocusEvent e) {}

          @Override
          public void focusGained(final FocusEvent e) {
            authorField.selectAll();
          }
        });
    fieldsPanel.add(authorField);

    styleComboBox = new JComboBox<String>();
    styleComboBox.setModel(
        new DefaultComboBoxModel<String>(
            new String[] {"Nana", "Rock", "Indie", "Pop"})); // TODO Get from controller
    fieldsPanel.add(styleComboBox);

    JButton searchButton = new JButton("Buscar");
    searchButton.addActionListener(
        e -> {
          changeTable(
              musicPlayer,
              Controller.getSingleton()
                  .searchSongsBy(
                      titleField.getText(),
                      authorField.getText(),
                      (String) styleComboBox.getSelectedItem()));
        });
    fieldsPanel.add(searchButton);
    this.add(fieldsPanel, BorderLayout.NORTH);

    scrollPane = new JScrollPane();
    this.add(scrollPane, BorderLayout.CENTER);
  }

  private void changeTable(MusicPlayer musicPlayer, Playlist playlist) {
    PlaylistTable searchTable = new PlaylistTable(musicPlayer, playlist);
    scrollPane.setViewportView(searchTable.getTable());
    this.revalidate();
  }
}
