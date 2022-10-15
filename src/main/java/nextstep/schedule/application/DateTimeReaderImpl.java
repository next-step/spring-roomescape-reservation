package nextstep.schedule.application;

import nextstep.reader.application.DateTimeReader;
import nextstep.reader.domain.DateTime;
import nextstep.schedule.domain.Schedule;
import nextstep.schedule.domain.ScheduleRepository;
import org.springframework.stereotype.Service;

@Service
public class DateTimeReaderImpl implements DateTimeReader {

    private final ScheduleRepository scheduleRepository;

    public DateTimeReaderImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public DateTime getDateTimeByScheduleId(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId);
        return new DateTime(
            schedule.getDate(),
            schedule.getTime()
        );
    }
}
