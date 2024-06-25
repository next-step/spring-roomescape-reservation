package roomescape.domain.theme;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class ThemeIsUsed extends HttpClientErrorException {

  public ThemeIsUsed() {
    super(HttpStatus.CONFLICT, "이 테마를 사용중인 예약이 존재합니다.");
  }
}
