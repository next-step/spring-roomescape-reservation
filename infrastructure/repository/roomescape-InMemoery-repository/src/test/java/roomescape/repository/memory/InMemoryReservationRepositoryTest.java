package roomescape.repository.memory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import roomescape.repository.entity.ReservationEntity;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

class InMemoryReservationRepositoryTest {

    @Nested
    @DisplayName("save 함수를 수행할 때")
    class InSaveFunction {

        @Nested
        @DisplayName("id가 존재하는 엔티티라면")
        class hasIdEntity {

            private final InMemoryReservationRepository inMemoryReservationRepository = new InMemoryReservationRepository();

            @BeforeEach
            void setUp() {
                inMemoryReservationRepository.save(new ReservationEntity(
                        1L,
                        "kilian",
                        LocalDate.of(2024, 6, 8),
                        1L,
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
                        1L,
                        1L
                );

                inMemoryReservationRepository.save(expected);
                ReservationEntity actual = inMemoryReservationRepository.findById(1L)
                        .orElseThrow(() -> new IllegalStateException("엔티티를 찾을 수 없습니다."));

                assertSoftly(softly -> softly.assertThat(actual.getReservationName()).isEqualTo("brie")
                );
            }
        }

        @Nested
        @DisplayName("id가 존재하지 않는 엔티티라면")
        class NotContainEntity {

            private final InMemoryReservationRepository inMemoryReservationRepository = new InMemoryReservationRepository();

            @Test
            @DisplayName("저장 한다.")
            void saveEntity() {
                ReservationEntity expected = new ReservationEntity(
                        1L,
                        "kilian",
                        LocalDate.of(2024, 6, 8),
                        1L,
                        1L
                );

                inMemoryReservationRepository.save(expected);

                ReservationEntity actual = inMemoryReservationRepository.findById(1L)
                        .orElseThrow(() -> new IllegalStateException("엔티티를 찾을 수 없습니다."));
                List<ReservationEntity> all = inMemoryReservationRepository.findAll();

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

            private final InMemoryReservationRepository inMemoryReservationRepository = new InMemoryReservationRepository();

            @Test
            @DisplayName("삭제 한다.")
            void delete() {
                inMemoryReservationRepository.save(
                        new ReservationEntity(
                                1L,
                                "kilian",
                                LocalDate.of(2024, 6, 8),
                                1L,
                                1L
                        )
                );
                List<ReservationEntity> savedEntities = inMemoryReservationRepository.findAll();

                inMemoryReservationRepository.delete(1L);
                List<ReservationEntity> deleteEntities = inMemoryReservationRepository.findAll();

                assertSoftly(softly -> {
                            softly.assertThat(savedEntities.size()).isEqualTo(1);
                            softly.assertThat(deleteEntities.size()).isEqualTo(0);
                        }
                );
            }
        }
    }
}
