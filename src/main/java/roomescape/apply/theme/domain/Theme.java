package roomescape.apply.theme.domain;

public class Theme {

    private Long id;
    private String name;
    private String description;
    private String thumbnail;

    protected Theme() {

    }

    public Theme(Long id, String name, String description, String thumbnail) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
    }

    public static Theme of(String name, String description, String thumbnail) {
        Theme theme = new Theme();
        theme.name = name;
        theme.description = description;
        theme.thumbnail = thumbnail;
        return theme;
    }

    public void changeId(long id) {
        this.id = id;
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
