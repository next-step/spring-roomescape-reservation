package roomescape.domain.time;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

class ReservationTimeNotFound extends HttpClientErrorException {

  public ReservationTimeNotFound() {
    super(HttpStatus.NOT_FOUND, "이 예약 시간이 존재하지 않습니다.");
  }
}
