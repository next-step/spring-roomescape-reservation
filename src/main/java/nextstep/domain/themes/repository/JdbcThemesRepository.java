package nextstep.domain.themes.repository;

import lombok.RequiredArgsConstructor;
import nextstep.domain.themes.ThemesEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JdbcThemesRepository implements ThemesRepository {

  private final JdbcTemplate jdbcTemplate;

  private final RowMapper<ThemesEntity> rowMapper = (resultSet, rowNum) -> {
    var entity = ThemesEntity.builder()
        .id(resultSet.getLong("id"))
        .name(resultSet.getString("name"))
        .desc(resultSet.getString("desc"))
        .price(resultSet.getBigDecimal("price"))
        .build();
    return entity;
  };

  @Override
  public ThemesEntity save(ThemesEntity themes) {
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
}
