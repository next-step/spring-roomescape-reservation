package nextstep.application;

import nextstep.domain.DateTime;

public interface DateTimeReader {

    DateTime getDateTimeByScheduleId(Long scheduleId);
}
