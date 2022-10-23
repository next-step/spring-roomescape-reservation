package nextstep.controller.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import nextstep.domain.schedule.dto.ScheduleCommandDto;
import nextstep.domain.schedule.dto.ScheduleFindCondition;
import org.springframework.format.annotation.DateTimeFormat;

public class ScheduleViewRequest {

  private static final String DATE_ERROR_MESSAGE = "일자는 비어있을 수 없습니다.";
  private static final String TIME_ERROR_MESSAGE = "시간은 비어있을 수 없습니다.";
  private static final String ID_ERROR_MESSAGE = "테마 ID는 비어있을 수 없습니다.";

  public record FindCondition(
      @NotNull(message = ID_ERROR_MESSAGE)
      Long themeId,
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

    public ScheduleFindCondition toDomainCondition() {
      return ScheduleFindCondition.builder()
          .themeId(themeId)
          .date(date)
          .time(time)
          .build();
    }
  }

  public record Create(
      @NotNull(message = ID_ERROR_MESSAGE)
      Long themeId,
      @NotNull(message = DATE_ERROR_MESSAGE)
      LocalDate date,
      @NotNull(message = TIME_ERROR_MESSAGE)
      LocalTime time
  ) {

    @Builder(toBuilder = true)
    public Create {
    }

    public ScheduleCommandDto.Create toDomainCommand() {
      return ScheduleCommandDto.Create.builder()
          .themeId(themeId)
          .date(date)
          .time(time)
          .build();
    }
  }

}
