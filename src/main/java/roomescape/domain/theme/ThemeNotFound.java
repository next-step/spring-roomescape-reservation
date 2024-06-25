package roomescape.domain.theme;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class ThemeNotFound extends HttpClientErrorException {

  public ThemeNotFound() {
    super(HttpStatus.NOT_FOUND, "존재하지 않는 테마입니다.");
  }
}
