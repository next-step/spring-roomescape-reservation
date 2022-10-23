package nextstep.domain.theme.validator;

import nextstep.domain.theme.dto.ThemeCommandDto;

public interface ThemeDeleteValidator {

  default void validate(ThemeCommandDto.Delete deleteReq) {
    throw new UnsupportedOperationException();
  }
}
