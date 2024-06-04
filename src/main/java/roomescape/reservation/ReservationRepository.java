package roomescape.reservation;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ReservationRepository {
	private final JdbcTemplate jdbcTemplate;

	public ReservationRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Reservation> findReservation() {
		return jdbcTemplate.query("SELECT id, name, date, time FROM reservation",
				(rs, rowNum) ->
						new Reservation(rs.getLong("id"),
								rs.getString("name"),
								rs.getString("date"),
								rs.getString("time"))
		);
	}

	public Reservation findReservationByKey(Long id) {
		RowMapper<Reservation> rowMapper = (rs, rowNum) -> new Reservation(
				rs.getLong(1),
				rs.getString(2),
				rs.getString(3),
				rs.getString(4));
		return jdbcTemplate.queryForObject("SELECT id, name, date, time FROM reservation where id = ?", rowMapper, id);
	}

	public Long saveReservation(String name, String date, String time) {
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(con -> {
			PreparedStatement ps = con.prepareStatement("INSERT INTO reservation(name, date, time) VALUES (?, ?, ?)", new String[]{"id"});
			ps.setString(1, name);
			ps.setString(2, date);
			ps.setString(3, time);

			return ps;
		}, keyHolder);

		return keyHolder.getKey().longValue();
	}

	public void deleteReservation(long id) {
		jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
	}
}
