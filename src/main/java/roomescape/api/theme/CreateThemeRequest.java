package roomescape.api.theme;

import jakarta.validation.constraints.NotBlank;
import roomescape.domain.theme.CreateTheme;

record CreateThemeRequest(@NotBlank String name, @NotBlank String description,
                          @NotBlank String thumbnail) {

  CreateTheme toDomain() {
    return new CreateTheme(name, description, thumbnail);
  }
}
