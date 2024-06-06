package roomescape.reservationTime;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ReservationTimeRepository {
	private final JdbcTemplate jdbcTemplate;

	public ReservationTimeRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}


	public Long save(String startAt) {
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(con -> {
			PreparedStatement ps = con.prepareStatement("INSERT INTO reservation_time(start_at) VALUES (?)", new String[]{"id"});
			ps.setString(1, startAt);

			return ps;
		}, keyHolder);

		return keyHolder.getKey().longValue();
	}

	public ReservationTime findById(Long id) {
		RowMapper<ReservationTime> rowMapper = (rs, rowNum) -> new ReservationTime(
				rs.getLong(1),
				rs.getString(2));
		return jdbcTemplate.queryForObject("SELECT id, start_at FROM reservation_time where id = ?", rowMapper, id);
	}

	public List<ReservationTime> find() {
		return jdbcTemplate.query("SELECT id, start_at FROM reservation_time",
				(rs, rowNum) ->
						new ReservationTime(rs.getLong("id"),
								rs.getString("start_at"))
		);
	}

	public void delete(Long id) {
		jdbcTemplate.update("DELETE FROM reservation_time WHERE id = ?", id);
	}
}
