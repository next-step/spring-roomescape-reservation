package roomescape.repository.mysql;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import roomescape.repository.config.MySQLJdbcRepositoryConfig;
import roomescape.repository.entity.ThemeEntity;

import java.util.List;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(MySQLJdbcRepositoryConfig.class)
@Sql({"classpath:db/schema/schema.sql", "classpath:db/data/init.sql"})
class MySQLJdbcThemeRepositoryTest {

    @Autowired
    private MySQLJdbcThemeRepository mySQLJdbcThemeRepository;

    @Nested
    @DisplayName("save 함수를 수행할 때")
    class InSaveFunction {
        @Nested
        @DisplayName("id가 존재하는 엔티티라면")
        class hasIdEntity {

            @BeforeEach
            void setUp() {
                mySQLJdbcThemeRepository.save(
                        new ThemeEntity(
                                1L,
                                "레벨2 탈출",
                                "우테코 레벨2를 탈출하는 내용입니다.",
                                "https://i.pinimg.com/236x/6e/bc/46/6ebc461a94a49f9ea3b8bbe2204145d4.jpg"
                        )
                );
            }

            @Test
            @DisplayName("업데이트 한다.")
            void updateEntity() {
                ThemeEntity expected = new ThemeEntity(
                        1L,
                        "레벨2 탈출 실패",
                        "우테코 레벨2를 탈출하는 내용입니다.",
                        "https://i.pinimg.com/236x/6e/bc/46/6ebc461a94a49f9ea3b8bbe2204145d4.jpg"
                );

                mySQLJdbcThemeRepository.save(expected);
                ThemeEntity actual = mySQLJdbcThemeRepository.findById(1L)
                        .orElseThrow(() -> new IllegalStateException("엔티티를 찾지 못했습니다."));

                assertSoftly(softly -> softly.assertThat(actual.getName()).isEqualTo(expected.getName()));
            }
        }

        @Nested
        @DisplayName("id가 존재하지 않는 엔티티라면")
        class NotContainEntity {

            @Test
            @DisplayName("저장 한다.")
            void saveEntity() {
                ThemeEntity expected = new ThemeEntity(
                        1L,
                        "레벨2 탈출 실패",
                        "우테코 레벨2를 탈출하는 내용입니다.",
                        "https://i.pinimg.com/236x/6e/bc/46/6ebc461a94a49f9ea3b8bbe2204145d4.jpg"
                );
                mySQLJdbcThemeRepository.save(expected);

                ThemeEntity actual = mySQLJdbcThemeRepository.findById(1L)
                        .orElseThrow(() -> new IllegalStateException("엔티티를 찾을 수 없습니다."));
                List<ThemeEntity> all = mySQLJdbcThemeRepository.findAll();

                assertSoftly(softly -> {
                            softly.assertThat(actual.getName()).isEqualTo(expected.getName());
                            softly.assertThat(all.size()).isEqualTo(1);
                        }
                );
            }
        }
    }
}
