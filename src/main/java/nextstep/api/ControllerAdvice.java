package nextstep.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> illegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
