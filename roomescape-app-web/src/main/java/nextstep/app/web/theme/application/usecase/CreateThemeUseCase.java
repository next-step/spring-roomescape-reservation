package nextstep.app.web.theme.application.usecase;

import nextstep.app.web.theme.application.dto.CreateThemeCommand;
import nextstep.app.web.theme.application.dto.CreateThemeResult;

public interface CreateThemeUseCase {
    CreateThemeResult create(CreateThemeCommand command);
}
