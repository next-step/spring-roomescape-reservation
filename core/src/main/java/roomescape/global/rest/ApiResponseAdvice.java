package roomescape.global.rest;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Objects;

@ControllerAdvice
public class ApiResponseAdvice implements ResponseBodyAdvice<ApiResponse<?>> {

    @Override
    public boolean supports(
            final MethodParameter returnType,
            final Class<? extends HttpMessageConverter<?>> converterType
    ) {
        return returnType.getParameterType() == ApiResponse.class;
    }

    @Override
    public ApiResponse<?> beforeBodyWrite(
            final ApiResponse<?> body,
            final MethodParameter returnType,
            final MediaType selectedContentType,
            final Class<? extends HttpMessageConverter<?>> selectedConverterType,
            final ServerHttpRequest request,
            final ServerHttpResponse response
    ) {
        if (Objects.nonNull(body)) {
            response.setStatusCode(body.getHttpStatus());
        }
        return body;
    }
}
