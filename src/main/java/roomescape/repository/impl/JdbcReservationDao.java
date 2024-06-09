package roomescape.repository.impl;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTheme;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationDao;

@Repository
public class JdbcReservationDao implements ReservationDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Reservation save(Reservation reservation) {
        final String sql = "INSERT INTO reservation (name, \"date\", time_id, theme_id) values (?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate());
            ps.setString(3, String.valueOf(reservation.getTime().getId()));
            ps.setString(4, String.valueOf(reservation.getTheme().getId()));

            return ps;
        }, keyHolder);

        return reservation.toEntity(reservation, keyHolder.getKey().longValue());
    }

    @Override
    public List<Reservation> findAll() {
        final String sql = "SELECT "
                + "r.id as reservation_id"
                + ", r.name as reservation_name"
                + ", r.\"date\" as reservation_date"
                + ", t.id as time_id"
                + ", t.start_at as time_start_at"
                + ", theme.id as theme_id"
                + ", theme.name as theme_name"
                + ", theme.description as theme_description"
                + ", theme.thumbnail as theme_thumbnail"
                + " FROM reservation as r"
                + " INNER JOIN reservation_time as t ON r.time_id = t.id"
                + " INNER JOIN theme ON r.theme_id = theme.id";

        return jdbcTemplate.query(sql, (resultSet, rowNum) -> {
            ReservationTime reservationTime = new ReservationTime(
                    resultSet.getLong("time_id")
                    , resultSet.getString("time_start_at"));

            ReservationTheme reservationTheme = new ReservationTheme(
                    resultSet.getLong("theme_id")
                    , resultSet.getString("theme_name")
                    , resultSet.getString("theme_description")
                    , resultSet.getString("theme_thumbnail")
            );

            return new Reservation(resultSet.getLong("id")
                    , resultSet.getString("name")
                    , resultSet.getString("reservation_date")
                    , reservationTime
                    , reservationTheme);
        });
    }

    @Override
    public void delete(Long id) {
        final String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Optional<Reservation> findByDateAndTimeStartAt(String date, String startAt) {
        final String sql = "SELECT"
                + " r.id as reservation_id"
                + " ,r.name as reservation_name"
                + " ,r.\"date\" as reservation_date"
                + " ,t.id as time_id"
                + " ,t.start_at as time_start_at"
                + " FROM reservation r "
                + " INNER JOIN reservation_time t on t.id = r.time_id "
                + " WHERE \"date\" = ? AND t.start_at = ?";

        Reservation reservation = null;
        try {
            reservation = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                ReservationTime reservationTime = new ReservationTime(rs.getLong("time_id"),
                        rs.getString("time_start_at"));
                return new Reservation(
                        rs.getLong("reservation_id")
                        , rs.getString("reservation_name")
                        , rs.getString("reservation_date")
                        , reservationTime
                        , null
                );
            }, date, startAt);
        } catch (EmptyResultDataAccessException e) {
        }

        return Optional.ofNullable(reservation);
    }

    @Override
    public Optional<Reservation> findByTimeId(Long timeId) {
        final String sql = "SELECT id, name, \"date\", time_id, theme_id FROM reservation WHERE time_id = ?";

        Reservation reservation = null;
        try {
            reservation = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new Reservation(
                    rs.getLong("reservation_id")
                    , rs.getString("reservation_name")
                    , rs.getString("reservation_date")
                    , new ReservationTime(rs.getLong("time_id"))
                    , new ReservationTheme(rs.getLong("theme_id"))
            ), timeId);
        } catch (EmptyResultDataAccessException e) {
        }

        return Optional.ofNullable(reservation);
    }

    @Override
    public Optional<Reservation> findByThemeId(Long themeId) {
        final String sql = "SELECT id, name, \"date\", time_id, theme_id FROM reservation WHERE theme_id = ?";

        Reservation reservation = null;
        try {
            reservation = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new Reservation(
                    rs.getLong("reservation_id")
                    , rs.getString("reservation_name")
                    , rs.getString("reservation_date")
                    , new ReservationTime(rs.getLong("time_id"))
                    , new ReservationTheme(rs.getLong("theme_id"))
            ), themeId);
        } catch (EmptyResultDataAccessException e) {
        }

        return Optional.ofNullable(reservation);
    }
}

