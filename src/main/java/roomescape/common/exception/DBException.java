package roomescape.common.exception;

public class DBException extends RuntimeException{
    public static final String SEARCH_ERROR = "조회 중 에러가 발생했습니다.";
    public DBException(String message){
        super(message);
    }
}