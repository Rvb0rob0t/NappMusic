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
  private static final String RECENTLY_CARD_NAME = "Recently Played";
  private static final Color BTN_SONGLOADER_COLOR = Color.RED;

  private Controller controller;

  private MusicPlayer musicPlayer;

  private JFrame mainFrame;
  private JPanel topPanel;
  private JLabel logoLabel;
  private JMenuItem upgradeMenuItem;
  private JMenuItem generatePdfMenuItem;
  private JMenuItem logOutMenuItem;
  private JPanel cardsPanel;
  private JPanel leftSidePanel;

  private JButton btnHome;
  private JButton btnSearch;
  private JButton btnPlaylists;
  private JButton btnRecently;
  private UiButton btnXmlLoader;

  private HomePanel homePanel;
  private SearchPanel searchPanel;
  private PlaylistsPanel playlistsPanel;
  private RecentlyPlayedPanel recentlyPlayedPanel;

  /**
   * Create the application.
   *
   * @param controller
   */
  public MainWindow() {
    controller = Controller.getSingleton();

    mainFrame = new JFrame(App.NAME);
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainFrame.getContentPane().setLayout(new BorderLayout(10, 0));

    musicPlayer = new MusicPlayer();
    mainFrame.getContentPane().add(musicPlayer.getPanel(), BorderLayout.SOUTH);

    createLeftSidePanel();
    mainFrame.getContentPane().add(leftSidePanel, BorderLayout.WEST);

    createCardsPanel();
    mainFrame.getContentPane().add(cardsPanel, BorderLayout.CENTER);

    createTopPanel();
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
    int cardSelectorBtnsHAlignment = SwingConstants.LEFT;

    btnHome = new JButton(HOME_CARD_NAME);
    btnHome.setFont(AppFonts.CARD_SELECTOR_FONT);
    btnHome.setHorizontalAlignment(cardSelectorBtnsHAlignment);
    cardSelectorPanel.add(btnHome);

    btnSearch = new JButton(SEARCH_CARD_NAME);
    btnSearch.setFont(AppFonts.CARD_SELECTOR_FONT);
    btnSearch.setHorizontalAlignment(cardSelectorBtnsHAlignment);
    cardSelectorPanel.add(btnSearch);

    btnPlaylists = new JButton(PLAYLISTS_CARD_NAME);
    btnPlaylists.setFont(AppFonts.CARD_SELECTOR_FONT);
    btnPlaylists.setHorizontalAlignment(cardSelectorBtnsHAlignment);
    cardSelectorPanel.add(btnPlaylists);

    btnRecently = new JButton(RECENTLY_CARD_NAME);
    btnRecently.setFont(AppFonts.CARD_SELECTOR_FONT);
    btnRecently.setHorizontalAlignment(cardSelectorBtnsHAlignment);
    cardSelectorPanel.add(btnRecently);

    leftSidePanel.add(cardSelectorPanel, BorderLayout.NORTH);
  }

  /** Create the changing panel. */
  private void createCardsPanel() {
    cardsPanel = new JPanel(new CardLayout());
    homePanel = new HomePanel();
    cardsPanel.add(homePanel.getPanel(), HOME_CARD_NAME);
    searchPanel = new SearchPanel(musicPlayer);
    cardsPanel.add(searchPanel.getPanel(), SEARCH_CARD_NAME);
    playlistsPanel = new PlaylistsPanel(musicPlayer);
    cardsPanel.add(playlistsPanel.getPanel(), PLAYLISTS_CARD_NAME);
    recentlyPlayedPanel = new RecentlyPlayedPanel(musicPlayer);
    cardsPanel.add(recentlyPlayedPanel.getPanel(), RECENTLY_CARD_NAME);
  }

  /** Create the panel with the user settings. */
  private void createTopPanel() {
    topPanel = new JPanel(new BorderLayout());

    logoLabel = new JLabel(App.NAME, AppLogo.get(), JLabel.CENTER);
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
                      + controller.getCurrentUser().getDiscount().calculatePrice()
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
