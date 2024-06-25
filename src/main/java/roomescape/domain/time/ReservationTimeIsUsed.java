package roomescape.domain.time;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

class ReservationTimeIsUsed extends HttpClientErrorException {

  public ReservationTimeIsUsed() {
    super(HttpStatus.CONFLICT, "이 시간대를 사용중인 예약이 존재합니다.");
  }
}
