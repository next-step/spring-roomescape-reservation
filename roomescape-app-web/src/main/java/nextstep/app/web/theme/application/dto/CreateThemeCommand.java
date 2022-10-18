package nextstep.app.web.theme.application.dto;

public record CreateThemeCommand(String name,
                                 String desc,
                                 Long price) {
}