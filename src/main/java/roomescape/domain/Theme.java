package roomescape.domain;

public class Theme {

  private final Long id;
  private final String name;
  private final String description;
  private final String thumbnail;

  public Theme(Long id, String name, String description, String thumbnail) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.thumbnail = thumbnail;
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

  public static Theme of(Long id, String name, String description, String thumbnail) {
    return new Theme(id, name, description, thumbnail);
  }

  public Theme addId(Long index) {
    return new Theme(index, this.name, this.description, this.thumbnail);
  }
}
