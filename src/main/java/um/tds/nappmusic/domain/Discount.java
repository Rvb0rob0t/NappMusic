package um.tds.nappmusic.domain;


public interface Discount {
  public boolean isApplicable(User user);

  public int calculatePrice();

  // TODO :(
  public static int getBasePrice() {
    return 1899; // 18.99
  }
}
