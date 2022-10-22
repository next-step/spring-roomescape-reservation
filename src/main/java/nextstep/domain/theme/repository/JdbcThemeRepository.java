package nextstep.domain.theme.repository;

import java.util.List;
import lombok.RequiredArgsConstructor;
import nextstep.domain.theme.ThemeEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JdbcThemeRepository implements ThemeRepository {

  private final JdbcTemplate jdbcTemplate;

  private final RowMapper<ThemeEntity> rowMapper = (resultSet, rowNum) -> {
    var entity = ThemeEntity.builder()
        .id(resultSet.getLong("id"))
        .name(resultSet.getString("name"))
        .desc(resultSet.getString("desc"))
        .price(resultSet.getBigDecimal("price"))
        .build();
    return entity;
  };

  @Override
  public ThemeEntity save(ThemeEntity themes) {
    var keyHolder = new GeneratedKeyHolder();

    var sql = "insert into theme (name, desc, price) values (?, ?, ?)";
    jdbcTemplate.update(con -> {
      var ps = con.prepareStatement(sql, new String[]{"id"});
      ps.setString(1, themes.getName());
      ps.setString(2, themes.getDesc());
      ps.setBigDecimal(3, themes.getPrice());
      return ps;
    }, keyHolder);
    return themes.toBuilder()
        .id(keyHolder.getKey().longValue())
        .build();
  }

  @Override
  public List<ThemeEntity> findAllThemes() {
    var sql = "select * from theme";
    return jdbcTemplate.query(sql, rowMapper);
  }

  @Override
  public void deleteById(Long id) {
    var sql = "delete from theme where id = ?";
    jdbcTemplate.update(sql, id);
  }
}
