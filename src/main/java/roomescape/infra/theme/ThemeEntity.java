package roomescape.infra.theme;

import roomescape.domain.theme.Theme;

public class ThemeEntity {
  private final long id;
  private String name;
  private String description;
  private String thumbnail;

  public ThemeEntity(long id, String name, String description, String thumbnail) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.thumbnail = thumbnail;
  }

  public Theme toDomain() {
    return new Theme(id, name, description, thumbnail);
  }

  public ThemeEntity(long id) {
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
