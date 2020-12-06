package um.tds.nappmusic.gui.cards;

import java.awt.BorderLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import um.tds.nappmusic.domain.Playlist;
import um.tds.nappmusic.domain.Song;
import um.tds.nappmusic.gui.MusicPlayer;
import um.tds.nappmusic.gui.tables.PlaylistTable;

@SuppressWarnings("serial")
public class SearchPanel extends JPanel {
  private static final String TITLE_FIELD_NAME = "Título";
  private static final String AUTHOR_FIELD_NAME = "Intérprete";
  private static final int FIELD_WIDTH = 10;
  private JTextField titleField;
  private JTextField authorField;

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

    JComboBox<String> genreComboBox = new JComboBox<String>();
    genreComboBox.setModel(
        new DefaultComboBoxModel<String>(
            new String[] {
              "Rock", "Indie", "Pop", "Dance / Electronic"
            })); // TODO Get from controller
    fieldsPanel.add(genreComboBox);

    JButton searchButton = new JButton("Buscar");
    searchButton.addActionListener(
        e -> {
          // TODO Get search playlist from controller
          Song prueba =
              new Song(
                  "Torero",
                  "Emilio Domínguez Sánchez",
                  null,
                  "src/main/resources/CALLA PEQUEÑO - Baby Song Sleep Music Sleeping.mp3",
                  0);
          ArrayList<Song> pruebaa = new ArrayList<Song>();
          pruebaa.add(prueba);
          Playlist playlist = new Playlist("webo", pruebaa);
          PlaylistTable searchTable = new PlaylistTable(musicPlayer, playlist);
          JScrollPane scrollPane = new JScrollPane(searchTable.getTable());
          this.add(scrollPane, BorderLayout.CENTER);
          this.revalidate();
        });
    fieldsPanel.add(searchButton);

    this.add(fieldsPanel, BorderLayout.NORTH);
  }
}
