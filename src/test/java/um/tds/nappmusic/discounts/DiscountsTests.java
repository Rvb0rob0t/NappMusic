package um.tds.nappmusic.discounts;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.Month;
import org.junit.jupiter.api.Test;
import um.tds.nappmusic.domain.User;
import um.tds.nappmusic.domain.discounts.Below25Discount;
import um.tds.nappmusic.domain.discounts.OctoberDiscount;

public class DiscountsTests {

  @Test
  void checkSetBestDiscount() {

    User user = new User();
    user.setBirthDate(LocalDate.parse("1999-08-18"));
    user.setBestDiscount();

    Below25Discount below25Discount = new Below25Discount();
    assertTrue(below25Discount.calculatePrice() >= user.getDiscount().calculatePrice());

    OctoberDiscount octoberDiscount = new OctoberDiscount();
    if (LocalDate.now().getMonth() == Month.OCTOBER) {
      assertTrue(octoberDiscount.calculatePrice() >= user.getDiscount().calculatePrice());
    }
  }
}
