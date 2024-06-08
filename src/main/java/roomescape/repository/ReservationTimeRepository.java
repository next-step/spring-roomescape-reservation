package roomescape.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.annotation.PostConstruct;
import roomescape.domain.ReservationTime;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationTimeRepository {

	private static final RowMapper<ReservationTime> RESERVATION_TIME_ROW_MAPPER;

	private final JdbcTemplate jdbcTemplate;

	private SimpleJdbcInsert jdbcInsert;

	ReservationTimeRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@PostConstruct
	public void init() {
		this.jdbcInsert = new SimpleJdbcInsert(this.jdbcTemplate).withTableName("reservation_time")
			.usingGeneratedKeyColumns("id");
	}

	public ReservationTime save(ReservationTime reservationTime) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("start_at", reservationTime.getStartAt());

		Number generatedId = this.jdbcInsert.executeAndReturnKey(parameters);
		reservationTime.setId(generatedId.longValue());
		return reservationTime;
	}

	public List<ReservationTime> findAll() {
		String sql = "SELECT rt.id, rt.start_at FROM reservation_time rt";
		return this.jdbcTemplate.query(sql, RESERVATION_TIME_ROW_MAPPER);
	}

	public ReservationTime findById(long id) {
		String sql = "SELECT rt.id, rt.start_at FROM reservation_time rt WHERE rt.id = ?";
		return this.jdbcTemplate.queryForObject(sql, RESERVATION_TIME_ROW_MAPPER, id);
	}

	public boolean isExistId(long id) {
		String sql = "SELECT COUNT(*) FROM reservation_time WHERE id = ?";
		int count = this.jdbcTemplate.queryForObject(sql, Integer.class, id);
		return count > 0;
	}

	public void delete(long id) {
		String sql = "DELETE FROM reservation_time WHERE id = ?";
		this.jdbcTemplate.update(sql, id);
	}

	static {
		RESERVATION_TIME_ROW_MAPPER = (resultSet, rowNum) -> {
			long id = resultSet.getLong("id");
			String startAt = resultSet.getString("start_at");

			return ReservationTime.builder().id(id).startAt(startAt).build();
		};
	}

}
