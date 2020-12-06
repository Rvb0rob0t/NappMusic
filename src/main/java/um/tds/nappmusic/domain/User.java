package um.tds.nappmusic.domain;

import java.util.ArrayList;
import java.util.List;
import um.tds.nappmusic.dao.Identifiable;

public class User implements Identifiable {
  private int id = 0;
  private String name;
  private String surname;
  private String birthDate;
  private String email;
  private String username;
  private String password;
  private boolean premium;
  private Discount discount;
  private ArrayList<Playlist> playlists;
  // DIFFERENCE We use Playlist for almost everything (that is a list of songs).
  // The idea is that the user can also replay the list of recently played songs.
  private Playlist recent;

  public User() {}

  /**
   * Create a user.
   *
   * @param name The name of the user
   * @param surname The surname of the user
   * @param birthDate The date of birth of the user
   * @param email The email of the user
   * @param username The nickname of the user
   * @param password The password of the user
   * @param premium true if the user is premium, false otherwise
   * @param discount discount that can be applied to the user for upgrading to premium
   * @param playlists playlists saved by the user
   * @param recent playlist composed by the 10 most recently played songs of the user
   */
  public User(
      String name,
      String surname,
      String birthDate,
      String email,
      String username,
      String password,
      boolean premium,
      Discount discount,
      ArrayList<Playlist> playlists,
      Playlist recent) {
    this.name = name;
    this.surname = surname;
    this.birthDate = birthDate;
    this.email = email;
    this.username = username;
    this.password = password;
    this.premium = premium;
    this.discount = discount;
    this.playlists = playlists;
    this.recent = recent;
  }

  /**
   * Create a user.
   *
   * @param name The name of the user
   * @param surname The surname of the user
   * @param birthDate The date of birth of the user
   * @param email The email of the user
   * @param username The nickname of the user
   * @param password The password of the user
   * @param premium true if the user is premium, false otherwise
   */
  public User(
      String name,
      String surname,
      String birthDate,
      String email,
      String username,
      String password,
      boolean premium) {
    // TODO null
    this(
        name,
        surname,
        birthDate,
        email,
        username,
        password,
        premium,
        null,
        new ArrayList<Playlist>(),
        new Playlist("Recent"));
  }

  // TODO consider if this is necessary
  public void addPlaylist(Playlist playlist) {
    playlists.add(playlist);
  }

  // TODO consider if this is necessary
  // public void updateRecent(Song reproduccion) {
  //     recent.add(reproduccion);
  // }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getSurname() {
    return surname;
  }

  public String getBirthDate() {
    return birthDate;
  }

  public String getEmail() {
    return email;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public boolean isPremium() {
    return premium;
  }

  public Discount getDiscount() {
    return discount;
  }

  public ArrayList<Playlist> getPlaylists() {
    return playlists;
  }

  public Playlist getRecent() {
    return recent;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public void setBirthDate(String birthDate) {
    this.birthDate = birthDate;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setPremium(boolean premium) {
    this.premium = premium;
  }

  public void setDiscount(Discount discount) {
    this.discount = discount;
  }

  public void setPlaylists(List<Playlist> playlists) {
    this.playlists = new ArrayList<Playlist>(playlists);
  }

  public void setRecent(Playlist recent) {
    this.recent = recent;
  }
}
