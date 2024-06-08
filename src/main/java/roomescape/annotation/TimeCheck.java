package roomescape.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TimeCheck {

  String message() default "잘 못된 Time 형식입니다. HH:mm 형식이어야 합니다.";
}
