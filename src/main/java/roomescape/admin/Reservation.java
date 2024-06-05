package roomescape.admin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Reservation {

    private static List<Reservation> Reservations = new ArrayList<>();

    private static AtomicLong autoIncrement = new AtomicLong(1);

    private Long id;
    private String name;
    private String date;
    private String time;

    public static void add(SaveReservationRequest saveReservationRequest){
        new Reservation(saveReservationRequest);
    }

    private Reservation(SaveReservationRequest saveReservationRequest) {
        this.id = autoIncrement.getAndIncrement();
        this.name = saveReservationRequest.name();
        this.date = saveReservationRequest.date();
        this.time = saveReservationRequest.time();

        Reservations.add(this);
    }

    public static List<Reservation> getReservations() {
        return Collections.unmodifiableList(Reservations);
    }

    public static void init(){
        Reservations.clear();
        autoIncrement.set(1);
    }

    public static void delete(Long id) {
        Reservations.stream()
                .filter(Reservation -> Reservation.id.equals(id))
                .toList()
                .forEach(index -> Reservations.remove(index));
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

}