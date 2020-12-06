package um.tds.nappmusic.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import um.tds.nappmusic.app.App;
import um.tds.nappmusic.app.AppLogo;
import um.tds.nappmusic.controller.Controller;

@SuppressWarnings("serial")
public class LoginWindow extends JFrame {
  private static final int FIELDS_WIDTH = 15;

  private JTextField nickField;
  private JPasswordField passwordField;

  /** Create the window. */
  public LoginWindow() {
    super(App.NAME + " - Login");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.getContentPane().setLayout(new BorderLayout());

    this.getContentPane().add(createTopPanel(), BorderLayout.NORTH);
    this.getContentPane().add(createLoginPanel(), BorderLayout.CENTER);

    this.pack();
    this.setResizable(false);
  }

  private JPanel createTopPanel() {
    JPanel topPanel = new JPanel();
    topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

    JLabel lblTitulo = new JLabel(App.NAME, AppLogo.get(), JLabel.CENTER);
    lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 20));
    lblTitulo.setForeground(Color.DARK_GRAY);
    topPanel.add(lblTitulo);

    return topPanel;
  }

  private JPanel createLoginPanel() {
    JPanel loginPanel = new JPanel();
    loginPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
    loginPanel.setLayout(new BorderLayout());

    loginPanel.add(createFieldsPanel(), BorderLayout.NORTH);
    loginPanel.add(createButtonsPanel(), BorderLayout.SOUTH);

    return loginPanel;
  }

  private JPanel createFieldsPanel() {
    JPanel centeringWrapperPanel = new JPanel(new FlowLayout());
    JPanel fieldsPanel = new JPanel();
    fieldsPanel.setBorder(new TitledBorder(null, "Login", TitledBorder.LEADING, TitledBorder.TOP));
    fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));

    // Nick field
    JPanel nickFieldPanel = new JPanel();
    nickFieldPanel.setLayout(new BorderLayout());

    JLabel userLabel = new JLabel("User: ");
    // Put the text next to the label
    userLabel.setHorizontalAlignment(SwingConstants.RIGHT);
    userLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
    nickFieldPanel.add(userLabel);

    nickField = new JTextField(FIELDS_WIDTH);
    nickFieldPanel.add(nickField, BorderLayout.EAST);

    fieldsPanel.add(nickFieldPanel);

    // Password field
    JPanel passwordFieldPanel = new JPanel();
    passwordFieldPanel.setLayout(new BorderLayout());

    JLabel passwordLabel = new JLabel("Password: ");
    passwordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
    passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
    passwordFieldPanel.add(passwordLabel);

    passwordField = new JPasswordField(FIELDS_WIDTH);
    passwordFieldPanel.add(passwordField, BorderLayout.EAST);

    fieldsPanel.add(passwordFieldPanel);

    centeringWrapperPanel.add(fieldsPanel);
    return centeringWrapperPanel;
  }

  private JPanel createButtonsPanel() {
    JPanel buttonsPanel = new JPanel(new BorderLayout());
    buttonsPanel.setBorder(new EmptyBorder(5, 0, 5, 0));

    JPanel loginRegisterButtonsPanel = new JPanel();
    buttonsPanel.add(loginRegisterButtonsPanel, BorderLayout.WEST);

    JButton btnLogin = new JButton("Login");
    loginRegisterButtonsPanel.add(btnLogin);
    btnLogin.setVerticalAlignment(SwingConstants.BOTTOM);
    btnLogin.addActionListener(
        event -> {
          boolean login =
              Controller.getSingleton()
                  .logIn(nickField.getText(), new String(passwordField.getPassword()));
          if (login) {
            MainWindow window = new MainWindow();
            window.setVisible(true);
            this.dispose();
          } else {
            JOptionPane.showMessageDialog(
                this,
                "Nombre de usuario o contraseña no válido",
                "Error",
                JOptionPane.ERROR_MESSAGE);
          }
        });

    JButton btnRegistro = new JButton("Register");
    loginRegisterButtonsPanel.add(btnRegistro);
    btnRegistro.setVerticalAlignment(SwingConstants.BOTTOM);
    btnRegistro.addActionListener(
        event -> {
          RegisterWindow registerPopup = new RegisterWindow(this);
          registerPopup.showWindow();
        });

    JPanel panelBotonSalir = new JPanel();
    buttonsPanel.add(panelBotonSalir, BorderLayout.EAST);

    JButton btnSalir = new JButton("Exit");
    btnSalir.setVerticalAlignment(SwingConstants.BOTTOM);
    btnSalir.addActionListener(
        event -> {
          this.dispose();
          System.exit(0);
        });
    panelBotonSalir.add(btnSalir);

    return buttonsPanel;
  }

  /** Launch the application. */
  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Throwable e) {
      e.printStackTrace();
    }
    EventQueue.invokeLater(
        () -> {
          try {
            LoginWindow window = new LoginWindow();
            window.setLocationRelativeTo(null);
            window.setVisible(true);
          } catch (Exception e) {
            e.printStackTrace();
          }
        });
  }
}
