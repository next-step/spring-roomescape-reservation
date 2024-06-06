package roomescape.admin;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservationTime {
    private long id;
    private String startAt;

    public static ReservationTime add(SaveReservationTimeRequest saveReservationTimeRequest){
        return new ReservationTime(saveReservationTimeRequest);
    }

    private ReservationTime(SaveReservationTimeRequest saveReservationTimeRequest) {
        this.startAt = saveReservationTimeRequest.startAt();
    }

    public static ReservationTime read(ResultSet rs){
        return new ReservationTime(rs);
    }

    private ReservationTime(ResultSet rs) {
        try {
            this.id = rs.getLong("time_id");
            this.startAt = rs.getString("time_start_at");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }
}