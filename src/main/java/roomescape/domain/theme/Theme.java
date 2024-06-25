package roomescape.domain.theme;

public class Theme {
  private final long id;
  private final String name;
  private final String description;
  private final String thumbnail;

  public Theme(long id, String name, String description, String thumbnail) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.thumbnail = thumbnail;
  }
}
