package nextstep.domain.theme.dto;

import static nextstep.domain.theme.Theme.Description.validateDescriptionRule;
import static nextstep.domain.theme.Theme.Name.validateNameRule;
import static nextstep.domain.theme.Theme.Price.validatePriceRule;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

public class ThemeCommandDto {

  public record Create(
      String name,
      BigDecimal price,
      String desc
  ) {

    @Jacksonized
    @Builder(toBuilder = true)
    public Create {
      validateNameRule(name);
      validatePriceRule(price);
      validateDescriptionRule(desc);
    }
  }

  public record Delete(
      Long id
  ) {

    @Jacksonized
    @Builder(toBuilder = true)
    public Delete {
    }
  }
}
