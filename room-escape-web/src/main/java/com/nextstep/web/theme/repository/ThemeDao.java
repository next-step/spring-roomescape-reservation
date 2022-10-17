package com.nextstep.web.theme.repository;

import com.nextstep.web.theme.repository.entity.ThemeEntity;
import nextsetp.domain.theme.Theme;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ThemeDao {
    private static final String TABLE_NAME = "THEME";
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final ThemeRowMapper rowMapper;

    public ThemeDao(NamedParameterJdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = new ThemeRowMapper();
        this.jdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME);
    }

    public Long save(ThemeEntity themeEntity) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("NAME", themeEntity.getName());
        parameters.put("DESC", themeEntity.getDesc());
        parameters.put("PRICE", themeEntity.getPrice());

        return (long) jdbcInsert.execute(parameters);
    }

    public Optional<ThemeEntity> findById(Long id) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE id = :id";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", id);
        return jdbcTemplate.query(query, namedParameters, rowMapper).stream().findFirst();
    }

    public List<ThemeEntity> findAll() {
        String query = "SELECT * FROM " + TABLE_NAME;
        return jdbcTemplate.query(query, rowMapper);
    }

    public void delete(Long id) {
        String query = "DELETE FROM " + TABLE_NAME + " WHERE id = :id";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", id);
        jdbcTemplate.update(query, namedParameters);
    }

    public class ThemeRowMapper implements RowMapper<ThemeEntity> {
        @Override
        public ThemeEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            ThemeEntity themeEntity = new ThemeEntity(
                    rs.getLong("ID"),
                    rs.getString("name"),
                    rs.getString("desc"),
                    rs.getLong("price")
            );
            return themeEntity;
        }
    }
}
