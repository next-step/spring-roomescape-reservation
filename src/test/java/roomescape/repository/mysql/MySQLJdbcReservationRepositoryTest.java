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
import roomescape.repository.config.MySQLJdbcReservationRepositoryConfig;
import roomescape.repository.entity.ReservationEntity;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(MySQLJdbcReservationRepositoryConfig.class)
@Sql({"classpath:db/schema/schema.sql", "classpath:db/data/init.sql"})
class MySQLJdbcReservationRepositoryTest {

    @Autowired
    private MySQLJdbcReservationRepository mySQLJdbcReservationRepository;

    @Nested
    @DisplayName("save 함수를 수행할 때")
    class InSaveFunction {

        @Nested
        @DisplayName("id가 존재하는 엔티티라면")
        class hasIdEntity {

            @BeforeEach
            void setUp() {
                mySQLJdbcReservationRepository.save(
                        new ReservationEntity(
                                1L,
                                "kilian",
                                LocalDate.of(2024, 6, 8),
                                1L
                        ));
            }

            @Test
            @DisplayName("업데이트 한다.")
            void updateEntity() {
                ReservationEntity expected = new ReservationEntity(
                        1L,
                        "brie",
                        LocalDate.of(2024, 6, 8),
                        1L
                );

                mySQLJdbcReservationRepository.save(expected);
                ReservationEntity actual = mySQLJdbcReservationRepository.findById(1L)
                        .orElseThrow(() -> new IllegalStateException("엔티티를 찾을 수 없습니다."));

                assertSoftly(softly -> softly.assertThat(actual.getReservationName()).isEqualTo("brie")
                );
            }
        }

        @Nested
        @DisplayName("id가 존재하지 않는 엔티티라면")
        class NotContainEntity {

            @Test
            @DisplayName("저장 한다.")
            void saveEntity() {
                ReservationEntity expected = new ReservationEntity(
                        1L,
                        "kilian",
                        LocalDate.of(2024, 6, 8),
                        1L
                );

                mySQLJdbcReservationRepository.save(expected);

                ReservationEntity actual = mySQLJdbcReservationRepository.findById(1L)
                        .orElseThrow(() -> new IllegalStateException("엔티티를 찾을 수 없습니다."));
                List<ReservationEntity> all = mySQLJdbcReservationRepository.findAll();

                assertSoftly(softly -> {
                            softly.assertThat(actual.getReservationName()).isEqualTo("kilian");
                            softly.assertThat(all.size()).isEqualTo(1);
                        }
                );
            }
        }
    }


    @Nested
    @DisplayName("delete 함수를 수행할 때")
    class InDeleteFunction {
        @Nested
        @DisplayName("id가 존재한다면 ")
        class hasIdEntity {

            @Test
            @DisplayName("삭제 한다.")
            void delete() {
                mySQLJdbcReservationRepository.save(
                        new ReservationEntity(
                                1L,
                                "kilian",
                                LocalDate.of(2024, 6, 8),
                                1L
                        ));
                List<ReservationEntity> savedEntities = mySQLJdbcReservationRepository.findAll();

                mySQLJdbcReservationRepository.delete(1L);
                List<ReservationEntity> deleteEntities = mySQLJdbcReservationRepository.findAll();

                assertSoftly(softly -> {
                            softly.assertThat(savedEntities.size()).isEqualTo(1);
                            softly.assertThat(deleteEntities.size()).isEqualTo(0);
                        }
                );
            }
        }
    }
}
