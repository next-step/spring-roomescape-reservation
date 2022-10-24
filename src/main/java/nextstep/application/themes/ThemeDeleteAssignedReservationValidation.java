package nextstep.application.themes;

import lombok.RequiredArgsConstructor;
import nextstep.application.reservation.ReservationQueryService;
import nextstep.application.themes.dto.ThemeDeleteValidationDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ThemeDeleteAssignedReservationValidation implements ThemeDeleteValidation {

  private final ReservationQueryService reservationQueryService;

  @Override
  public void checkValid(ThemeDeleteValidationDto validationDto) {
    var reservation = reservationQueryService.getReservationByThemeId(validationDto.id());
    if (reservation.isPresent()) {
      var reservationRes = reservation.get();
      throw new IllegalArgumentException(String.format("예약이 되어있어 삭제가 불가능합니다. 예약ID: %s", reservationRes.id()));
    }
  }

  @Override
  public int priority() {
    return 1;
  }
}
