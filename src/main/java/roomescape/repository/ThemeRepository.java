package roomescape.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.annotation.PostConstruct;
import roomescape.domain.Theme;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class ThemeRepository {

	private static final RowMapper<Theme> THEME_ROW_MAPPER;

	private final JdbcTemplate jdbcTemplate;

	private SimpleJdbcInsert jdbcInsert;

	ThemeRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@PostConstruct
	public void init() {
		this.jdbcInsert = new SimpleJdbcInsert(this.jdbcTemplate).withTableName("theme").usingGeneratedKeyColumns("id");
	}

	public List<Theme> findAll() {
		String sql = """
					SELECT id, name, description, thumbnail FROM theme
				""";
		return this.jdbcTemplate.query(sql, THEME_ROW_MAPPER);
	}

	public Theme findById(long id) {
		String sql = "SELECT t.id, t.name, t.description, t.thumbnail FROM theme t WHERE t.id = ?";
		return this.jdbcTemplate.queryForObject(sql, THEME_ROW_MAPPER, id);
	}

	public Theme save(Theme theme) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("name", theme.getName());
		parameters.put("description", theme.getDescription());
		parameters.put("thumbnail", theme.getThumbnail());

		Number generatedId = this.jdbcInsert.executeAndReturnKey(parameters);
		theme.setId(generatedId.longValue());
		return theme;
	}

	public void delete(long id) {
		String sql = "DELETE FROM theme WHERE id = ?";
		int result = this.jdbcTemplate.update(sql, id);

		if (result == 0) {
			throw new RuntimeException("No data found for ID: " + id);
		}
	}

	static {
		THEME_ROW_MAPPER = (resultSet, rowNum) -> {
			long id = resultSet.getLong("id");
			String name = resultSet.getString("name");
			String description = resultSet.getString("description");
			String thumbnail = resultSet.getString("thumbnail");

			return Theme.builder().id(id).name(name).description(description).thumbnail(thumbnail).build();
		};
	}

}
