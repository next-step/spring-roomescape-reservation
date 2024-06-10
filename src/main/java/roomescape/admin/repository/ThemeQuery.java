package roomescape.admin.repository;

public class ThemeQuery {
    public static final String FIND_ALL = "select id as theme_id, \n" +
                                                    "name as theme_name, \n" +
                                                    "description as theme_description, \n" +
                                                    "thumbnail as theme_thumbnail \n" +
                                            "from theme";
    public static final String FIND_BY_ID = FIND_ALL + "\n where id = ?";
    public static final String DELETE = "delete from theme where id = ?";
    public static final String SAVE = "insert into theme(name, description, thumbnail) values (?, ?, ?)";
}
