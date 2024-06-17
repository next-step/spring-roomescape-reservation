package roomescape.reservationTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@JdbcTest
class ReservationTimeRepositoryTest {

    ReservationTimePolicy reservationTimePolicy = new ReservationTimePolicy();

    @Autowired
    JdbcTemplate jdbcTemplate;

    private ReservationTimeRepository reservationTimeRepository;

    @BeforeEach
    void setUp() {

        reservationTimeRepository = new ReservationTimeRepository(jdbcTemplate);

        jdbcTemplate.execute("DROP TABLE IF EXISTS reservation");
        jdbcTemplate.execute("DROP TABLE IF EXISTS reservation_time");

        jdbcTemplate.execute("CREATE TABLE reservation_time (" +
                "id BIGINT NOT NULL AUTO_INCREMENT, " +
                "start_at VARCHAR(255) NOT NULL, " +
                "PRIMARY KEY (id))");

        jdbcTemplate.execute("CREATE TABLE reservation (" +
                "id BIGINT NOT NULL AUTO_INCREMENT, " +
                "name VARCHAR(255) NOT NULL, " +
                "date VARCHAR(255) NOT NULL, " +
                "time_id BIGINT, " +   // 컬럼 수정
                "PRIMARY KEY (id))");  // 외래키 제거
    }

    @DisplayName("예약 시간을 등록할 수 있습니다")
    @Test
    void creatTime(){
        // given
        final ReservationTime request = new ReservationTime("15:40");

        // when
        final Long id = reservationTimeRepository.save(request);

        // then
        final ReservationTime actual = reservationTimeRepository.findById(id);
        assertThat(actual.getStartAt()).isEqualTo(request.getStartAt());
    }

    @DisplayName("예약 시간을 삭제할 수 있습니다")
    @Test
    void deleteTime(){
        // given
        final ReservationTime request = new ReservationTime("15:40");
        final Long id = reservationTimeRepository.save(request);

        // when
        reservationTimeRepository.deleteById(id);

        // then
        assertThat(reservationTimeRepository.existsById(id)).isFalse();
    }

    @DisplayName("예약 시간을 조회할 수 있습니다")
    @Test
    void findAll(){
        // given
        final ReservationTime reservationTime1 = new ReservationTime("15:40");
        final ReservationTime reservationTime2 = new ReservationTime("16:40");
        final Long id1 = reservationTimeRepository.save(reservationTime1);
        final Long id2 = reservationTimeRepository.save(reservationTime2);

        // when
        final List<ReservationTime> actual = reservationTimeRepository.findAll();

        // then
        assertThat(actual).hasSize(2);
        assertThat(actual.get(0).getId()).isEqualTo(id1);
        assertThat(actual.get(0).getStartAt()).isEqualTo(reservationTime1.getStartAt());
        assertThat(actual.get(1).getId()).isEqualTo(id2);
        assertThat(actual.get(1).getStartAt()).isEqualTo(reservationTime2.getStartAt());
    }

    @DisplayName("예약 시간을 조회할 수 있습니다")
    @Test
    void findById(){
        // given
        final ReservationTime reservationTime = new ReservationTime("15:40");
        final Long id = reservationTimeRepository.save(reservationTime);

        // when
        final ReservationTime actual = reservationTimeRepository.findById(id);

        // then
        assertThat(actual.getId()).isEqualTo(id);
        assertThat(actual.getStartAt()).isEqualTo(reservationTime.getStartAt());
    }
}
