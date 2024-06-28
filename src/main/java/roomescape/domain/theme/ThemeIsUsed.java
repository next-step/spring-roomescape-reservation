package roomescape.domain.theme;

public class ThemeIsUsed extends RuntimeException{

  public ThemeIsUsed(long id) {
    super(id+"번 테마를 사용중인 예약이 존재합니다.");
  }
}
