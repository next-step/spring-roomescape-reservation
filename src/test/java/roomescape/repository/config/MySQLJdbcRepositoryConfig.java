package roomescape.repository.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import roomescape.repository.mysql.MySQLJdbcReservationRepository;
import roomescape.repository.mysql.MySQLJdbcReservationTimeRepository;
import roomescape.repository.mysql.MySQLJdbcThemeRepository;

@TestConfiguration
public class MySQLJdbcRepositoryConfig {

    @Bean
    public MySQLJdbcReservationRepository mySQLJdbcReservationRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new MySQLJdbcReservationRepository(namedParameterJdbcTemplate);
    }

    @Bean
    public MySQLJdbcReservationTimeRepository mySQLJdbcReservationTimeRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new MySQLJdbcReservationTimeRepository(namedParameterJdbcTemplate);
    }

    @Bean
    public MySQLJdbcThemeRepository mySQLJdbcThemeRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new MySQLJdbcThemeRepository(namedParameterJdbcTemplate);
    }
}
