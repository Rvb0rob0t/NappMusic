package um.tds.nappmusic.domain.discounts;

import um.tds.nappmusic.domain.Discount;
import um.tds.nappmusic.domain.User;

public class Below25Discount implements Discount {
  private static double DISCOUNTED_PERCENTAGE = 0.7;

  public Below25Discount() {}

  @Override
  public boolean isApplicable(User user) {
    // TODO
    // return user.getAge() < 25;
    return false;
  }

  @Override
  public int calculatePrice() {
    return (int) Math.round((1 - DISCOUNTED_PERCENTAGE) * Discount.getBasePrice());
  }
}
