package nextstep.schedules.exception;

public class ScheduleNotFoundException extends IllegalArgumentException{

    private static final String MESSAGE = "존재하지 않는 스케쥴입니다.";

    public ScheduleNotFoundException() {
        super(MESSAGE);
    }
}
