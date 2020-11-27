package um.tds.nappmusic.gui;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JToggleButton;
import java.awt.CardLayout;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.Box;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import um.tds.nappmusic.gui.cards.HomePanel;
import um.tds.nappmusic.gui.cards.PlaylistsPanel;
import um.tds.nappmusic.gui.cards.RecentlyPlayedPane;
import um.tds.nappmusic.gui.cards.SearchPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JPopupMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.Component;

@SuppressWarnings("serial")
public class MainWindow extends JFrame implements ActionListener {

  private PlayerPanel playerPanel;
  
  private JPanel topPanel;
  private JMenu menu;
  private JMenuItem upgradeMenuItem;
  private JMenuItem signOutMenuItem;
  
  private JPanel cardsPanel;
  private HomePanel homePanel;
  private static final String HOME_CARD_NAME = "Home";
  private SearchPanel searchPanel;
  private static final String SEARCH_CARD_NAME = "Search";
  private PlaylistsPanel playlistsPanel;
  private static final String PLAYLISTS_CARD_NAME = "Playlists";
  private RecentlyPlayedPane recentlyPlayedPane;
  private static final String RECENTLY_CARD_NAME = "Recently";
  
  private JPanel leftSidePanel;
  private JButton btnHome;
  private JButton btnSearch;
  private JButton btnPlaylists;
  private JButton btnRecently;
  private JMenuBar menuBar;

  /**
   * Create the application.
   */
  public MainWindow() {
    super("NappMusic");
    initialize();
  }

  /**
   * Create the menu selector panel.
   */
  private void createLeftSidePanel() {
    leftSidePanel = new JPanel(new BorderLayout());

    JPanel cardSelectorPanel = new JPanel(new GridLayout(4, 1, 0, 0));
    cardSelectorPanel.setPreferredSize(new Dimension(200, 300));
    Font cardSelectorBtnsFont = new Font("Arial Black", Font.BOLD, 16);
    int cardSelectorBtnsHAlignment = SwingConstants.LEFT;

    btnHome = new JButton(HOME_CARD_NAME);
    btnHome.setFont(cardSelectorBtnsFont);
    btnHome.setHorizontalAlignment(cardSelectorBtnsHAlignment);
    btnHome.addActionListener(this);
    cardSelectorPanel.add(btnHome);

    btnSearch = new JButton(SEARCH_CARD_NAME);
    btnSearch.setFont(cardSelectorBtnsFont);
    btnSearch.setHorizontalAlignment(cardSelectorBtnsHAlignment);
    btnSearch.addActionListener(this);
    cardSelectorPanel.add(btnSearch);

    btnPlaylists = new JButton("Tus playlists");
    btnPlaylists.setFont(cardSelectorBtnsFont);
    btnPlaylists.setHorizontalAlignment(cardSelectorBtnsHAlignment);
    btnPlaylists.addActionListener(this);
    cardSelectorPanel.add(btnPlaylists);

    btnRecently = new JButton("Recientemente...");
    btnRecently.setFont(cardSelectorBtnsFont);
    btnRecently.setHorizontalAlignment(cardSelectorBtnsHAlignment);
    btnRecently.addActionListener(this);
    cardSelectorPanel.add(btnRecently);

    leftSidePanel.add(cardSelectorPanel, BorderLayout.NORTH);
  }

  /**
   * Create the changing panel.
   */
  private void createCardsPanel() {
    cardsPanel = new JPanel(new CardLayout());

    homePanel = new HomePanel();
    cardsPanel.add(homePanel, HOME_CARD_NAME);

    searchPanel = new SearchPanel();
    cardsPanel.add(searchPanel, SEARCH_CARD_NAME);

    playlistsPanel = new PlaylistsPanel();
    cardsPanel.add(playlistsPanel, PLAYLISTS_CARD_NAME);

    recentlyPlayedPane = new RecentlyPlayedPane();
    cardsPanel.add(recentlyPlayedPane, RECENTLY_CARD_NAME);
  }

  /**
   * Create the panel with the user settings.
   */
  private void createTopPanel(String username) {
    topPanel = new JPanel(new BorderLayout());
    
    menuBar = new JMenuBar();
    menu = new JMenu("Username");
    upgradeMenuItem = new JMenuItem("Mejora tu cuenta");
    menu.add(upgradeMenuItem);
    signOutMenuItem = new JMenuItem("Cerrar sesión");
    menu.add(signOutMenuItem);
    menuBar.add(menu);
    
    topPanel.add(menuBar, BorderLayout.EAST);
  }

  /**
   * Initialize the contents of the frame.
   */
  private void initialize() {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.getContentPane().setLayout(new BorderLayout());

    playerPanel = new PlayerPanel();
    playerPanel.changeSongData("src/main/resources/torero.jpg", "Emilio Domínguez Sánchez", "Torero");
    this.getContentPane().add(playerPanel, BorderLayout.SOUTH);

    createLeftSidePanel();
    this.getContentPane().add(leftSidePanel, BorderLayout.WEST);

    createCardsPanel();
    this.getContentPane().add(cardsPanel, BorderLayout.CENTER);

    createTopPanel("Username");
    this.getContentPane().add(topPanel, BorderLayout.NORTH);

    this.pack();
    this.setMinimumSize(this.getSize());
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == btnHome) {
      ((CardLayout) cardsPanel.getLayout()).show(cardsPanel, HOME_CARD_NAME);
      cardsPanel.revalidate();
    } else if (e.getSource() == btnSearch) {
      ((CardLayout) cardsPanel.getLayout()).show(cardsPanel, SEARCH_CARD_NAME);
      cardsPanel.revalidate();
    } else if (e.getSource() == btnPlaylists) {
      ((CardLayout) cardsPanel.getLayout()).show(cardsPanel, PLAYLISTS_CARD_NAME);
      cardsPanel.revalidate();
    } else if (e.getSource() == btnRecently) {
      ((CardLayout) cardsPanel.getLayout()).show(cardsPanel, RECENTLY_CARD_NAME);
      cardsPanel.revalidate();
    } else if (e.getSource() == menu) {
      System.out.println("a");
    }
  }
  
  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Throwable e) {
      e.printStackTrace();
    }
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          MainWindow window = new MainWindow();
          window.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }
}
