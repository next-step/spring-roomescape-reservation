package roomescape.common.exception;

import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

public record ErrorResponse(String fieldName,
                            String errorMessage) {

    private ErrorResponse(ObjectError error){
        this(((FieldError) error).getField(), error.getDefaultMessage());
    }

    private ErrorResponse(PolicyException cs){
        this("", cs.getMessage());
    }
    public static ErrorResponse from(ObjectError error){
        return new ErrorResponse(error);
    }
    public static ErrorResponse from(PolicyException pe){return new ErrorResponse(pe);}

}
