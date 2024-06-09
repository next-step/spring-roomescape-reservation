package roomescape.reservation;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.reservationTime.ReservationTime;
import roomescape.theme.Theme;

import java.sql.PreparedStatement;
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
						rs.getString("reservation_date"),
						new ReservationTime(rs.getLong("time_id"), rs.getString("time_start_at")),
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
						rs.getString(3),
						new ReservationTime(rs.getLong(4), rs.getString(5)),
						new Theme(rs.getLong(6), rs.getString(7), rs.getString(8), rs.getString(9)));

		return jdbcTemplate.queryForObject(sql, rowMapper, id);
	}

	public Long save(String name, String date, Long timeId, Long themeId) {
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(con -> {
			PreparedStatement ps = con.prepareStatement("INSERT INTO reservation(name, date, time_id, theme_id) VALUES (?, ?, ?, ?)", new String[]{"id"});
			ps.setString(1, name);
			ps.setString(2, date);
			ps.setLong(3, timeId);
			ps.setLong(4, themeId);

			return ps;
		}, keyHolder);

		return keyHolder.getKey().longValue();
	}

	public void delete(long id) {
		jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
	}
}
