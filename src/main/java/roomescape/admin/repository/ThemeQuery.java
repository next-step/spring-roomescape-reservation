package roomescape.admin.repository;

public class ThemeQuery {
    private static final String JOIN_TABLE = """
             FROM reservation as r\s
            inner join reservation_time as t\s
               on r.time_id = t.id\s
            inner join theme as te\s
               on r.theme_id = te.id 
            """;

    public static final String FIND_ALL ="""
                    select id as theme_id, \s
                           name as theme_name,\s
                           description as theme_description,\s
                           thumbnail as theme_thumbnail\s
                      from theme
                    """;

    public static final String COUNT_BY_ID = "select count(1) "+
            JOIN_TABLE+
            " where r.theme_id = ?";
    public static final String FIND_BY_ID = FIND_ALL + "\n where id = ?";
    public static final String DELETE = "delete from theme where id = ?";
    public static final String SAVE = "insert into theme(name, description, thumbnail) values (?, ?, ?)";
}