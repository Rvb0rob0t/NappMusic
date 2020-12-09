package um.tds.nappmusic.domain.discounts;

import java.time.LocalDate;
import java.time.Period;
import um.tds.nappmusic.app.App;
import um.tds.nappmusic.domain.Discount;
import um.tds.nappmusic.domain.User;

public class Below25Discount implements Discount {
  private static double DISCOUNTED_PERCENTAGE = 0.7;

  public Below25Discount() {}

  @Override
  public boolean isApplicable(User user) {
    return Period.between(user.getBirthDate(), LocalDate.now()).getYears() < 25;
  }

  @Override
  public int calculatePrice() {
    return (int) Math.round((1 - DISCOUNTED_PERCENTAGE) * App.BASE_PRICE);
  }
}
