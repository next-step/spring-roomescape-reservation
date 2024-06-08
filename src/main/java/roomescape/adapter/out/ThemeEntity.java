package roomescape.adapter.out;

public class ThemeEntity {

  private final Long id;
  private final String name;
  private final String description;
  private final String thumbnail;

  public ThemeEntity(Long id, String name, String description, String thumbnail) {
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
}
