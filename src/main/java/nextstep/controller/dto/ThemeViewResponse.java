package nextstep.controller.dto;

import java.math.BigDecimal;

public class ThemeViewResponse {

  public record Theme(
      Long id,
      String name,
      BigDecimal price,
      String desc
  ) {

    public static Theme of(nextstep.domain.theme.Theme domain) {
      return new Theme(domain.getId(), domain.getName().value(), domain.getPrice().value(), domain.getDescription().value());
    }
  }
}
