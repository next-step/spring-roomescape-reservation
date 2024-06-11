package roomescape.admin.repository;

public class ReservationQuery {
    public static final String FIND_ALL = "SELECT \n" +
            "    r.id as reservation_id, \n" +
            "    r.name as reservation_name, \n" +
            "    r.date as reservation_date, \n" +
            "    t.id as time_id, \n" +
            "    t.start_at as time_start_at, \n" +
            "    te.id as theme_id, \n" +
            "    te.name as theme_name, \n" +
            "    te.description as theme_description, \n" +
            "    te.thumbnail as theme_thumbnail \n" +
            "FROM reservation as r \n" +
            "inner join reservation_time as t \n" +
            "on r.time_id = t.id \n" +
            "inner join theme as te \n" +
            "on r.theme_id = te.id \n"
            ;

    public static final String FIND_BY_ID = FIND_ALL + " \n where r.id = ?";

    public static final String SAVE = "insert into reservation(name, date, time_id, theme_id) values(?,?,?,?)";

    public static final String DELETE = "delete from reservation where id = ?";

    public static final String COUNT_BY_DATE_AND_START_AT = "SELECT COUNT(1) as duplicated_count \n" +
                                                            "FROM reservation as r \n" +
                                                            "inner join reservation_time as t \n" +
                                                            "on r.time_id = t.id \n" +
                                                            "inner join theme as te \n" +
                                                            "on r.theme_id = te.id \n" +
                                                            "where r.date = ? \n" +
                                                            "and t.start_at = ?";
}