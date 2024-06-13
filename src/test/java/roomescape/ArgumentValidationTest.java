package roomescape;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArgumentValidationTest {

    private final Validator validator;

    public ArgumentValidationTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Nested
    @DisplayName("@NotNull는")
    class Describe_Not_Null {

        @Test
        @DisplayName("null일 경우, 에러를 리턴합니다.")
        void it_return_error_for_string_null() {
            request req = new request(null, "Zerg", "Protos");
            Set<ConstraintViolation<request>> violations = validator.validate(req);

            assertEquals(1, violations.size());
            assertEquals("null이 아니며, 빈값은 허용합니다.", violations.iterator().next().getMessage());
        }

        @Test
        @DisplayName("null이 아닌 경우, 에러를 리턴하지 않습니다.")
        void it_does_not_return_error_for_non_null() {
            request req = new request("Terran", "Zerg", "Protos");
            Set<ConstraintViolation<request>> violations = validator.validate(req);

            assertEquals(0, violations.size());
        }
    }

    @Nested
    @DisplayName("@NotEmpty는")
    class Describe_Not_Empty {

        @Test
        @DisplayName("빈 문자열일 경우, 에러를 리턴합니다.")
        void it_return_error_for_empty_string() {
            request req = new request("Terran", "", "Protos");
            Set<ConstraintViolation<request>> violations = validator.validate(req);

            assertEquals(1, violations.size());
            assertEquals("null이 아니며, 길이나 크기가 0보다 커야 함을 확인합니다.", violations.iterator().next().getMessage());
        }

        @Test
        @DisplayName("null일 경우, 에러를 리턴합니다.")
        void it_return_error_for_null_string() {
            request req = new request("Terran", null, "1");
            Set<ConstraintViolation<request>> violations = validator.validate(req);

            assertEquals(1, violations.size());
            assertEquals("null이 아니며, 길이나 크기가 0보다 커야 함을 확인합니다.", violations.iterator().next().getMessage());
        }

        @Test
        @DisplayName("빈 값이 아닌 경우, 에러를 리턴하지 않습니다.")
        void it_does_not_return_error_for_non_empty() {
            request req = new request("Terran", "Zerg", "Protos");
            Set<ConstraintViolation<request>> violations = validator.validate(req);

            assertEquals(0, violations.size());
        }
    }

    @Nested
    @DisplayName("@NotBlank는")
    class Describe_Not_Blank {

        @Test
        @DisplayName("빈 문자열일 경우, 에러를 리턴합니다.")
        void it_return_error_for_blank_string() {
            request req = new request("Terran", "Zerg", "");
            Set<ConstraintViolation<request>> violations = validator.validate(req);

            assertEquals(1, violations.size());
            assertEquals("null이 아니고 공백을 제거한 길이가 0보다 커야 함을 확인합니다.", violations.iterator().next().getMessage());
        }

        @Test
        @DisplayName("공백만 있는 문자열일 경우, 에러를 리턴합니다.")
        void it_return_error_for_blank_spaces() {
            request req = new request("Terran", "Zerg", "   ");
            Set<ConstraintViolation<request>> violations = validator.validate(req);

            assertEquals(1, violations.size());
            assertEquals("null이 아니고 공백을 제거한 길이가 0보다 커야 함을 확인합니다.", violations.iterator().next().getMessage());
        }

        @Test
        @DisplayName("null일 경우, 에러를 리턴합니다.")
        void it_return_error_for_null_string() {
            request req = new request("Terran", "Zerg", null);
            Set<ConstraintViolation<request>> violations = validator.validate(req);

            assertEquals(1, violations.size());
            assertEquals("null이 아니고 공백을 제거한 길이가 0보다 커야 함을 확인합니다.", violations.iterator().next().getMessage());
        }

        @Test
        @DisplayName("공백이 아닌 경우, 에러를 리턴하지 않습니다.")
        void it_does_not_return_error_for_non_blank() {
            request req = new request("Terran", "Zerg", "Protos");
            Set<ConstraintViolation<request>> violations = validator.validate(req);

            assertEquals(0, violations.size());
        }
    }

    record request(
            @NotNull(message = "null이 아니며, 빈값은 허용합니다.") String terran,
            @NotEmpty(message = "null이 아니며, 길이나 크기가 0보다 커야 함을 확인합니다.") String zerg,
            @NotBlank(message = "null이 아니고 공백을 제거한 길이가 0보다 커야 함을 확인합니다.") String protos) {
    }
}