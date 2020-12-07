package um.tds.nappmusic.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import um.tds.nappmusic.app.App;
import um.tds.nappmusic.app.AppLogo;
import um.tds.nappmusic.controller.Controller;
import um.tds.nappmusic.gui.cards.HomePanel;
import um.tds.nappmusic.gui.cards.PlaylistsPanel;
import um.tds.nappmusic.gui.cards.RecentlyPlayedPane;
import um.tds.nappmusic.gui.cards.SearchPanel;
import um.tds.uibutton.UiButton;
import um.tds.uibutton.UiButtonEvent;
import um.tds.uibutton.UiButtonListener;

@SuppressWarnings("serial")
public class MainWindow {
  private static final String HOME_CARD_NAME = "Home";
  private static final String SEARCH_CARD_NAME = "Search";
  private static final String PLAYLISTS_CARD_NAME = "Playlists";
  private static final String RECENTLY_CARD_NAME = "Recently";
  private static final Color BTN_SONGLOADER_COLOR = Color.RED;

  private MusicPlayer musicPlayer;

  private JFrame mainFrame;
  private JPanel topPanel;
  private JLabel logoLabel;
  private JMenuItem upgradeMenuItem;
  private JMenuItem logOutMenuItem;
  private JPanel cardsPanel;
  private JPanel leftSidePanel;

  private JButton btnHome;
  private JButton btnSearch;
  private JButton btnPlaylists;
  private JButton btnRecently;
  private UiButton btnXmlLoader;

  /** Create the application. */
  public MainWindow() {
    mainFrame = new JFrame(App.NAME);
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainFrame.getContentPane().setLayout(new BorderLayout(10, 0));

    musicPlayer = new MusicPlayer();
    mainFrame.getContentPane().add(musicPlayer.getPanel(), BorderLayout.SOUTH);

    createLeftSidePanel();
    mainFrame.getContentPane().add(leftSidePanel, BorderLayout.WEST);

    createCardsPanel();
    mainFrame.getContentPane().add(cardsPanel, BorderLayout.CENTER);

    createTopPanel("Username");
    mainFrame.getContentPane().add(topPanel, BorderLayout.NORTH);

    assignButtonActions();

    mainFrame.pack();
    mainFrame.setMinimumSize(mainFrame.getSize());
  }

  public void setVisible(boolean value) {
    mainFrame.setVisible(value);
  }

  /** Create the menu selector panel. */
  private void createLeftSidePanel() {
    leftSidePanel = new JPanel(new BorderLayout());

    JPanel cardSelectorPanel = new JPanel(new GridLayout(4, 1, 0, 0));
    cardSelectorPanel.setPreferredSize(new Dimension(200, 300));
    Font cardSelectorBtnsFont = new Font("Arial Black", Font.BOLD, 16);
    int cardSelectorBtnsHAlignment = SwingConstants.LEFT;

    btnHome = new JButton(HOME_CARD_NAME);
    btnHome.setFont(cardSelectorBtnsFont);
    btnHome.setHorizontalAlignment(cardSelectorBtnsHAlignment);
    cardSelectorPanel.add(btnHome);

    btnSearch = new JButton(SEARCH_CARD_NAME);
    btnSearch.setFont(cardSelectorBtnsFont);
    btnSearch.setHorizontalAlignment(cardSelectorBtnsHAlignment);
    cardSelectorPanel.add(btnSearch);

    btnPlaylists = new JButton("Your playlists");
    btnPlaylists.setFont(cardSelectorBtnsFont);
    btnPlaylists.setHorizontalAlignment(cardSelectorBtnsHAlignment);
    cardSelectorPanel.add(btnPlaylists);

    btnRecently = new JButton("Recently Played");
    btnRecently.setFont(cardSelectorBtnsFont);
    btnRecently.setHorizontalAlignment(cardSelectorBtnsHAlignment);
    cardSelectorPanel.add(btnRecently);

    leftSidePanel.add(cardSelectorPanel, BorderLayout.NORTH);
  }

  /** Create the changing panel. */
  private void createCardsPanel() {
    cardsPanel = new JPanel(new CardLayout());
    cardsPanel.add(new HomePanel(), HOME_CARD_NAME);
    cardsPanel.add(new SearchPanel(musicPlayer), SEARCH_CARD_NAME);
    cardsPanel.add(new PlaylistsPanel(musicPlayer).getPanel(), PLAYLISTS_CARD_NAME);
    cardsPanel.add(new RecentlyPlayedPane(musicPlayer), RECENTLY_CARD_NAME);
  }

  /** Create the panel with the user settings. */
  private void createTopPanel(String username) {
    topPanel = new JPanel(new BorderLayout());

    logoLabel = new JLabel(App.NAME, AppLogo.get(), JLabel.CENTER);
    logoLabel.setBorder(new EmptyBorder(10, 30, 10, 0));
    logoLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
    topPanel.add(logoLabel, BorderLayout.WEST);

    JMenuBar menuBar = new JMenuBar();
    JMenu menu = new JMenu(username);
    upgradeMenuItem = new JMenuItem("Upgrade");
    menu.add(upgradeMenuItem);
    logOutMenuItem = new JMenuItem("Log out");
    menu.add(logOutMenuItem);
    menuBar.add(menu);
    btnXmlLoader = new UiButton();
    btnXmlLoader.setColor(BTN_SONGLOADER_COLOR);
    btnXmlLoader.setSize(20, 20);

    JPanel rightSidePanel = new JPanel(new BorderLayout(0, 20));
    JPanel btnWrapper = new JPanel(new FlowLayout());
    btnWrapper.add(btnXmlLoader);
    rightSidePanel.add(btnWrapper, BorderLayout.CENTER);
    rightSidePanel.add(menuBar, BorderLayout.NORTH);
    topPanel.add(rightSidePanel, BorderLayout.EAST);
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
          }
        });

    btnPlaylists.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            ((CardLayout) cardsPanel.getLayout()).show(cardsPanel, PLAYLISTS_CARD_NAME);
          }
        });

    btnRecently.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            ((CardLayout) cardsPanel.getLayout()).show(cardsPanel, RECENTLY_CARD_NAME);
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
                System.out.println(
                    "You chose to open this file: " + chooser.getSelectedFile().getAbsolutePath());
                Controller controller = Controller.getSingleton();
                controller.loadXml(chooser.getSelectedFile().getAbsolutePath());
              } else {
                btnXmlLoader.setTurnedOn(false);
              }
            }
          }
        });
  }
}
