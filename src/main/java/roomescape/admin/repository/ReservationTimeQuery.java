package roomescape.admin.repository;

public class ReservationTimeQuery {
    public static final String FIND_ALL = "select id as time_id, start_at as time_start_at from reservation_time";

    public static final String FIND_BY_ID = FIND_ALL + "\n where id = ?";

    public static final String SAVE = "insert into reservation_time(start_at) values(?)";

    public static final String DELETE = "delete from reservation_time where id = ?";


}