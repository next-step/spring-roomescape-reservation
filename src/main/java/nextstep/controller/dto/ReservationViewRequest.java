package nextstep.controller.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import nextstep.domain.reservation.dto.ReservationCommandDto;
import nextstep.domain.reservation.dto.ReservationFindCondition;
import org.springframework.format.annotation.DateTimeFormat;

public class ReservationViewRequest {

  private static final String DATE_ERROR_MESSAGE = "일자는 비어있을 수 없습니다.";
  private static final String TIME_ERROR_MESSAGE = "시간은 비어있을 수 없습니다.";
  private static final String NAME_ERROR_MESSAGE = "이름은 비어있을 수 없습니다.";

  public record FindCondition(
      @NotNull(message = DATE_ERROR_MESSAGE)
      @DateTimeFormat(pattern = "yyyy-MM-dd")
      LocalDate date,
      @NotNull(message = TIME_ERROR_MESSAGE)
      @DateTimeFormat(pattern = "HH:mm")
      LocalTime time
  ) {

    @Builder(toBuilder = true)
    @Jacksonized
    public FindCondition {
    }

    public ReservationFindCondition toDomainCondition() {
      return ReservationFindCondition.builder()
          .date(date)
          .time(time)
          .build();
    }
  }

  public record Create(
      @NotNull(message = DATE_ERROR_MESSAGE)
      LocalDate date,
      @NotNull(message = TIME_ERROR_MESSAGE)
      LocalTime time,
      @NotBlank(message = NAME_ERROR_MESSAGE)
      String name
  ) {

    @Builder(toBuilder = true)
    public Create {
    }

    public ReservationCommandDto.Create toDomainCommand() {
      return new ReservationCommandDto.Create(date, time, name);
    }
  }

  public record Delete(
      @NotNull(message = DATE_ERROR_MESSAGE)
      LocalDate date,
      @NotNull(message = TIME_ERROR_MESSAGE)
      LocalTime time
  ) {

    @Builder(toBuilder = true)
    @Jacksonized
    public Delete {
    }

    public ReservationCommandDto.Delete toDomainCommand() {
      return new ReservationCommandDto.Delete(date, time);
    }
  }
}
