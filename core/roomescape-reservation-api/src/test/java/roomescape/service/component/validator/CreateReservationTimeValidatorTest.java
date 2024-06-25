package roomescape.service.component.validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import roomescape.application.error.exception.CreateReservationTimeValidateException;
import roomescape.application.service.component.validator.CreateReservationTimeValidator;
import roomescape.domain.reservationtime.ReservationTime;
import roomescape.domain.reservationtime.vo.ReservationTimeId;
import roomescape.domain.reservationtime.vo.ReservationTimeStartAt;
import roomescape.repository.ReservationTimeRepository;
import roomescape.repository.entity.ReservationTimeEntity;

import java.time.LocalTime;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql({"classpath:db/schema/schema.sql", "classpath:db/data/init.sql"})
class CreateReservationTimeValidatorTest {

    @Autowired
    private ReservationTimeRepository reservationTimeRepository;

    @Test
    @DisplayName("이미 생성한 시작 시간의 예약 시간은 생성할 수 없다.")
    void CannotCreateExistStartAt() {
        reservationTimeRepository.save(
                new ReservationTimeEntity(1L, LocalTime.of(12, 55))
        );

        CreateReservationTimeValidator validator = new CreateReservationTimeValidator(reservationTimeRepository);
        Assertions.assertThrows(CreateReservationTimeValidateException.class,
                () -> validator.validate(
                        new ReservationTime(
                                new ReservationTimeId(1L),
                                new ReservationTimeStartAt(LocalTime.of(12, 55))
                        )));
    }
}
