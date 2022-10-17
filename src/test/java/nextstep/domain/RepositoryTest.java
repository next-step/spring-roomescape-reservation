package nextstep.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class RepositoryTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    protected void initReservationTable() {
        String sql = "create table if not exists reservation (" +
                "id bigint not null auto_increment primary key," +
                "schedule_id bigint not null," +
                "date date not null," +
                "time time not null," +
                "name varchar(255) not null)";

        jdbcTemplate.execute("drop table reservation if exists");
        jdbcTemplate.execute(sql);
    }

    protected void initThemeTable() {
        String sql = "create table if not exists theme (" +
                "id bigint not null auto_increment primary key," +
                "name varchar(255) not null," +
                "desc varchar(255) not null," +
                "price int not null)";

        jdbcTemplate.execute("drop table theme if exists");
        jdbcTemplate.execute(sql);
    }

    protected void initScheduleTable() {
        String sql = "create table if not exists schedule (" +
                "id bigint not null auto_increment primary key," +
                "theme_id bigint not null," +
                "date date not null," +
                "time time not null)";

        jdbcTemplate.execute("drop table schedule if exists");
        jdbcTemplate.execute(sql);
    }
}
