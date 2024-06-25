package roomescape.domain.reservation;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class ReservationNotFound extends HttpClientErrorException {

  public ReservationNotFound() {
    super(HttpStatus.NOT_FOUND, "존재하지 않는 예약입니다.");
  }
}
