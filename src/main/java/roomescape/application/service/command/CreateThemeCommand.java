package roomescape.application.service.command;

public class CreateThemeCommand {

    private final String name;

    private final String description;

    private final String thumbnail;

    public CreateThemeCommand(String name, String description, String thumbnail) {
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
    }
}
