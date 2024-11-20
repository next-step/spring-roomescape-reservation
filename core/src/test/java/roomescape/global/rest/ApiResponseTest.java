package roomescape.global.rest;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import roomescape.global.rest.error.ErrorDetails;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ApiResponseTest {

    @Test
    void ok() {
        ApiResponse<ApiResponseData> response = ApiResponse.ok(new ApiResponseData());

        assertAll(
                () -> assertThat(response.getResponseType()).isEqualTo(HttpResponseType.SUCCESS),
                () -> assertThat(response.getStatusCode()).isEqualTo(200),
                () -> assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.OK),
                () -> assertThat(response.getData()).isNotNull()
        );
    }

    @Test
    void serverError() {
        ApiResponse<ErrorDetails> response = ApiResponse.serverError();

        assertAll(
                () -> assertThat(response.getResponseType()).isEqualTo(HttpResponseType.ERROR),
                () -> assertThat(response.getStatusCode()).isEqualTo(500),
                () -> assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR),
                () -> assertThat(response.getData()).isNotNull()
        );
    }

    @Test
    void badRequest() {
        ApiResponse<ErrorDetails> response = ApiResponse.badRequest(null);

        assertAll(
                () -> assertThat(response.getResponseType()).isEqualTo(HttpResponseType.ERROR),
                () -> assertThat(response.getStatusCode()).isEqualTo(400),
                () -> assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST)
        );
    }

    @Test
    void notFound() {
        ApiResponse<ErrorDetails> response = ApiResponse.notFound(null);

        assertAll(
                () -> assertThat(response.getResponseType()).isEqualTo(HttpResponseType.ERROR),
                () -> assertThat(response.getStatusCode()).isEqualTo(404),
                () -> assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND)
        );
    }

    private static class ApiResponseData {

    }
}