package um.tds.nappmusic.domain.discounts;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import um.tds.nappmusic.domain.Discount;
import um.tds.nappmusic.domain.User;

public class Below25Discount implements Discount {
  private static double DISCOUNTED_PERCENTAGE = 0.7;

  public Below25Discount() {}

  @Override
  public boolean isApplicable(User user) {
    // TODO user birthDate should be of type LocalDate
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    LocalDate birthDate = LocalDate.parse(user.getBirthDate(), formatter);
    return Period.between(birthDate, LocalDate.now()).getYears() < 25;
  }

  @Override
  public int calculatePrice() {
    return (int) Math.round((1 - DISCOUNTED_PERCENTAGE) * Discount.getBasePrice());
  }
}
