package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.domain.Theme;
import roomescape.dto.reservation.create.ReservationCreateRequest;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ReservationRepositoryImpl implements ReservationRepository{

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reservation> findReservations() {
        String sql = "select r.id as id, r.name as name, r.date as date, rt.id as timeId, rt.start_at as startAt, " +
                "th.id as themeId, th.name as themeName " +
                "from reservation as r inner join " +
                "reservation_time as rt on r.time_id = rt.id " +
                "inner join theme as th on r.theme_id = th.id";
        return jdbcTemplate.query(
                        sql, (rs, rowNum) -> {
                    Reservation entity = new Reservation(
                            rs.getLong("id"),
                            rs.getString("name"),
                            rs.getString("date"),
                            rs.getLong("timeId"),
                            rs.getString("startAt"),
                            rs.getLong("themeId"),
                            rs.getString("themeName")
                    );
                    return entity;
                });
    }

    @Override
    public Reservation createReservation(ReservationCreateRequest dto, ReservationTime time, Theme theme) {
        String sql = "insert into reservation(name, date, time_id, theme_Id) values(?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    sql, new String[]{"id"});
            ps.setString(1, dto.getName());
            ps.setString(2, dto.getDate());
            ps.setLong(3, time.getId());
            ps.setLong(4, theme.getId());
            return ps;
        }, keyHolder);

        long id = keyHolder.getKey().longValue();
        Reservation entity = new Reservation(id, dto.getName(), dto.getDate(), time ,theme);
        return entity;
    }

    @Override
    public void deleteReservation(Long id) {
        String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, Long.valueOf(id));
    }
}
