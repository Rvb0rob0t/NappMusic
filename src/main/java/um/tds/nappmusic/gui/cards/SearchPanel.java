package um.tds.nappmusic.gui.cards;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.util.Arrays;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import um.tds.nappmusic.controller.Controller;
import um.tds.nappmusic.domain.Playlist;
import um.tds.nappmusic.gui.MusicPlayer;
import um.tds.nappmusic.gui.tables.PlaylistTable;

public class SearchPanel {
  private static final String TITLE_FIELD_NAME = "Título";
  private static final String AUTHOR_FIELD_NAME = "Intérprete";
  private static final String STYLE_FIELD_NAME = "Género";
  private static final int FIELD_WIDTH = 10;

  JPanel mainPanel;

  private JTextField titleField;
  private JTextField authorField;
  private JComboBox<String> styleComboBox;

  private PlaylistTable playlistTable;
  private JScrollPane scrollPane;

  public SearchPanel(MusicPlayer musicPlayer) {
    mainPanel = new JPanel(new BorderLayout());

    JPanel fieldsPanel = new JPanel(new FlowLayout());

    titleField = new JTextField(FIELD_WIDTH);
    JPanel titleFieldPanel = wrapWithTitledBorderPanel(titleField, TITLE_FIELD_NAME);
    fieldsPanel.add(titleFieldPanel);

    authorField = new JTextField(FIELD_WIDTH);
    JPanel authorFieldPanel = wrapWithTitledBorderPanel(authorField, AUTHOR_FIELD_NAME);
    fieldsPanel.add(authorFieldPanel);

    styleComboBox = new JComboBox<String>();
    styleComboBox.setPrototypeDisplayValue(columnsToPrototypeDisplayValue(FIELD_WIDTH));
    updateStyleList();
    JPanel styleComboBoxPanel = wrapWithTitledBorderPanel(styleComboBox, STYLE_FIELD_NAME);
    fieldsPanel.add(styleComboBoxPanel);

    JButton searchButton = new JButton("Buscar");
    searchButton.addActionListener(e -> changeTable(musicPlayer));
    fieldsPanel.add(searchButton);
    mainPanel.add(fieldsPanel, BorderLayout.NORTH);

    scrollPane = new JScrollPane();
    mainPanel.add(scrollPane, BorderLayout.CENTER);
  }

  private String columnsToPrototypeDisplayValue(int columns) {
    char[] string = new char[columns];
    Arrays.fill(string, 'w');
    return new String(string);
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

  private void changeTable(MusicPlayer musicPlayer) {
    String style = (String) styleComboBox.getSelectedItem();
    Playlist playlist;
    if (style == "") {
      playlist =
          Controller.getSingleton()
              .searchSongsByTitleAndAuthor(titleField.getText(), authorField.getText());
    } else {
      playlist =
          Controller.getSingleton()
              .searchSongsBy(titleField.getText(), authorField.getText(), style);
    }
    if (playlistTable == null) {
      playlistTable = new PlaylistTable(Controller.getSingleton(), musicPlayer, playlist);
    } else {
      playlistTable.setPlaylist(playlist);
    }
    scrollPane.setViewportView(playlistTable.getTable());
    mainPanel.revalidate();
  }

  private void updateStyleList() {
    List<String> styles = Controller.getSingleton().getAllStyles();
    String[] options = new String[styles.size() + 1];
    options[0] = "";
    for (int i = 0; i < styles.size(); i++) {
      options[i + 1] = styles.get(i);
    }
    styleComboBox.setModel(new DefaultComboBoxModel<>(options));
  }

  public void revalidate() {
    updateStyleList();
  }
}
