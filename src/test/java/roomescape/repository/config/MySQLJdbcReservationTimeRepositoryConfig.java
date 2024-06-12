package roomescape.repository.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import roomescape.repository.mysql.MySQLJdbcReservationTimeRepository;

@TestConfiguration
public class MySQLJdbcReservationTimeRepositoryConfig {

    @Bean
    public MySQLJdbcReservationTimeRepository mySQLJdbcReservationRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new MySQLJdbcReservationTimeRepository(namedParameterJdbcTemplate);
    }
}
