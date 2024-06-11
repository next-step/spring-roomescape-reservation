package roomescape.reservation;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.reservationTime.ReservationTime;
import roomescape.theme.Theme;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.List;

@Repository
public class ReservationRepository {
	private final JdbcTemplate jdbcTemplate;

	public ReservationRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Reservation> find() {
		String sql = new StringBuilder()
				.append("SELECT ")
				.append("r.id as reservation_id, ")
				.append("r.name as reservation_name, ")
				.append("r.date as reservation_date, ")
				.append("t.id as time_id, ")
				.append("t.start_at as time_start_at, ")
				.append("th.id as theme_id, ")
				.append("th.name as theme_name, ")
				.append("th.description as theme_description, ")
				.append("th.thumbnail as theme_thumbnail ")
				.append("FROM reservation as r ")
				.append("inner join reservation_time as t ")
				.append("on r.time_id = t.id ")
				.append("inner join theme as th ")
				.append("on r.theme_id = th.id").toString();

		return jdbcTemplate.query(sql, (rs, rowNum) ->
				new Reservation(rs.getLong("reservation_id"),
						rs.getString("reservation_name"),
						rs.getDate("reservation_date").toLocalDate(),
						new ReservationTime(rs.getLong("time_id"), rs.getTime("time_start_at").toLocalTime()),
						new Theme(rs.getLong("theme_id"), rs.getString("theme_name"), rs.getString("theme_description"), rs.getString("theme_thumbnail"))));
	}

	public Reservation findByKey(Long id) {
		String sql = new StringBuilder()
				.append("SELECT ")
				.append("r.id as reservation_id, ")
				.append("r.name as reservation_name, ")
				.append("r.date as reservation_date, ")
				.append("t.id as time_id, ")
				.append("t.start_at as time_start_at, ")
				.append("th.id as theme_id, ")
				.append("th.name as theme_name, ")
				.append("th.description as theme_description, ")
				.append("th.thumbnail as theme_thumbnail ")
				.append("FROM reservation as r ")
				.append("inner join reservation_time as t ")
				.append("on r.time_id = t.id ")
				.append("inner join theme as th ")
				.append("on r.theme_id = th.id ")
				.append("WHERE r.id = ?").toString();

		RowMapper<Reservation> rowMapper = (rs, rowNum) ->
				new Reservation(rs.getLong(1),
						rs.getString(2),
						rs.getDate(3).toLocalDate(),
						new ReservationTime(rs.getLong(4), rs.getTime(5).toLocalTime()),
						new Theme(rs.getLong(6), rs.getString(7), rs.getString(8), rs.getString(9)));

		return jdbcTemplate.queryForObject(sql, rowMapper, id);
	}

	public int countByTime(Long timeId) {
		String sql = "SELECT COUNT(*) FROM reservation WHERE time_id = ?";
		return jdbcTemplate.queryForObject(sql, Integer.class, timeId);
	}

	public int countByTheme(Long themeId) {
		String sql = "SELECT COUNT(*) FROM reservation WHERE theme_id = ?";
		return jdbcTemplate.queryForObject(sql, Integer.class, themeId);
	}

	public int countByDateAndTimeAndTheme(LocalDate date, Long timeId, Long themeId) {
		String sql = "SELECT COUNT(*) FROM reservation WHERE date = ? AND time_id = ? AND theme_id = ?";
		return jdbcTemplate.queryForObject(sql, Integer.class, date.toString(), timeId, themeId);
	}

	public Reservation save(Reservation reservation) {
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(con -> {
			PreparedStatement ps = con.prepareStatement("INSERT INTO reservation(name, date, time_id, theme_id) VALUES (?, ?, ?, ?)", new String[]{"id"});
			ps.setString(1, reservation.getName());
			ps.setString(2, reservation.getDate().toString());
			ps.setLong(3, reservation.getReservationTime().getId());
			ps.setLong(4, reservation.getTheme().getId());

			return ps;
		}, keyHolder);

		return new Reservation(keyHolder.getKey().longValue(),
				reservation.getName(),
				reservation.getDate(),
				reservation.getReservationTime(),
				reservation.getTheme());
	}

	public void delete(long id) {
		jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
	}
}
