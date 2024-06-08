package roomescape.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.annotation.PostConstruct;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationRepository {

	private static final RowMapper<Reservation> RESERVATION_ROW_MAPPER;

	private final JdbcTemplate jdbcTemplate;

	private SimpleJdbcInsert jdbcInsert;

	ReservationRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@PostConstruct
	public void init() {
		this.jdbcInsert = new SimpleJdbcInsert(this.jdbcTemplate).withTableName("reservation")
			.usingGeneratedKeyColumns("id");
	}

	public List<Reservation> findAll() {
		String sql = """
						SELECT
							r.id AS reservation_id,
							r.name AS reservation_name,
							r.date AS reservation_date,
							rt.id AS time_id,
							rt.start_at AS time_start_at
						FROM reservation AS r
						INNER JOIN reservation_time AS rt
								ON r.time_id = rt.id
				""";
		return this.jdbcTemplate.query(sql, RESERVATION_ROW_MAPPER);
	}

	public Reservation save(Reservation reservation) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("name", reservation.getName());
		parameters.put("date", reservation.getDate());
		parameters.put("time_id", reservation.getTime().getId());

		Number generatedId = this.jdbcInsert.executeAndReturnKey(parameters);
		reservation.setId(generatedId.longValue());
		return reservation;
	}

	public void delete(long id) {
		String sql = "DELETE FROM reservation WHERE id = ?";
		int result = this.jdbcTemplate.update(sql, id);

		if (result == 0) {
			throw new RuntimeException("No data found for ID: " + id);
		}
	}

	static {
		RESERVATION_ROW_MAPPER = (resultSet, rowNum) -> {
			long timeId = resultSet.getLong("time_id");
			String timeStartAt = resultSet.getString("time_start_at");
			ReservationTime reservationTime = ReservationTime.builder().id(timeId).startAt(timeStartAt).build();

			long id = resultSet.getLong("id");
			String name = resultSet.getString("name");
			String date = resultSet.getString("date");

			return Reservation.builder().id(id).name(name).date(date).time(reservationTime).build();
		};
	}

}
