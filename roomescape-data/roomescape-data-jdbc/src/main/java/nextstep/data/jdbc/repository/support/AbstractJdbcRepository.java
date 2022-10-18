package nextstep.data.jdbc.repository.support;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.annotation.PostConstruct;

public abstract class AbstractJdbcRepository<T> {


    protected JdbcTemplate jdbcTemplate;

    protected SimpleJdbcInsert simpleJdbcInsert;

    protected AbstractJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void init() {
        simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName(tableName()).usingGeneratedKeyColumns("id");
    }

    protected abstract String tableName();

    protected abstract RowMapper<T> rowMapper();

    protected void deleteAll() {
        jdbcTemplate.update("TRUNCATE TABLE " + tableName());
    }
}
