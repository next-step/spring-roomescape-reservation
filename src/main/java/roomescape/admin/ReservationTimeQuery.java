package roomescape.admin;

public class ReservationTimeQuery {
    public static final String READ_ALL = "select id as time_id, start_at as time_start_at from reservation_time";

    public static final String SAVE = "insert into reservation_time(start_at) values(?)";

    public static final String DELETE = "delete from reservation_time where id = ?";

    public static final String READ_BY_ID = READ_ALL + "\n where id = ?";
}