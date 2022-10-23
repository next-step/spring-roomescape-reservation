package nextstep.domain.theme.validator;

import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import nextstep.domain.schedule.Schedule;
import nextstep.domain.schedule.ScheduleRepository;
import nextstep.domain.schedule.dto.ScheduleFindCondition;
import nextstep.domain.theme.dto.ThemeCommandDto;
import nextstep.exception.ReservationIllegalArgumentException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ScheduledThemeValidator implements
    ThemeDeleteValidator {

  ScheduleRepository scheduleRepository;

  @Override
  public void validate(ThemeCommandDto.Delete deleteReq) {
    List<Schedule> schedules = scheduleRepository.findAll(ScheduleFindCondition.builder()
        .themeId(deleteReq.id())
        .build());

    if (!schedules.isEmpty()) {
      throw new ReservationIllegalArgumentException("스케줄링 되어있는 테마입니다. 테마 ID : %d".formatted(deleteReq.id()));
    }
  }
}
