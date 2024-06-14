package roomescape.admin.repository;

public class ReservationTimeQuery {

    private static final String JOIN_TABLE = """
             FROM reservation as r\s
            inner join reservation_time as t\s
               on r.time_id = t.id\s
            inner join theme as te\s
               on r.theme_id = te.id 
            """;

    public static final String FIND_ALL = "select id as time_id, start_at as time_start_at from reservation_time";

    public static final String COUNT_BY_ID = "select count(1) " +
            JOIN_TABLE +
            " where r.time_id = ?";

    public static final String FIND_BY_ID = FIND_ALL + "\n where id = ?";

    public static final String SAVE = "insert into reservation_time(start_at) values(?)";

    public static final String DELETE = "delete from reservation_time where id = ?";
}