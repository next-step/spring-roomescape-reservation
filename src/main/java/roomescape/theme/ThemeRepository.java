package roomescape.theme;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ThemeRepository {
	private final JdbcTemplate jdbcTemplate;

	public ThemeRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Theme> find() {
		return jdbcTemplate.query("SELECT id, name, description, thumbnail FROM theme", (rs, rowNum) ->
				new Theme(rs.getLong(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4)));
	}

	public Theme findById(Long id) {
		RowMapper<Theme> rowMapper = (rs, rowNum) -> new Theme(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4));

		return DataAccessUtils.singleResult(jdbcTemplate.query("SELECT id, name, description, thumbnail FROM theme where id = ?", rowMapper, id));
	}

	public Long save(String name, String description, String thumbnail) {
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(con -> {
			PreparedStatement ps = con.prepareStatement("INSERT INTO theme(name, description, thumbnail) VALUES (?, ?, ?)", new  String[]{"id"});
			ps.setString(1, name);
			ps.setString(2, description);
			ps.setString(3, thumbnail);

			return ps;
		}, keyHolder);

		return keyHolder.getKey().longValue();
	}

	public void delete(Long id) {
		jdbcTemplate.update("DELETE FROM theme WHERE id = ?", id);
	}
}
