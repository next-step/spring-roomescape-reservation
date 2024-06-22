package roomescape.repositories;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.entities.Reservation;
import roomescape.entities.ReservationTime;
import roomescape.entities.Theme;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public class ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Reservation save(Reservation reservation){
        String sql = "INSERT INTO RESERVATION(date, time_id, name, theme_id) VALUES(?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.getDate());
            ps.setLong(2, reservation.getReservationTime().getId());
            ps.setString(3, reservation.getName());
            ps.setLong(4, reservation.getTheme().getId());
            return ps;
        }, keyHolder);

        return new Reservation(keyHolder.getKey().longValue(),
          reservation.getName(),
          reservation.getDate(),
          reservation.getReservationTime(),
          reservation.getTheme()
        );
    }

    public List<Reservation> findAll(){
        String sql = """
                      SELECT r.id AS reservation_id,
                      r.name AS reservation_name,
                      r.date AS reservation_date,
                      t.start_at AS time,
                      th.id AS theme_id,
                      th.name AS theme_name,
                      th.description AS theme_description,
                      th.thumbnail AS theme_thumbnail
               FROM reservation AS r
               INNER JOIN reservation_time AS t
               ON r.time_id = t.id
               INNER JOIN theme AS th
               ON r.theme_id = th.id
            """;

        List<Reservation> reservations = jdbcTemplate.query(sql,
          (rs, rowNum) -> new Reservation(
            rs.getLong("reservation_id"),
            rs.getString("reservation_name"),
            rs.getString("reservation_date"),
            new ReservationTime(
              rs.getLong("reservation_id"),
              rs.getString("time")),
            new Theme(
              rs.getLong("id"),
              rs.getString("theme_name"),
              rs.getString("theme_description"),
              rs.getString("theme_thumbnail")
            )
          ));

        return reservations;
    }

    public Optional<Reservation> findByReservationTimeId(Long id){
        String sql = """
          SELECT r.id AS reservation_id,
              r.name AS reservation_name,
              r.date AS reservation_date,
              t.start_at AS time
          FROM reservation AS r
          INNER JOIN reservation_time AS t
          ON r.time_id = t.id
          WHERE r.time_id = ?
        """;

        try {
            Reservation reservation = jdbcTemplate.queryForObject(
              sql, (rs, rowNum) -> new Reservation(
                rs.getLong("reservation_id"),
                rs.getString("reservation_date"),
                rs.getString("reservation_name"),
                new ReservationTime(
                  rs.getLong("reservation_id"),
                  rs.getString("time")),
                new Theme(
                  rs.getLong("theme_id"),
                  rs.getString("theme_name"),
                  rs.getString("theme_description"),
                  rs.getString("theme_thumbnail")
              )), id);

            return Optional.ofNullable(reservation);
        } catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    public Optional<Reservation> findByDateAndTime(String date, String time){
        String sql = """
          SELECT r.id AS reservation_id,
              r.name AS reservation_name,
              r.date AS reservation_date,
              t.start_at AS time
          FROM reservation AS r
          INNER JOIN reservation_time AS t
          ON r.time_id = t.id
          WHERE r.date = ? AND t.start_at = ?
        """;

        try {
            Reservation reservation = jdbcTemplate.queryForObject(sql,
              (rs, rowNum) -> new Reservation(
                rs.getLong("reservation_id"),
                rs.getString("reservation_date"),
                rs.getString("reservation_name"),
                new ReservationTime(
                  rs.getLong("reservation_id"),
                  rs.getString("time")),
                new Theme(
                  rs.getLong("theme_id"),
                  rs.getString("theme_name"),
                  rs.getString("theme_description"),
                  rs.getString("theme_thumbnail"))
              ), date, time);

            return Optional.ofNullable(reservation);
        } catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    public void deleteById(Long id){
        String sql = "DELETE FROM RESERVATION WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
