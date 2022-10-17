package nextstep;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class CurrentDateTimeService {

    public LocalDate nowDate() {
        return LocalDate.now();
    }

    public LocalTime nowTime() {
        return LocalTime.now();
    }
}
