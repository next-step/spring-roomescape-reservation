package nextstep.application.themes;

import nextstep.application.themes.dto.ThemeDeleteValidationDto;

public interface ThemeDeleteValidation {

  void checkValid(ThemeDeleteValidationDto validationDto);

  int priority();
}
