package um.tds.nappmusic.gui;

import com.toedter.calendar.JCalendar;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import um.tds.nappmusic.app.App;
import um.tds.nappmusic.controller.Controller;

public class RegisterWindow {
  private JDialog dialogWin;
  private JButton registerButton;
  private JButton cancelButton;

  private JLabel nameLbl;
  private JLabel surnameLbl;
  private JLabel birthDateLbl;
  private JLabel emailLbl;
  private JLabel usernameLbl;
  private JLabel passwordLbl;
  private JLabel passwordChkLbl;
  private JTextField nameTxt;
  private JTextField surnameTxt;
  private JCalendar birthDateCal;
  private JTextField emailTxt;
  private JTextField usernameTxt;
  private JPasswordField passwordTxt;
  private JPasswordField passwordChkTxt;
  private JLabel nameErrLbl;
  private JLabel surnameErrLbl;
  private JLabel birthDateErrLbl;
  private JLabel emailErrLbl;
  private JLabel usernameErrLbl;
  private JLabel passwordErrLbl;

  public RegisterWindow(Frame owner) {
    dialogWin = new JDialog(owner, App.NAME + " - Register");
    dialogWin.setLocationRelativeTo(null);
    dialogWin.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    dialogWin.getContentPane().setLayout(new BorderLayout());

    Container contentPane = dialogWin.getContentPane();

    JPanel personalData = new JPanel();
    contentPane.add(personalData);
    personalData.setBorder(
        new TitledBorder(null, "Register Data", TitledBorder.LEADING, TitledBorder.TOP));
    personalData.setLayout(new BoxLayout(personalData, BoxLayout.Y_AXIS));

    personalData.add(initNameEntry());
    personalData.add(initSurnameEntry());
    personalData.add(initEmailEntry());
    personalData.add(initUserEntry());
    personalData.add(initPasswordEntry());
    personalData.add(initBirthDateEntry());
    initButtonPanel();
    hideAllErrors();

    dialogWin.revalidate();
    dialogWin.pack();
    dialogWin.setResizable(false);
  }

  public void showWindow() {
    dialogWin.setLocationRelativeTo(null);
    dialogWin.setVisible(true);
  }

  private JPanel createEntry(JLabel label, JTextField textField, JLabel errLabel) {
    JPanel onlyEntry = new JPanel();
    onlyEntry.add(label);
    onlyEntry.add(textField);

    JPanel entryAndErr = new JPanel(new BorderLayout(0, 0));
    entryAndErr.setAlignmentX(Component.LEFT_ALIGNMENT);
    entryAndErr.add(onlyEntry, BorderLayout.CENTER);
    entryAndErr.add(errLabel, BorderLayout.SOUTH);
    errLabel.setForeground(Color.RED);
    return entryAndErr;
  }

  private JPanel initNameEntry() {
    nameLbl = new JLabel("Name: ", JLabel.RIGHT);
    nameTxt = new JTextField();
    nameErrLbl = new JLabel("", JLabel.CENTER);
    fixedSize(nameLbl, 75, 20);
    fixedSize(nameTxt, 270, 20);
    fixedSize(nameErrLbl, 224, 15);
    return createEntry(nameLbl, nameTxt, nameErrLbl);
  }

  private JPanel initSurnameEntry() {
    surnameLbl = new JLabel("Surname: ", JLabel.RIGHT);
    surnameTxt = new JTextField();
    surnameErrLbl = new JLabel("", JLabel.CENTER);
    fixedSize(surnameLbl, 75, 20);
    fixedSize(surnameTxt, 270, 20);
    fixedSize(surnameErrLbl, 255, 15);
    return createEntry(surnameLbl, surnameTxt, surnameErrLbl);
  }

  private JPanel initEmailEntry() {
    emailLbl = new JLabel("Email: ", JLabel.RIGHT);
    emailTxt = new JTextField();
    emailErrLbl = new JLabel("", JLabel.CENTER);
    fixedSize(emailLbl, 75, 20);
    fixedSize(emailTxt, 270, 20);
    fixedSize(emailErrLbl, 150, 15);
    return createEntry(emailLbl, emailTxt, emailErrLbl);
  }

  private JPanel initUserEntry() {
    usernameLbl = new JLabel("Usuario: ", JLabel.RIGHT);
    usernameTxt = new JTextField();
    usernameErrLbl = new JLabel("", JLabel.CENTER);
    fixedSize(usernameLbl, 75, 20);
    fixedSize(usernameTxt, 270, 20);
    fixedSize(usernameErrLbl, 150, 15);
    return createEntry(usernameLbl, usernameTxt, usernameErrLbl);
  }

  private JPanel initPasswordEntry() {
    passwordLbl = new JLabel("Password: ", JLabel.RIGHT);
    passwordTxt = new JPasswordField();
    passwordChkLbl = new JLabel("Confirm: ", JLabel.RIGHT);
    passwordChkTxt = new JPasswordField();
    passwordErrLbl = new JLabel("", JLabel.CENTER);
    fixedSize(passwordLbl, 75, 20);
    fixedSize(passwordTxt, 100, 20);
    fixedSize(passwordChkLbl, 60, 20);
    fixedSize(passwordChkTxt, 100, 20);

    JPanel onlyEntry = new JPanel();
    onlyEntry.add(passwordLbl);
    onlyEntry.add(passwordTxt);
    onlyEntry.add(passwordChkLbl);
    onlyEntry.add(passwordChkTxt);

    JPanel entryAndErr = new JPanel(new BorderLayout(0, 0));
    entryAndErr.setAlignmentX(Component.LEFT_ALIGNMENT);
    entryAndErr.add(onlyEntry, BorderLayout.CENTER);
    entryAndErr.add(passwordErrLbl, BorderLayout.SOUTH);
    passwordErrLbl.setForeground(Color.RED);
    return entryAndErr;
  }

  private JPanel initBirthDateEntry() {
    birthDateLbl = new JLabel("Birth date: ", JLabel.RIGHT);
    birthDateCal = new JCalendar();
    birthDateErrLbl = new JLabel("", JLabel.CENTER);
    fixedSize(birthDateLbl, 75, 20);
    fixedSize(birthDateCal, 270, 170);
    fixedSize(birthDateErrLbl, 150, 15);

    JPanel onlyEntry = new JPanel();
    onlyEntry.add(birthDateLbl);
    onlyEntry.add(birthDateCal);

    JPanel entryAndErr = new JPanel(new BorderLayout(0, 0));
    entryAndErr.setAlignmentX(Component.LEFT_ALIGNMENT);
    entryAndErr.add(onlyEntry, BorderLayout.CENTER);
    entryAndErr.add(birthDateErrLbl, BorderLayout.SOUTH);
    birthDateErrLbl.setForeground(Color.RED);
    return entryAndErr;
  }

  private void initButtonPanel() {
    registerButton = new JButton("Register");
    cancelButton = new JButton("Cancel");
    initRegisterButtonHandler();
    initCancelButtonHandler();

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    buttonPanel.setBorder(new EmptyBorder(5, 0, 0, 0));
    buttonPanel.add(registerButton);
    buttonPanel.add(cancelButton);
    dialogWin.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
  }

  private void initRegisterButtonHandler() {
    registerButton.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            if (checkFields()) {
              boolean succesfulRegister =
                  Controller.getSingleton()
                      .registerUser(
                          nameTxt.getText(),
                          surnameTxt.getText(),
                          dateToLocalDate(birthDateCal.getDate()),
                          emailTxt.getText(),
                          usernameTxt.getText(),
                          new String(passwordTxt.getPassword()));
              if (succesfulRegister) {
                JOptionPane.showMessageDialog(
                    dialogWin,
                    "You were succesfully registered.",
                    "Registration complete",
                    JOptionPane.INFORMATION_MESSAGE);
              } else {
                JOptionPane.showMessageDialog(
                    dialogWin,
                    "Something went wrong. You were not registered.\n",
                    "Oops!",
                    JOptionPane.ERROR_MESSAGE);
              }
              dialogWin.dispose();
            }
          }
        });
  }

  private void initCancelButtonHandler() {
    cancelButton.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            dialogWin.dispose();
          }
        });
  }

  private boolean checkFields() {
    hideAllErrors();
    boolean ok = true;

    String password = new String(passwordTxt.getPassword());
    String password2 = new String(passwordChkTxt.getPassword());

    // Empty field checks

    if (nameTxt.getText().trim().isEmpty()) {
      nameErrLbl.setText("Name needed");
      nameErrLbl.setVisible(true);
      nameLbl.setForeground(Color.RED);
      nameTxt.setBorder(BorderFactory.createLineBorder(Color.RED));
      ok = false;
    }
    if (surnameTxt.getText().trim().isEmpty()) {
      surnameErrLbl.setText("Surname needed");
      surnameErrLbl.setVisible(true);
      surnameLbl.setForeground(Color.RED);
      surnameTxt.setBorder(BorderFactory.createLineBorder(Color.RED));
      ok = false;
    }
    if (emailTxt.getText().trim().isEmpty()) {
      emailErrLbl.setText("Email needed");
      emailErrLbl.setVisible(true);
      emailLbl.setForeground(Color.RED);
      emailTxt.setBorder(BorderFactory.createLineBorder(Color.RED));
      ok = false;
    }
    if (usernameTxt.getText().trim().isEmpty()) {
      usernameErrLbl.setText("Username needed");
      usernameErrLbl.setVisible(true);
      usernameLbl.setForeground(Color.RED);
      usernameTxt.setBorder(BorderFactory.createLineBorder(Color.RED));
      ok = false;
    }
    if (password.isEmpty()) {
      passwordErrLbl.setText("The first password field is empty");
      passwordErrLbl.setVisible(true);
      passwordLbl.setForeground(Color.RED);
      passwordTxt.setBorder(BorderFactory.createLineBorder(Color.RED));
      ok = false;
    }
    if (password2.isEmpty()) {
      passwordErrLbl.setText("The second password field is empty");
      passwordErrLbl.setVisible(true);
      passwordChkLbl.setForeground(Color.RED);
      passwordChkTxt.setBorder(BorderFactory.createLineBorder(Color.RED));
      ok = false;
    }

    // Integrity Checks

    if (!password.equals(password2)) {
      passwordErrLbl.setText("Passwords do not match");
      passwordErrLbl.setVisible(true);
      passwordLbl.setForeground(Color.RED);
      passwordChkLbl.setForeground(Color.RED);
      passwordTxt.setBorder(BorderFactory.createLineBorder(Color.RED));
      passwordChkTxt.setBorder(BorderFactory.createLineBorder(Color.RED));
      ok = false;
    }
    if (!usernameErrLbl.getText().isEmpty()
        && Controller.getSingleton().isUserRegistered(usernameTxt.getText())) {
      usernameErrLbl.setText("That username is already in use");
      usernameErrLbl.setVisible(true);
      usernameLbl.setForeground(Color.RED);
      usernameTxt.setBorder(BorderFactory.createLineBorder(Color.RED));
      ok = false;
    }
    // TODO
    // if (Period.between(birthDateCal.getDate(), LocalDate.now()).getYears() < App.MINIMUM_AGE) {
    //   birthDateErrLbl.setVisible(true);
    //   birthDateLbl.setForeground(Color.RED);
    //   birthDateCal.setBorder(BorderFactory.createLineBorder(Color.RED));
    //   ok = false;
    // }

    dialogWin.revalidate();
    dialogWin.pack();
    return ok;
  }

  private void hideAllErrors() {
    nameErrLbl.setVisible(false);
    surnameErrLbl.setVisible(false);
    birthDateErrLbl.setVisible(false);
    emailErrLbl.setVisible(false);
    usernameErrLbl.setVisible(false);
    passwordErrLbl.setVisible(false);
    birthDateErrLbl.setVisible(false);

    Border border = new JTextField().getBorder();
    nameTxt.setBorder(border);
    surnameTxt.setBorder(border);
    emailTxt.setBorder(border);
    usernameTxt.setBorder(border);
    passwordTxt.setBorder(border);
    passwordChkTxt.setBorder(border);
    passwordTxt.setBorder(border);
    passwordChkTxt.setBorder(border);
    usernameTxt.setBorder(border);
    birthDateCal.setBorder(border);

    nameLbl.setForeground(Color.BLACK);
    surnameLbl.setForeground(Color.BLACK);
    emailLbl.setForeground(Color.BLACK);
    usernameLbl.setForeground(Color.BLACK);
    passwordLbl.setForeground(Color.BLACK);
    passwordChkLbl.setForeground(Color.BLACK);
    birthDateLbl.setForeground(Color.BLACK);
  }

  /** Fixes the size of a component */
  private void fixedSize(JComponent o, int x, int y) {
    Dimension d = new Dimension(x, y);
    o.setMinimumSize(d);
    o.setMaximumSize(d);
    o.setPreferredSize(d);
  }

  private LocalDate dateToLocalDate(Date date) {
    // TODO system default? I can only sense danger
    return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
  }
}
