package nextstep;

import nextsetp.domain.reservation.ReservationRepository;
import nextsetp.repository.reservation.InmemoryReservationRepository;

public class Configuration {

    public static ReservationService getReservationService() {
        return new ReservationService(getInmemoryReservationRepository());
    }

    private static ReservationRepository getInmemoryReservationRepository() {
        return new InmemoryReservationRepository();
    }
}
