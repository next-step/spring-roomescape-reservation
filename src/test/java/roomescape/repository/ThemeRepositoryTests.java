package roomescape.repository;

import java.util.Map;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import roomescape.domain.ReservationTime;
import roomescape.domain.Theme;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ThemeRepositoryTests {

	@InjectMocks
	private ThemeRepository themeRepository;

	@Mock
	private JdbcTemplate jdbcTemplate;

	@Mock
	private SimpleJdbcInsert jdbcInsert;

	@Mock
	private DataSource dataSource;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		given(this.jdbcTemplate.getDataSource()).willReturn(this.dataSource);

		this.jdbcInsert = mock(SimpleJdbcInsert.class);

		ReflectionTestUtils.setField(this.themeRepository, "jdbcInsert", this.jdbcInsert);
	}

	@Test
	void findAll() {
		// given
		Theme theme = Theme.builder().id(1L).name("테마1").description("첫번째테마").thumbnail("썸네일이미지").build();

		given(this.jdbcInsert.executeAndReturnKey(any(Map.class))).willReturn(1L);

		// when
		Theme savedTheme = this.themeRepository.save(theme);

		// then
		assertThat(savedTheme).isNotNull();
		assertThat(savedTheme.getId()).isEqualTo(1L);
	}

	@Test
	void delete() {
		// given
		long id = 1L;
		given(this.jdbcTemplate.update(anyString(), any(Object[].class))).willReturn(1);

		// when
		this.themeRepository.delete(id);

		// then
		verify(this.jdbcTemplate).update(anyString(), eq(id));
	}

	@Test
	void findById() {
		// given
		Theme theme = Theme.builder().id(1L).name("테마1").description("첫번째테마").thumbnail("썸네일이미지").build();

		given(this.jdbcTemplate.queryForObject(any(String.class), any(RowMapper.class), anyLong()))
				.willReturn(theme);

		// when
		Theme result = this.themeRepository.findById(1L);

		// then
		assertThat(result).isEqualTo(theme);
	}

}
