package roomescape.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getBindingResult().getFieldError().getDefaultMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(ReservationTimeInvalidException.class)
    public ResponseEntity<ErrorResponse> handleReservationTimeInvalidException(ReservationTimeInvalidException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(ReservationDateInvalidException.class)
    public ResponseEntity<ErrorResponse> handleReservationDateInvalidException(ReservationDateInvalidException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(ReservationNameInvalidException.class)
    public ResponseEntity<ErrorResponse> handleReservationNameInvalidException(ReservationNameInvalidException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(ReservationExistException.class)
    public ResponseEntity<ErrorResponse> handleReservationExistException(ReservationExistException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(ReservationDatePastException.class)
    public ResponseEntity<ErrorResponse> handleReservationDatePastException(ReservationDatePastException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(ReservationDuplicateException.class)
    public ResponseEntity<ErrorResponse> handleReservationDuplicateException(ReservationDuplicateException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }
}
