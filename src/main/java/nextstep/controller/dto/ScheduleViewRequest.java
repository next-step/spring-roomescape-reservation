package nextstep.controller.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import nextstep.domain.schedule.dto.ScheduleCommandDto;

public class ScheduleViewRequest {

  private static final String DATE_ERROR_MESSAGE = "일자는 비어있을 수 없습니다.";
  private static final String TIME_ERROR_MESSAGE = "시간은 비어있을 수 없습니다.";
  private static final String ID_ERROR_MESSAGE = "테마 ID는 비어있을 수 없습니다.";

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
