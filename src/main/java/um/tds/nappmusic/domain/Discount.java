package um.tds.nappmusic.domain;

public interface Discount {
  public static final String DISCOUNTS_PACKAGE = "um.tds.nappmusic.domain.discounts";

  public boolean isApplicable(User user);

  public int calculatePrice();
}
