package roomescape.controller.dto;

import roomescape.exception.ErrorCode;
import roomescape.exception.RoomEscapeException;

import org.springframework.util.ObjectUtils;

public record ThemeRequest(String name, String description, String thumbnail) {

	public static void validateThemeRequest(ThemeRequest request) {
		if (ObjectUtils.isEmpty(request.name())) {
			throw new RoomEscapeException(ErrorCode.INVALID_THEME_NAME);
		}

		if (ObjectUtils.isEmpty(request.description())) {
			throw new RoomEscapeException(ErrorCode.INVALID_THEME_DESCRIPTION);
		}

		if (ObjectUtils.isEmpty(request.thumbnail())) {
			throw new RoomEscapeException(ErrorCode.INVALID_THEME_THUMBNAIL);
		}
	}

}
