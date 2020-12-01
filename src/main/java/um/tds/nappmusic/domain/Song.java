package um.tds.nappmusic.domain;

import java.util.ArrayList;
import um.tds.nappmusic.dao.Identifiable;

public class Song implements Identifiable {
  private int id;
  private String title;
  private String author;
  private ArrayList<String> styles;
  private String filePath;
  private int numPlays;

  public Song() {}

  public Song(
      String title, String author, ArrayList<String> styles, String filePath, int numPlays) {
    this.title = title;
    this.author = author;
    this.styles = styles;
    this.filePath = filePath;
    this.numPlays = numPlays;
  }

  public int getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getAuthor() {
    return author;
  }

  public ArrayList<String> getStyles() {
    return styles;
  }

  public String getFilePath() {
    return filePath;
  }

  public int getNumPlays() {
    return numPlays;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }

  public void setNumPlays(int numPlays) {
    this.numPlays = numPlays;
  }
}
