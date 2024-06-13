package roomescape.admin.repository;

public class ReservationQuery {
    private static final String JOIN_TABLE = """
             FROM reservation as r\s
            inner join reservation_time as t\s
               on r.time_id = t.id\s
            inner join theme as te\s
               on r.theme_id = te.id 
            """;

    public static final String FIND_ALL = """ 
                            SELECT r.id as reservation_id,\s
                                    r.name as reservation_name,\s
                                    r.date as reservation_date,\s
                                    t.id as time_id,\s
                                    t.start_at as time_start_at,\s
                                    te.id as theme_id,\s
                                    te.name as theme_name,\s
                                    te.description as theme_description,\s
                                    te.thumbnail as theme_thumbnail\s
                        """ +
            JOIN_TABLE;

    public static final String FIND_BY_ID = FIND_ALL + "where r.id = ?";

    public static final String SAVE = "insert into reservation(name, date, time_id, theme_id) values(?,?,?,?)";

    public static final String DELETE = "delete from reservation where id = ?";

    public static final String COUNT_BY_DATE_AND_START_AT = "SELECT COUNT(1) " +
            JOIN_TABLE +
            """
                where r.date = ?
                  and t.start_at = ?
            """;
}