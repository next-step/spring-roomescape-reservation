package roomescape.common.exception;

public class PolicyException extends RuntimeException{

    public final static String DUPLICATE_RESERVATION_ERROR = "중복된 예약이 존재합니다.";
    public final static String RESERVATION_PRIOR_TO_CURRENT_TIME_ERROR = "예약 시간은 현재 시간보다 이전일 수 없습니다.";

    public final static String RESERVATION_TIME_IN_USE_RESERVATION_ERROR = "예약 목록에 사용중인 예약시간이 있습니다.";

    public final static String THEME_IN_USE_RESERVATION_ERROR = "예약 목록에 사용중인 테마가 있습니다.";

    public PolicyException(String message){
        super(message);
    }
}