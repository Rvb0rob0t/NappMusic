package um.tds.nappmusic.domain.discounts;

import java.time.LocalDate;
import java.time.Month;
import um.tds.nappmusic.domain.Discount;
import um.tds.nappmusic.domain.User;

public class OctoberDiscount implements Discount {
  private static double DISCOUNTED_PERCENTAGE = 0.05;

  public OctoberDiscount() {}

  @Override
  public boolean isApplicable(User user) {
    return LocalDate.now().getMonth() == Month.OCTOBER;
  }

  @Override
  public int calculatePrice() {
    return (int) Math.round((1 - DISCOUNTED_PERCENTAGE) * Discount.getBasePrice());
  }
}
