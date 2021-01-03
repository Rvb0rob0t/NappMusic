package um.tds.nappmusic.gui;

import com.itextpdf.text.DocumentException;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import um.tds.nappmusic.app.App;
import um.tds.nappmusic.app.AppFonts;
import um.tds.nappmusic.app.AppLogo;
import um.tds.nappmusic.controller.Controller;
import um.tds.nappmusic.gui.cards.HomePanel;
import um.tds.nappmusic.gui.cards.MostPlayedPanel;
import um.tds.nappmusic.gui.cards.PlaylistsPanel;
import um.tds.nappmusic.gui.cards.RecentlyPlayedPanel;
import um.tds.nappmusic.gui.cards.SearchPanel;
import um.tds.uibutton.UiButton;
import um.tds.uibutton.UiButtonEvent;
import um.tds.uibutton.UiButtonListener;

public class MainWindow {
  private static final String HOME_CARD_NAME = "Home";
  private static final String SEARCH_CARD_NAME = "Search";
  private static final String PLAYLISTS_CARD_NAME = "Your playlists";
  private static final String RECENTLY_CARD_NAME = "Recently played";
  private static final String MOST_PLAYED_CARD_NAME = "Most played";
  private static final int CARD_SELECTOR_BUTTON_H_ALIGNMENT = SwingConstants.LEFT;
  private static final Color BTN_SONGLOADER_COLOR = Color.RED;

  private Controller controller;
  private MusicPlayer musicPlayer;
  private JFrame mainFrame;

  private JMenuItem upgradeMenuItem;
  private JMenuItem generatePdfMenuItem;
  private JMenuItem logOutMenuItem;
  private UiButton btnXmlLoader;

  private JPanel cardsPanel;

  private JPanel cardSelectorPanel;
  private JButton btnHome;
  private JButton btnSearch;
  private JButton btnPlaylists;
  private JButton btnRecently;
  private JButton btnMostPlayed;

  private HomePanel homePanel;
  private SearchPanel searchPanel;
  private PlaylistsPanel playlistsPanel;
  private RecentlyPlayedPanel recentlyPlayedPanel;
  private MostPlayedPanel mostPlayedPanel;

  /** Create the application. */
  public MainWindow() {
    controller = Controller.getSingleton();

    mainFrame = new JFrame(App.NAME);
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainFrame.getContentPane().setLayout(new BorderLayout(10, 0));

    musicPlayer = new MusicPlayer();
    mainFrame.getContentPane().add(musicPlayer.getPanel(), BorderLayout.SOUTH);

    mainFrame.getContentPane().add(createLeftSidePanel(), BorderLayout.WEST);

    mainFrame.getContentPane().add(createCardsPanel(), BorderLayout.CENTER);

    mainFrame.getContentPane().add(createTopPanel(), BorderLayout.NORTH);

    assignButtonActions();

    mainFrame.pack();
    mainFrame.setMinimumSize(mainFrame.getSize());
  }

  public void setVisible(boolean value) {
    mainFrame.setVisible(value);
  }

  /** Create the menu selector panel. */
  private JPanel createLeftSidePanel() {
    JPanel leftSidePanel = new JPanel(new BorderLayout());

    cardSelectorPanel = new JPanel(new GridLayout(0, 1, 0, 0));
    cardSelectorPanel.setPreferredSize(new Dimension(200, 300));

    btnHome = addCardSelectorButton(HOME_CARD_NAME);
    btnSearch = addCardSelectorButton(SEARCH_CARD_NAME);
    btnPlaylists = addCardSelectorButton(PLAYLISTS_CARD_NAME);
    btnRecently = addCardSelectorButton(RECENTLY_CARD_NAME);
    btnMostPlayed = addCardSelectorButton(MOST_PLAYED_CARD_NAME);

    leftSidePanel.add(cardSelectorPanel, BorderLayout.NORTH);
    return leftSidePanel;
  }

  private JButton addCardSelectorButton(String name) {
    JButton button = new JButton(name);
    button.setFont(AppFonts.CARD_SELECTOR_FONT);
    button.setHorizontalAlignment(CARD_SELECTOR_BUTTON_H_ALIGNMENT);
    cardSelectorPanel.add(button);
    return button;
  }

  /** Create the changing panel. */
  private JPanel createCardsPanel() {
    cardsPanel = new JPanel(new CardLayout());

    homePanel = new HomePanel();
    cardsPanel.add(homePanel.getPanel(), HOME_CARD_NAME);

    searchPanel = new SearchPanel(musicPlayer);
    cardsPanel.add(searchPanel.getPanel(), SEARCH_CARD_NAME);

    playlistsPanel = new PlaylistsPanel(musicPlayer);
    cardsPanel.add(playlistsPanel.getPanel(), PLAYLISTS_CARD_NAME);

    recentlyPlayedPanel = new RecentlyPlayedPanel(musicPlayer);
    cardsPanel.add(recentlyPlayedPanel.getPanel(), RECENTLY_CARD_NAME);

    mostPlayedPanel = new MostPlayedPanel(musicPlayer);
    cardsPanel.add(mostPlayedPanel.getPanel(), MOST_PLAYED_CARD_NAME);

    return cardsPanel;
  }

  /** Create the panel with the user settings. */
  private JPanel createTopPanel() {
    JPanel topPanel = new JPanel(new BorderLayout());

    JLabel logoLabel = new JLabel(App.NAME, AppLogo.get(), JLabel.CENTER);
    logoLabel.setBorder(new EmptyBorder(10, 30, 10, 0));
    logoLabel.setFont(AppFonts.APP_NAME_FONT);
    topPanel.add(logoLabel, BorderLayout.WEST);

    JMenuBar menuBar = new JMenuBar();
    JMenu menu = new JMenu(Controller.getSingleton().getCurrentUser().getUsername());
    upgradeMenuItem = new JMenuItem("Upgrade");
    upgradeMenuItem.addActionListener(
        event -> {
          int result =
              JOptionPane.showConfirmDialog(
                  mainFrame,
                  "Would you like upgrade to premium for only "
                      + controller.getCurrentUser().calculatePremiumPrice()
                      + " gold coins?");
          switch (result) {
            case JOptionPane.YES_OPTION:
              controller.makeUserPremium(controller.getCurrentUser());
              menu.removeAll();
              menu.add(generatePdfMenuItem);
              menu.add(logOutMenuItem);
              break;
            default:
              break;
          }
        });
    generatePdfMenuItem = new JMenuItem("Save playlists in pdf");
    generatePdfMenuItem.addActionListener(
        event -> {
          JFileChooser chooser = new JFileChooser();
          if (chooser.showOpenDialog(mainFrame) == JFileChooser.APPROVE_OPTION) {
            try {
              controller.generatePlaylistsPdf(chooser.getSelectedFile().getAbsolutePath());
            } catch (FileNotFoundException | DocumentException e) {
              JOptionPane.showMessageDialog(
                  mainFrame,
                  "You probably should choose another path",
                  "PDF not generated",
                  JOptionPane.ERROR_MESSAGE);
            }
          }
        });
    if (controller.getCurrentUser().isPremium()) {
      menu.add(generatePdfMenuItem);
    } else {
      menu.add(upgradeMenuItem);
    }
    logOutMenuItem = new JMenuItem("Log out");
    logOutMenuItem.addActionListener(
        event -> {
          LoginWindow window = new LoginWindow();
          window.showWindow();
          dispose();
        });
    menu.add(logOutMenuItem);
    menuBar.add(menu);
    btnXmlLoader = new UiButton();
    btnXmlLoader.setColor(BTN_SONGLOADER_COLOR);
    btnXmlLoader.setSize(20, 20);

    JPanel rightSidePanel = new JPanel();
    rightSidePanel.setLayout(new BoxLayout(rightSidePanel, BoxLayout.Y_AXIS));
    JPanel btnWrapper = new JPanel(new FlowLayout());
    btnWrapper.add(btnXmlLoader);
    rightSidePanel.add(btnWrapper);
    JPanel menuWrapper = new JPanel(new BorderLayout());
    menuWrapper.add(menuBar, BorderLayout.NORTH);
    rightSidePanel.add(menuWrapper);
    topPanel.add(rightSidePanel, BorderLayout.EAST);
    return topPanel;
  }

  public void assignButtonActions() {
    btnHome.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            ((CardLayout) cardsPanel.getLayout()).show(cardsPanel, HOME_CARD_NAME);
          }
        });

    btnSearch.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            ((CardLayout) cardsPanel.getLayout()).show(cardsPanel, SEARCH_CARD_NAME);
            searchPanel.revalidate();
          }
        });

    btnPlaylists.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            ((CardLayout) cardsPanel.getLayout()).show(cardsPanel, PLAYLISTS_CARD_NAME);
            playlistsPanel.revalidate();
          }
        });

    btnRecently.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            ((CardLayout) cardsPanel.getLayout()).show(cardsPanel, RECENTLY_CARD_NAME);
            recentlyPlayedPanel.revalidate();
          }
        });

    btnMostPlayed.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            if (Controller.getSingleton().getCurrentUser().isPremium()) {
              ((CardLayout) cardsPanel.getLayout()).show(cardsPanel, MOST_PLAYED_CARD_NAME);
              mostPlayedPanel.revalidate();
            } else {
              JOptionPane.showMessageDialog(
                  mainFrame,
                  "This option is only available when you're premium",
                  "You shall not pass",
                  JOptionPane.INFORMATION_MESSAGE);
            }
          }
        });

    btnXmlLoader.addUiButtonListener(
        new UiButtonListener() {
          public void notifyButtonEvent(UiButtonEvent e) {
            if (!e.isOldTurnedOn() && e.isTurnedOn()) {
              JFileChooser chooser = new JFileChooser();
              FileNameExtensionFilter filter = new FileNameExtensionFilter("Xml Song Files", "xml");
              chooser.setFileFilter(filter);
              int returnVal = chooser.showOpenDialog(mainFrame);
              if (returnVal == JFileChooser.APPROVE_OPTION) {
                controller.loadXml(chooser.getSelectedFile().getAbsolutePath());
                searchPanel.revalidate();
              } else {
                btnXmlLoader.setTurnedOn(false);
              }
            }
          }
        });
  }

  public void dispose() {
    musicPlayer.dispose();
    mainFrame.dispose();
  }
}
