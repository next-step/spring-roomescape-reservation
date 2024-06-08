package roomescape.validator;

import java.lang.reflect.Field;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import roomescape.annotation.DateCheck;
import roomescape.annotation.TimeCheck;

@Aspect
@Component
public class DateTimeValidateAop {

  @Before("execution(* roomescape.adapter.in.web..*.*(..))")
  public void validateFields(JoinPoint joinPoint) throws IllegalArgumentException, IllegalAccessException {
    Object[] args = joinPoint.getArgs();

    for (Object arg : args) {
      if (arg != null) {
        validate(arg);
      }
    }
  }

  private void validate(Object obj) throws IllegalArgumentException, IllegalAccessException {
    validateDate(obj);
    validateTime(obj);
  }

  private void validateDate(Object obj) throws IllegalArgumentException, IllegalAccessException {
    Field[] fields = obj.getClass()
                        .getDeclaredFields();

    for (Field field : fields) {
      if (field.isAnnotationPresent(DateCheck.class)) {
        String value = getFieldValue(obj, field);
        if (isInvalidFormat(value, "yyyy-MM-dd")) {
          DateCheck dateCheck = field.getAnnotation(DateCheck.class);
          throw new IllegalArgumentException(dateCheck.message());
        }
      }
    }
  }

  private void validateTime(Object obj) throws IllegalArgumentException, IllegalAccessException {
    Field[] fields = obj.getClass()
                        .getDeclaredFields();

    for (Field field : fields) {
      if (field.isAnnotationPresent(TimeCheck.class)) {
        String value = getFieldValue(obj, field);
        if (isInvalidFormat(value, "HH:mm")) {
          TimeCheck timeCheck = field.getAnnotation(TimeCheck.class);
          throw new IllegalArgumentException(timeCheck.message());
        }
      }
    }
  }

  private String getFieldValue(Object obj, Field field) throws IllegalAccessException {
    boolean accessible = field.canAccess(obj);
    if (!accessible) {
      field.setAccessible(true);
    }
    String value = (String) field.get(obj);
    if (!accessible) {
      field.setAccessible(false);
    }
    return value;
  }

  private boolean isInvalidFormat(String value, String pattern) {
    if (value == null || value.isEmpty()) {
      return true;
    }

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
    try {
      formatter.parse(value);
      return false;
    } catch (DateTimeParseException e) {
      return true;
    }
  }
}