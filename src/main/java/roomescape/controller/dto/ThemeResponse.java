package roomescape.controller.dto;

import roomescape.domain.Theme;

public record ThemeResponse(long id, String name, String description, String thumbnail) {

	public static ThemeResponse from(Theme theme) {
		return new ThemeResponse(theme.getId(), theme.getName(), theme.getDescription(), theme.getThumbnail());
	}

}
