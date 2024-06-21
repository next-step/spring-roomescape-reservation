package roomescape.application.service.command;

public class DeleteThemeCommand {

    private final Long themeId;

    public DeleteThemeCommand(Long themeId) {
        this.themeId = themeId;
    }

    public Long getThemeId() {
        return themeId;
    }
}
