package um.tds.nappmusic.domain.discounts;

import um.tds.nappmusic.app.App;
import um.tds.nappmusic.domain.Discount;
import um.tds.nappmusic.domain.User;

public class NoDiscount implements Discount {

  @Override
  public boolean isApplicable(User user) {
    return true;
  }

  @Override
  public int calculatePrice() {
    return App.BASE_PRICE;
  }
}
