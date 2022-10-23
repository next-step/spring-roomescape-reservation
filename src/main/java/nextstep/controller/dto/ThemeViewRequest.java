package nextstep.controller.dto;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import nextstep.domain.theme.dto.ThemeCommandDto;

public class ThemeViewRequest {

  private static final String NAME_ERROR_MESSAGE = "이름은 비어있을 수 없습니다.";
  private static final String PRICE_ERROR_MESSAGE = "가격은 비어있을 수 없습니다.";
  private static final String DESC_ERROR_MESSAGE = "설명은 비어있을 수 없습니다.";
  private static final String ID_ERROR_MESSAGE = "ID는 비어있을 수 없습니다.";

  public record Create(
      @NotNull(message = NAME_ERROR_MESSAGE)
      String name,
      @NotNull(message = PRICE_ERROR_MESSAGE)
      BigDecimal price,
      @NotNull(message = DESC_ERROR_MESSAGE)
      String desc
  ) {

    @Builder(toBuilder = true)
    public Create {
    }

    public ThemeCommandDto.Create toDomainCommand() {
      return ThemeCommandDto.Create.builder()
          .name(name)
          .price(price)
          .desc(desc)
          .build();
    }
  }

}
