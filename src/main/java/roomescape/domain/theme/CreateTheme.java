package roomescape.domain.theme;

public record CreateTheme(String name, String description, String thumbnail) {
  public Theme toDomain(long id) {
    return new Theme(id, name, description, thumbnail);
  }
}
