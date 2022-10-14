package nextstep.reader.application;

import nextstep.reader.domain.DateTime;

public interface DateTimeReader {

    DateTime getDateTimeByScheduleId(Long scheduleId);
}
