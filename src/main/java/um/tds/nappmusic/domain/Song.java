package um.tds.nappmusic.domain;

import java.util.List;
import um.tds.nappmusic.dao.Identifiable;

public class Song implements Identifiable {
  private int id;
  private String title;
  private String author;
  private List<String> styles;
  private String filePath;
  private int numPlays;

  public Song() {}

  public Song(String title, String author, List<String> styles, String filePath, int numPlays) {
    this.title = title;
    this.author = author;
    this.styles = styles;
    this.filePath = filePath;
    this.numPlays = numPlays;
  }

  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }

    if (obj.getClass() != this.getClass()) {
      return false;
    }

    Song song = (Song) obj;

    return this.title.equals(song.title) && this.author.equals(song.author);
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

  public List<String> getStyles() {
    return styles;
  }

  public String getFilePath() {
    return filePath;
  }

  public int getNumPlays() {
    return numPlays;
  }

  @Override
  public void setId(int id) {
    this.id = id;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public void setStyles(List<String> styles) {
    this.styles = styles;
  }

  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }

  public void setNumPlays(int numPlays) {
    this.numPlays = numPlays;
  }

  public void incrementNumPlays() {
    numPlays++;
  }
}
