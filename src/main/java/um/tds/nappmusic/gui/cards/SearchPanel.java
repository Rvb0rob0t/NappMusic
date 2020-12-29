package um.tds.nappmusic.gui.cards;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import um.tds.nappmusic.controller.Controller;
import um.tds.nappmusic.domain.Playlist;
import um.tds.nappmusic.domain.SongCatalog;
import um.tds.nappmusic.gui.MusicPlayer;
import um.tds.nappmusic.gui.tables.PlaylistTable;

public class SearchPanel {
  private static final String TITLE_FIELD_NAME = "Título";
  private static final String AUTHOR_FIELD_NAME = "Intérprete";
  private static final String STYLE_FIELD_NAME = "Género";
  private static final int FIELD_WIDTH = 10;

  private Controller controller;

  JPanel mainPanel;

  private JTextField titleField;
  private JTextField authorField;
  private JComboBox<String> styleComboBox;

  private PlaylistTable playlistTable;
  private JScrollPane scrollPane;

  /**
   * .
   *
   * @param controller
   */
  public SearchPanel(Controller controller, MusicPlayer musicPlayer) {
    this.controller = controller;

    mainPanel = new JPanel(new BorderLayout());

    JPanel fieldsPanel = new JPanel();

    titleField = new JTextField(FIELD_WIDTH);
    JPanel titleFieldPanel = wrapWithTitledBorderPanel(titleField, TITLE_FIELD_NAME);
    fieldsPanel.add(titleFieldPanel);

    authorField = new JTextField(FIELD_WIDTH);
    JPanel authorFieldPanel = wrapWithTitledBorderPanel(authorField, AUTHOR_FIELD_NAME);
    fieldsPanel.add(authorFieldPanel);

    styleComboBox = new JComboBox<String>();
    updateStyleList();
    JPanel styleComboBoxPanel = wrapWithTitledBorderPanel(styleComboBox, STYLE_FIELD_NAME);
    fieldsPanel.add(styleComboBoxPanel);

    JButton searchButton = new JButton("Buscar");
    searchButton.addActionListener(
        e -> {
          changeTable(
              musicPlayer,
              controller.searchSongsBy(
                  titleField.getText(),
                  authorField.getText(),
                  (String) styleComboBox.getSelectedItem()));
        });
    fieldsPanel.add(searchButton);
    mainPanel.add(fieldsPanel, BorderLayout.NORTH);

    scrollPane = new JScrollPane();
    mainPanel.add(scrollPane, BorderLayout.CENTER);
  }

  // aux method to set a good-looking titled (title centered) border
  private JPanel wrapWithTitledBorderPanel(Component comp, String title) {
    JPanel titledPanel = new JPanel(new FlowLayout());
    TitledBorder titledBorder = new TitledBorder(title);
    titledBorder.setTitleJustification(TitledBorder.CENTER);
    titledPanel.setBorder(titledBorder);
    titledPanel.add(comp);
    return titledPanel;
  }

  public JPanel getPanel() {
    return mainPanel;
  }

  private void changeTable(MusicPlayer musicPlayer, Playlist playlist) {
    if (playlistTable == null) {
      playlistTable = new PlaylistTable(controller, musicPlayer, playlist);
    } else {
      playlistTable.setPlaylist(playlist);
    }
    scrollPane.setViewportView(playlistTable.getTable());
    mainPanel.revalidate();
  }

  private void updateStyleList() {
    SongCatalog songCatalog = controller.getSongCatalog();
    styleComboBox.setModel(
        new DefaultComboBoxModel<String>(songCatalog.getAllStyles().toArray(new String[0])));
  }

  public void revalidate() {
    updateStyleList();
  }
}
