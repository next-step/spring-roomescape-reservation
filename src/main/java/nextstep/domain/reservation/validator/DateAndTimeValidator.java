package nextstep.domain.reservation.validator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import nextstep.domain.reservation.dto.ReservationCommandDto.Create;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DateAndTimeValidator implements ReservationCreateValidator {

  @Override
  public void validate(Create createReq) {
    var now = LocalDateTime.now();

    var criteriaDate = now.toLocalDate();
    var criteriaTime = now.toLocalTime();

    var requestDate = createReq.date();
    var requestTime = createReq.time();

    if (criteriaDate.isAfter(requestDate)) {
      var message = "이미 지난 날짜에 대해 예약할 수 없습니다. 현재 날짜 : %s, 요청 날짜 : %s".formatted(requestDate, criteriaDate);
      log.debug("예약 생성 중 예외 발생 : {}", message);
      throw new IllegalArgumentException(message);
    }

    if (criteriaDate.isEqual(requestDate) && criteriaTime.isAfter(requestTime)) {
      var message = "이미 지난 시간에 대해 예약할 수 없습니다. 현재 시간 : %s, 요청 시간 : %s".formatted(requestTime, criteriaTime);
      log.debug("예약 생성 중 예외 발생 : {}", message);
      throw new IllegalArgumentException(message);
    }
  }
}
