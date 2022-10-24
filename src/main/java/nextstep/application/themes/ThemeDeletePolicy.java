package nextstep.application.themes;

import java.util.List;
import lombok.RequiredArgsConstructor;
import nextstep.application.themes.dto.ThemeDeleteValidationDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ThemeDeletePolicy {

  private final List<ThemeDeleteValidation> validations;

  public void checkValid(ThemeDeleteValidationDto validationDto) {
    validations.forEach(it -> it.checkValid(validationDto));
  }
}
