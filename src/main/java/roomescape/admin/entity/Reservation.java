package roomescape.admin.entity;

import roomescape.admin.dto.SaveReservationRequest;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Reservation {

    private Long id;
    private String name;
    private String date;
    private ReservationTime time;

    public static Reservation add(SaveReservationRequest saveReservationRequest){
        return new Reservation(saveReservationRequest);
    }

    private Reservation(SaveReservationRequest saveReservationRequest) {
        this.name = saveReservationRequest.name();
        this.date = saveReservationRequest.date();
    }

    public static Reservation read(ResultSet rs){
        return new Reservation(rs);
    }

    private Reservation(ResultSet rs) {
        try {
            this.id = rs.getLong("reservation_id");
            this.name = rs.getString("reservation_name");
            this.date = rs.getString("reservation_date");
            this.time = ReservationTime.read(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

    public ReservationTime getTime() {
        return time;
    }
}