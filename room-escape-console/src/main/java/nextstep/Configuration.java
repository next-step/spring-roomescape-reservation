package nextstep;

import nextsetp.domain.reservation.ReservationRepository;
import nextsetp.domain.reservation.InmemoryReservationRepository;
import nextsetp.domain.theme.InmemoryThemeRepository;
import nextsetp.domain.theme.ThemeRepository;
import nextstep.app.reservation.ReservationService;
import nextstep.app.theme.ThemeService;

public class Configuration {

    public static ReservationService getReservationService() {
        return new ReservationService(getInmemoryReservationRepository());
    }

    private static ReservationRepository getInmemoryReservationRepository() {
        return new InmemoryReservationRepository();
    }

    public static ThemeService getThemeService() {
        return new ThemeService(getInmemoryThemeRepository());
    }

    private static ThemeRepository getInmemoryThemeRepository() {
        return new InmemoryThemeRepository();
    }
}
