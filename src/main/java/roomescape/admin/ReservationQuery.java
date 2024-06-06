package roomescape.admin;

public class ReservationQuery {
    public static final String READ_ALL = "SELECT \n" +
            "    r.id as reservation_id, \n" +
            "    r.name as reservation_name, \n" +
            "    r.date as reservation_date, \n" +
            "    t.id as time_id, \n" +
            "    t.start_at as time_start_at \n" +
            "FROM reservation as r \n" +
            "inner join reservation_time as t \n" +
            "on r.time_id = t.id";

    public static final String READ_BY_ID = READ_ALL + " \n where r.id = ?";

    public static final String SAVE = "insert into reservation(name, date, time_id) values(?,?,?)";

    public static final String DELETE = "delete from reservation where id = ?";
}