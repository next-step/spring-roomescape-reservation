package roomescape.repository.mysql;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import roomescape.repository.config.MySQLJdbcReservationTimeRepositoryConfig;
import roomescape.repository.entity.ReservationTimeEntity;

import java.time.LocalTime;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(MySQLJdbcReservationTimeRepositoryConfig.class)
@Sql({"classpath:db/schema/schema.sql", "classpath:db/data/init.sql"})
class MySQLJdbcReservationTimeRepositoryTest {

    @Autowired
    private MySQLJdbcReservationTimeRepository mySQLJdbcReservationTimeRepository;

    @Nested
    @DisplayName("save 함수를 수행할 때")
    class InSaveFunction {
        @Nested
        @DisplayName("id가 존재하는 엔티티라면")
        class hasIdEntity {

            @BeforeEach
            void setUp() {
                mySQLJdbcReservationTimeRepository.save(new ReservationTimeEntity(1L, LocalTime.of(19, 44)));
            }

            @Test
            @DisplayName("업데이트 한다.")
            void updateEntity() {
                ReservationTimeEntity expected = new ReservationTimeEntity(1L, LocalTime.of(19, 45));

                mySQLJdbcReservationTimeRepository.save(expected);
                ReservationTimeEntity actual = mySQLJdbcReservationTimeRepository.findById(1L)
                        .orElseThrow(() -> new IllegalStateException("엔티티를 찾지 못했습니다."));

                assertSoftly(softly -> softly.assertThat(actual.getStartAt()).isEqualTo(LocalTime.of(19, 45)));
            }
        }
    }
}
