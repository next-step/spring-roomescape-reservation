package nextstep.domain.reservation.validator;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import nextstep.domain.reservation.ReservationFixtureFactory;
import nextstep.domain.reservation.dto.ReservationCommandDto.Create;
import nextstep.domain.reservation.exception.ReservationIllegalArgumentException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;

class ReservationNameValidatorTest {

  ReservationNameValidator validator = new ReservationNameValidator();

  @DisplayName("이름이 적절하면 통과한다.")
  @Test
  void pass() {
    // given
    var reservationFixtureFactory = ReservationFixtureFactory.newInstance();
    // 이름 : 성시형
    Create fixtureCreateReq = reservationFixtureFactory.getFixtureCreateReq();

    // when & then
    assertThatCode(() -> validator.validate(
        fixtureCreateReq
    )).doesNotThrowAnyException();
  }

  @DisplayName("이름이 비어있거나 null이면 실패한다.")
  @NullSource
  @EmptySource
  @ParameterizedTest
  void whenNameIsEmptyOrNull(String name) {
    // given
    // when & then
    assertThatThrownBy(() -> validator.validate(
        Create.builder()
            .name(name)
            .build()
    )).isInstanceOf(ReservationIllegalArgumentException.class)
        .hasMessageContaining("예약의 이름은 공란일 수 없습니다.");
  }

}