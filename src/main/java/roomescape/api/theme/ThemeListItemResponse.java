package roomescape.api.theme;

import roomescape.domain.theme.Theme;

record ThemeListItemResponse(long id, String name, String description, String thumbnail) {

  static ThemeListItemResponse from(Theme theme) {
    return new ThemeListItemResponse(theme.getId(), theme.getName(), theme.getDescription(),
        theme.getThumbnail());
  }
}
