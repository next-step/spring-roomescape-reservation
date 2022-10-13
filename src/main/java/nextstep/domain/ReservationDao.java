package nextstep.domain;

import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertReservation;

    public ReservationDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertReservation = new SimpleJdbcInsert(dataSource)
            .withTableName("reservation")
            .usingGeneratedKeyColumns("id");
    }

    public Reservation insert(Reservation reservation) {
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(reservation);
        Long id = insertReservation.executeAndReturnKey(parameters).longValue();
        return new Reservation(id, reservation);
    }
}
