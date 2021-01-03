package um.tds.nappmusic.controller;

import static org.junit.jupiter.api.Assertions.fail;

import com.itextpdf.text.DocumentException;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Arrays;
import javax.swing.JFileChooser;
import um.tds.nappmusic.dao.DaoException;
import um.tds.nappmusic.dao.DaoFactory;
import um.tds.nappmusic.domain.Song;
import um.tds.nappmusic.domain.SongCatalog;
import um.tds.nappmusic.domain.UserCatalog;

public class PdfGeneratorTest {
  PdfGenerator pdfGenerator;

  public PdfGeneratorTest() {
    pdfGenerator = new PdfGenerator();
  }

  public static void main(String[] args) {
    // Register and logs a user with a single song playlist
    Controller controller;
    try {
      // controller = Controller.getSingleton();
      UserCatalog userCatalog = UserCatalog.getSingleton();
      SongCatalog songCatalog = SongCatalog.getSingleton();
      DaoFactory factory = DaoFactory.getSingleton();
      controller = Controller.getSingleton(userCatalog, songCatalog, factory);
    } catch (DaoException e) {
      e.printStackTrace();
      fail("Failed database initialization");
      return;
    }

    controller.registerUser(
        "Rubén", "Gaspar", LocalDate.of(199, 8, 18), "ruben.gasparm@um.es", "1234", "1234");
    controller.logIn("1234", "1234");
    Song song =
        new Song(
            "Title0",
            "Author0",
            Arrays.asList("Style0", "Style1", "Style2", "Style2", "Style2"),
            "/home/useredsa/Music/song0.mp4",
            0);
    controller.addToPlaylist(controller.createPlaylist("Test playlist 1"), song);
    controller.addToPlaylist(controller.createPlaylist("Test playlist 2"), song);

    JFileChooser chooser = new JFileChooser();
    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
      System.out.println(
          "You chose to save the pdf in this file: " + chooser.getSelectedFile().getAbsolutePath());
      try {
        controller.generatePlaylistsPdf(chooser.getSelectedFile().getAbsolutePath());
      } catch (FileNotFoundException | DocumentException e) {
        e.printStackTrace();
      }
    }

    controller.removeUser(controller.getCurrentUser());
  }
}
