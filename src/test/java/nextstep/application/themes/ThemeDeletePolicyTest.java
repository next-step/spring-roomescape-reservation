package nextstep.application.themes;

import static org.mockito.BDDMockito.given;

import java.util.List;
import java.util.Optional;
import nextstep.application.reservation.ReservationQueryService;
import nextstep.application.reservation.dto.ReservationRes;
import nextstep.application.themes.dto.ThemeDeleteValidationDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@Sql("/truncate.sql")
@SpringBootTest
@DisplayName("테마 삭제 정책 테스트")
class ThemeDeletePolicyTest {

  @Mock
  private ReservationQueryService reservationQueryService;

  @Test
  void 예약되어있는_테마_삭제시_예외가_발생한다() {
    //given
    var themeId = 1L;
    var sut = new ThemeDeletePolicy(List.of(new ThemeDeleteAssignedReservationValidation(reservationQueryService)));
    given(reservationQueryService.getReservationByThemeId(themeId)).willReturn(
        Optional.of(ReservationRes.builder().build()));
    var validationDto = ThemeDeleteValidationDto.builder()
        .id(themeId)
        .build();
    //when
    //then
    Assertions.assertThatThrownBy(() -> sut.checkValid(validationDto))
        .isInstanceOf(IllegalArgumentException.class);
  }
}
