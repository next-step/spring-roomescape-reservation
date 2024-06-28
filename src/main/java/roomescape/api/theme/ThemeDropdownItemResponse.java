package roomescape.api.theme;

import roomescape.domain.theme.ThemeSummary;

record ThemeDropdownItemResponse(long id, String name) {
  static ThemeDropdownItemResponse from(ThemeSummary summary) {
    return new ThemeDropdownItemResponse(summary.id(), summary.name());
  }
}
