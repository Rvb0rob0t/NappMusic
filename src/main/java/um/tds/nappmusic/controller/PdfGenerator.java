package um.tds.nappmusic.controller;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.stream.Collectors;
import um.tds.nappmusic.domain.Playlist;
import um.tds.nappmusic.domain.User;

public class PdfGenerator {

  private static final String TITLE_COLUMN_NAME = "Title";
  private static final String AUTHOR_COLUMN_NAME = "Artist";
  private static final String STYLES_COLUMN_NAME = "Style";
  private static final String STYLES_SEPARATOR = ", ";

  private static final Font TITLE_FONT = new Font(FontFamily.HELVETICA, 22, Font.BOLD);
  private static final Font PLAYLIST_NAME_FONT = new Font(FontFamily.HELVETICA, 18, Font.UNDERLINE);

  /**
   * Write a pdf with the user's playlists
   *
   * @param user The owner of the playlists
   * @param filePath The path of the new pdf
   * @throws FileNotFoundException if the file exists but is a directory rather than a regular file,
   *     does not exist but cannot be created, or cannot be opened for any other reason
   * @throws DocumentException on error
   */
  public void userPlaylistsToPdf(User user, String filePath)
      throws FileNotFoundException, DocumentException {
    Document output = initializeWriter(filePath);
    output.open();

    output.add(
        new Paragraph("Hello " + user.getUsername() + ", these are your playlists", TITLE_FONT));

    List<Playlist> playlists = user.getPlaylists();
    for (Playlist playlist : playlists) {
      output.add(new Paragraph(playlist.getName(), PLAYLIST_NAME_FONT));
      output.add(Chunk.SPACETABBING);
      output.add(playlistToPdfPTable(playlist));
      output.add(Chunk.NEWLINE);
    }

    output.close();
  }

  private static PdfPTable playlistToPdfPTable(Playlist playlist) {
    PdfPTable table = new PdfPTable(3);
    addRowToTable(table, 3, TITLE_COLUMN_NAME, AUTHOR_COLUMN_NAME, STYLES_COLUMN_NAME);
    table.setHeaderRows(1);
    playlist
        .getSongs()
        .forEach(
            song ->
                addRowToTable(
                    table,
                    3,
                    song.getTitle(),
                    song.getAuthor(),
                    song.getStyles().stream().collect(Collectors.joining(STYLES_SEPARATOR))));
    return table;
  }

  private static void addRowToTable(PdfPTable table, int numColumns, String... columnStrings) {
    if (columnStrings.length != numColumns) {
      throw new IndexOutOfBoundsException(
          "number of columns is not equals to the number of strings arguments");
    }
    for (int i = 0; i < columnStrings.length; i++) {
      table.addCell(new PdfPCell(new Paragraph(columnStrings[i])));
    }
  }

  private static Document initializeWriter(String outputPath)
      throws FileNotFoundException, DocumentException {
    FileOutputStream outputFile = new FileOutputStream(outputPath);
    Document output = new Document();
    PdfWriter.getInstance(output, outputFile);
    return output;
  }
}
