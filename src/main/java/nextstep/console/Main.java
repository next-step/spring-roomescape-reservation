package nextstep.console;

import nextstep.repository.MemoryReservationRepository;
import nextstep.repository.ReservationRepository;
import nextstep.service.ReservationService;

public class Main {

    public static void main(String[] args) {
        ReservationRepository reservationRepository = new MemoryReservationRepository();
        ReservationService reservationService = new ReservationService(reservationRepository);
        Console console = new Console(reservationService);

        console.execute();
    }
}
