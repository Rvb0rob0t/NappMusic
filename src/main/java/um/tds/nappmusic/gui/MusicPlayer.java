package um.tds.nappmusic.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import um.tds.nappmusic.domain.Playlist;
import um.tds.nappmusic.domain.Song;

@SuppressWarnings("restriction")
public class MusicPlayer {
  private static final String RESOURCES = "src/main/resources";
  private static final String DEFAULT_THUMBNAIL = RESOURCES + "/default_thumbnail.png";
  private static final int THUMB_WIDTH = 100;
  private static final int THUMB_HEIGHT = 100;

  private static final ImageIcon PREV_ICON = new ImageIcon(RESOURCES + "/previous.png");
  private static final ImageIcon PLAY_ICON = new ImageIcon(RESOURCES + "/play.png");
  private static final ImageIcon PAUSE_ICON = new ImageIcon(RESOURCES + "/pause.png");
  private static final ImageIcon NEXT_ICON = new ImageIcon(RESOURCES + "/next.png");

  private MediaPlayer mediaPlayer;
  private Playlist playlistPlaying;
  private int songPlayingIndex;

  private JPanel mainPanel;
  private JLabel thumbLabel;
  private JLabel artistLabel;
  private JLabel songNameLabel;

  private JButton buttonPrevious;
  private JButton buttonPlay;
  private JButton buttonNext;

  // private JPanel volumePanel;

  public MusicPlayer() {
    mainPanel = new JPanel(new BorderLayout());

    JPanel songDataPanel = createSongDataPanel();
    mainPanel.add(songDataPanel, BorderLayout.WEST);

    // TODO playback time slider?
    JPanel buttonsPanel = createButtonsPanel();
    mainPanel.add(buttonsPanel, BorderLayout.CENTER);

    // TODO Volume?

    mainPanel.setVisible(false);

    try {
      com.sun.javafx.application.PlatformImpl.startup(() -> {});
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Exception: " + e.getMessage());
    }
  }

  private JPanel createSongDataPanel() {
    JPanel songDataPanel = new JPanel(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    EmptyBorder emptyBorder = new EmptyBorder(10, 10, 10, 10);

    thumbLabel = new JLabel();
    thumbLabel.setBorder(emptyBorder);
    c.gridx = 0;
    c.gridy = 0;
    c.gridheight = 2;
    c.weighty = 1;
    c.anchor = GridBagConstraints.CENTER;
    songDataPanel.add(thumbLabel, c);

    artistLabel = new JLabel();
    artistLabel.setBorder(emptyBorder);
    c.gridx = 1;
    c.gridy = 0;
    c.gridheight = 1;
    c.weighty = 0.5;
    c.anchor = GridBagConstraints.SOUTHWEST;
    songDataPanel.add(artistLabel, c);

    songNameLabel = new JLabel();
    songNameLabel.setBorder(emptyBorder);
    c.gridx = 1;
    c.gridy = 1;
    c.gridheight = 1;
    c.weighty = 0.5;
    c.anchor = GridBagConstraints.NORTHWEST;
    songDataPanel.add(songNameLabel, c);
    return songDataPanel;
  }

  private JPanel createButtonsPanel() {
    JPanel buttonsPanel = new JPanel();
    buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
    buttonsPanel.add(Box.createHorizontalGlue());
    buttonPrevious = new JButton(PREV_ICON);
    buttonPrevious.addActionListener(
        event -> {
          songPlayingIndex =
              songPlayingIndex == 0 ? (playlistPlaying.getSize() - 1) : (songPlayingIndex - 1);
          changeSong(playlistPlaying.getSong(songPlayingIndex));
          mediaPlayer.play();
        });
    buttonsPanel.add(buttonPrevious);
    buttonPlay = new JButton(PLAY_ICON);
    buttonPlay.addActionListener(
        event -> {
          switch (mediaPlayer.getStatus()) {
            case READY:
            case STOPPED:
            case PAUSED:
              buttonPlay.setIcon(PAUSE_ICON);
              mediaPlayer.play();
              break;
            case PLAYING:
              buttonPlay.setIcon(PLAY_ICON);
              mediaPlayer.pause();
              break;
            default:
              // TODO
              break;
          }
        });
    buttonsPanel.add(buttonPlay);
    buttonNext = new JButton(NEXT_ICON);
    buttonNext.addActionListener(
        event -> {
          songPlayingIndex = (songPlayingIndex + 1) % playlistPlaying.getSize();
          changeSong(playlistPlaying.getSong(songPlayingIndex));
          mediaPlayer.play();
        });
    buttonsPanel.add(buttonNext);
    buttonsPanel.add(Box.createHorizontalGlue());
    return buttonsPanel;
  }

  public Component getPanel() {
    return mainPanel;
  }

  private void changeSong(Song song) {
    if (mediaPlayer != null) {
      mediaPlayer.dispose();
    }

    artistLabel.setText(song.getAuthor());
    songNameLabel.setText(song.getTitle());
    BufferedImage img = null;
    try {
      // img = ImageIO.read(new File(song.getThumbnail()));
      img = ImageIO.read(new File(DEFAULT_THUMBNAIL));
      thumbLabel.setIcon(
          new ImageIcon(img.getScaledInstance(THUMB_WIDTH, THUMB_HEIGHT, Image.SCALE_DEFAULT)));
    } catch (IOException e) {

    }

    File f = new File(song.getFilePath());
    Media media = new Media(f.toURI().toString());
    mediaPlayer = new MediaPlayer(media);
  }

  /**
   * Change the currently playing song and sets the Panel if it's not already visible.
   *
   * @param playlist The playlist to be played
   * @param index Index of the song to be played
   */
  public void play(Playlist playlist, int index) {
    if (!mainPanel.isVisible()) {
      mainPanel.setVisible(true);
    }
    playlistPlaying = playlist;
    changeSong(playlistPlaying.getSong(index));
    buttonPlay.setIcon(PAUSE_ICON);
    mediaPlayer.play();
  }
}
