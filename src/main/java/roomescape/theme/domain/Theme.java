package roomescape.theme.domain;

import roomescape.theme.error.exception.ErrorCode;
import roomescape.theme.error.exception.ThemeException;

public class Theme {

    private static final String NAME_FORMAT = "^[A-Za-z0-9가-힣\\s]{8,20}$";
    private static final String DESCRIPTION_FORMAT = "^[A-Za-z0-9가-힣\\s]{1,200}$";
    private static final String THUMBNAIL_FORMAT = "^(https?:\\/\\/)?([a-zA-Z0-9.-]+)(:[0-9]{1,5})?(\\/[^\\s]*)?$";
    private Long id;
    private String name;
    private String description;
    private String thumbnail;

    public Theme(Long id, String name, String description, String thumbnail) {
        this.id = id;
        checkFormats(name, description, thumbnail);
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
    }

    private void checkFormats(String name, String description, String thumbnail) {
        if (!name.matches(NAME_FORMAT)) {
            throw new ThemeException(ErrorCode.INVALID_THEME_NAME_FORMAT_ERROR);
        }
        if (!description.matches(DESCRIPTION_FORMAT)) {
            throw new ThemeException(ErrorCode.INVALID_THEME_DESCRIPTION_FORMAT_ERROR);
        }
        if (!thumbnail.matches(THUMBNAIL_FORMAT)) {
            throw new ThemeException(ErrorCode.INVALID_THEME_THUMBNAIL_FORMAT_ERROR);
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getThumbnail() {
        return thumbnail;
    }
}
