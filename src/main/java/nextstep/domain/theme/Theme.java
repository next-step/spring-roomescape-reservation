package nextstep.domain.theme;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.extern.jackson.Jacksonized;
import nextstep.exception.ThemeIllegalArgumentException;

@Getter
@EqualsAndHashCode
@ToString
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class Theme {

  Long id;
  Name name;
  Price price;
  Description description;

  private Theme(Long id, Name name, Price price, Description description) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.description = description;
  }

  @Jacksonized
  @Builder
  public Theme(Long id, String name, BigDecimal price, String description) {
    this.id = id;
    this.name = Name.builder().value(name).build();
    this.price = Price.builder().value(price).build();
    this.description = Description.builder().value(description).build();
  }

  public record Name(String value) {

    private static final int MAX_LENGTH = 1000;

    public static void validateNameRule(@NotNull String description) {
      if (description.length() > MAX_LENGTH) {
        throw new ThemeIllegalArgumentException(
            "테마 설명이 너무 깁니다. 테마 설명 최대 길이 : %d, 입력된 글자 길이 : %d".formatted(MAX_LENGTH, description.length()));
      }
    }

    @Builder
    public Name {
      validateNameRule(value);
    }
  }

  public record Price(BigDecimal value) {

    public static void validatePriceRule(@NotNull BigDecimal price) {
      if (lessThanZero(price)) {
        throw new ThemeIllegalArgumentException(
            "테마 가격은 0보다 작을 수 없습니다. 입력된 금액 : %d".formatted(price.longValue()));
      }
    }

    private static boolean lessThanZero(BigDecimal price) {
      return price.compareTo(BigDecimal.ZERO) < 0;
    }

    @Builder
    public Price {
      validatePriceRule(value);
    }
  }

  public record Description(String value) {

    private static final int MAX_LENGTH = 1000;

    public static void validateDescriptionRule(@NotNull String description) {
      if (description.length() > MAX_LENGTH) {
        throw new ThemeIllegalArgumentException(
            "테마 설명이 너무 깁니다. 테마 설명 최대 길이 : %d, 입력된 글자 길이 : %d".formatted(MAX_LENGTH, description.length()));
      }
    }

    @Builder
    public Description {
      validateDescriptionRule(value);
    }
  }
}
