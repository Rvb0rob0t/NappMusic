package um.tds.nappmusic.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class PlayerPanel extends JPanel {
  private static final String RESOURCES = "src/main/resources";
  private final int thumbWidth = 100;
  private final int thumbHeight = 100;

  private JPanel songDataPanel;
  private JLabel imgLabel;
  private JLabel artistLabel;
  private JLabel songNameLabel;

  private JPanel centralPanel;

  //  private JPanel volumePanel;

  /** . */
  public PlayerPanel() {
    songDataPanel = new JPanel(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    EmptyBorder emptyBorder = new EmptyBorder(10, 10, 10, 10);

    imgLabel = new JLabel();
    imgLabel.setBorder(emptyBorder);
    c.gridx = 0;
    c.gridy = 0;
    c.gridheight = 2;
    c.weighty = 1;
    c.anchor = GridBagConstraints.CENTER;
    songDataPanel.add(imgLabel, c);

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

    this.add(songDataPanel, BorderLayout.WEST);

    centralPanel = new JPanel(new BorderLayout());

    //    JSlider playerSlider = new JSlider();
    //    centralPanel.add(playerSlider, BorderLayout.SOUTH);

    JPanel playerButtonsPanel = new JPanel();
    playerButtonsPanel.setLayout(new BoxLayout(playerButtonsPanel, BoxLayout.X_AXIS));
    playerButtonsPanel.add(Box.createHorizontalGlue());
    JButton btnPrevious = new JButton(new ImageIcon(RESOURCES + "/previous.png"));
    playerButtonsPanel.add(btnPrevious);
    JButton btnPlay = new JButton(new ImageIcon(RESOURCES + "/play.png"));
    playerButtonsPanel.add(btnPlay);
    JButton btnNext = new JButton(new ImageIcon(RESOURCES + "/next.png"));
    playerButtonsPanel.add(btnNext);
    playerButtonsPanel.add(Box.createHorizontalGlue());
    centralPanel.add(playerButtonsPanel, BorderLayout.CENTER);

    this.add(centralPanel, BorderLayout.CENTER);

    //    volumePanel = new JPanel();
    //    this.add(volumePanel, BorderLayout.EAST);
    //
    //    JSlider volumeSlider = new JSlider();
    //    volumePanel.add(volumeSlider);
  }

  /**
   * Change the data of the of the currently playing song.
   *
   * @param thumbnail The relative path of the thumbnail
   * @param artist The artist name
   * @param songName The name of the song
   */
  public void changeSongData(String thumbnail, String artist, String songName) {
    artistLabel.setText(artist);
    songNameLabel.setText(songName);
    BufferedImage img = null;
    try {
      img = ImageIO.read(new File(thumbnail));
      imgLabel.setIcon(
          new ImageIcon(img.getScaledInstance(thumbWidth, thumbHeight, Image.SCALE_DEFAULT)));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
