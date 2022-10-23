package nextstep.domain.theme;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nextstep.domain.theme.dto.ThemeCommandDto.Create;
import nextstep.domain.theme.dto.ThemeCommandDto.Delete;
import nextstep.domain.theme.validator.ThemeDeleteValidator;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ThemeRepositoryImpl implements ThemeRepository {

  static RowMapper<Theme> themeRowMapper = (resultSet, rowNum) -> Theme.builder()
      .id(resultSet.getLong("id"))
      .name(resultSet.getString("name"))
      .description(resultSet.getString("description"))
      .price(BigDecimal.valueOf(resultSet.getLong("price")))
      .build();

  JdbcTemplate jdbcTemplate;
  Set<ThemeDeleteValidator> deleteValidators;

  @Override
  public Theme save(Create createReq) {
    String name = createReq.name();
    BigDecimal price = createReq.price();
    String description = createReq.desc();

    String saveQuery = """
        insert into theme (name, price, description) values (?, ?, ?)
        """;

    jdbcTemplate.update(saveQuery, name, price, description);

    String getIdQuery = "select last_insert_id ()";
    Long entityId = jdbcTemplate.queryForObject(getIdQuery, Long.class);

    return Theme.builder()
        .id(entityId)
        .name(name)
        .price(price)
        .description(description)
        .build();
  }

  // TODO : 정렬 로직은 어디서?
  @Override
  public List<Theme> findAll() {
    String sql = """
        select id, name, price, description
        from theme 
        order by id
        """;

    return jdbcTemplate.query(sql, themeRowMapper);
  }

  @Override
  public Optional<Theme> findById(Long id) {
    String sql = """
        select id, name, price, description
        from theme 
        where id = ? 
        """;

    try {
      return Optional.ofNullable(jdbcTemplate.queryForObject(sql, themeRowMapper, id));
    } catch (IncorrectResultSizeDataAccessException e) {
      return Optional.empty();
    }
  }

  /**
   * @param deleteReq
   * @return 실제로 삭제 되었는지 여부. 삭제 되었으면 true, 그렇지 않으면 false
   */
  @Override
  public boolean delete(Delete deleteReq) {
    deleteValidators.forEach(validator -> validator.validate(deleteReq));

    String sql = """
        delete 
        from theme 
        where id = ?
        """;

    int update = jdbcTemplate.update(sql, deleteReq.id());
    return update > 0;
  }

}
