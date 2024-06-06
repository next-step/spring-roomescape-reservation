package roomescape.admin;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Reservation {

    private Long id;
    private String name;
    private String date;
    private String time;

    public static Reservation add(SaveReservationRequest saveReservationRequest){
        return new Reservation(saveReservationRequest);
    }

    private Reservation(SaveReservationRequest saveReservationRequest) {
        this.name = saveReservationRequest.name();
        this.date = saveReservationRequest.date();
        this.time = saveReservationRequest.time();
    }

    public static Reservation read(ResultSet rs){
        return new Reservation(rs);
    }

    private Reservation(ResultSet rs) {
        try {
            this.id = rs.getLong("id");
            this.name = rs.getString("name");
            this.date = rs.getString("date");
            this.time = rs.getString("time");
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

    public String getTime() {
        return time;
    }

}