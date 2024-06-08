package roomescape.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.annotation.PostConstruct;
import roomescape.controller.dto.ReservationRequest;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.domain.Theme;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

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
							rt.start_at AS time_start_at,
							t.id AS theme_id,
							t.name AS theme_name,
							t.description AS theme_description,
							t.thumbnail AS theme_thumbnail
						FROM reservation AS r
						INNER JOIN reservation_time AS rt
								ON r.time_id = rt.id
						INNER JOIN theme AS t
								ON r.theme_id = t.id
				""";
		return this.jdbcTemplate.query(sql, RESERVATION_ROW_MAPPER);
	}

	public boolean isExistId(long id) {
		String sql = "SELECT COUNT(*) FROM reservation WHERE id = ?";
		int count = this.jdbcTemplate.queryForObject(sql, Integer.class, id);
		return count > 0;
	}

	public Reservation save(Reservation reservation) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("name", reservation.getName());
		parameters.put("date", reservation.getDate());
		parameters.put("time_id", reservation.getTime().getId());
		parameters.put("theme_id", reservation.getTheme().getId());

		Number generatedId = this.jdbcInsert.executeAndReturnKey(parameters);
		reservation.setId(generatedId.longValue());
		return reservation;
	}

	public boolean isDuplicateReservation(ReservationRequest request) {
		String sql = "SELECT COUNT(*) FROM reservation WHERE date = ? AND time_id = ?";
		int count = this.jdbcTemplate.queryForObject(sql, Integer.class, request.date(), request.timeId());
		return count > 0;
	}

	public void delete(long id) {
		String sql = "DELETE FROM reservation WHERE id = ?";
		this.jdbcTemplate.update(sql, id);
	}

	static {
		RESERVATION_ROW_MAPPER = (resultSet, rowNum) -> {
			long timeId = resultSet.getLong("time_id");
			String timeStartAt = resultSet.getString("time_start_at");
			ReservationTime reservationTime = ReservationTime.builder().id(timeId).startAt(timeStartAt).build();

			long id = resultSet.getLong("id");
			String name = resultSet.getString("name");
			String date = resultSet.getString("date");

			long themeId = resultSet.getLong("theme_id");
			String themeName = resultSet.getString("theme_name");
			String themeDescription = resultSet.getString("theme_description");
			String themeThumbnail = resultSet.getString("theme_thumbnail");
			Theme theme = Theme.builder()
				.id(themeId)
				.name(themeName)
				.description(themeDescription)
				.thumbnail(themeThumbnail)
				.build();

			return Reservation.builder().id(id).name(name).date(date).time(reservationTime).theme(theme).build();
		};
	}

}
