package nextstep.app.web.theme.port.web;

import nextstep.app.web.theme.application.dto.CreateThemeCommand;

public record ThemeCreateRequest(String name,
                                 String desc,
                                 Long price) {
    public CreateThemeCommand toCommand() {
        return new CreateThemeCommand(name, desc, price);
    }
}
