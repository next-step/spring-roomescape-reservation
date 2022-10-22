package nextstep.domain.schedule.exception;

public class NotFoundScheduleException extends RuntimeException {
    public NotFoundScheduleException() {
        super("스케쥴 정보를 찾을 수 없습니다.");
    }
}
