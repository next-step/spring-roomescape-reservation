package roomescape.domain.theme;

public class Theme {
  private final long id;
  private String name;
  private String description;
  private String thumbnail;

  public Theme(long id, String name, String description, String thumbnail) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.thumbnail = thumbnail;
  }

  public Theme(long id) {
    this.id = id;
  }

  public long getId() {

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
