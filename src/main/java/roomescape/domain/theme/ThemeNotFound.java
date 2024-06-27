package roomescape.domain.theme;

public class ThemeNotFound extends RuntimeException {

  public ThemeNotFound(long id) {
    super(id + "번 테마는 존재하지 않습니다.");
  }
}
