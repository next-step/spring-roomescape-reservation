package roomescape.repository.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import roomescape.repository.mysql.MySQLJdbcReservationRepository;

@TestConfiguration
public class MySQLJdbcReservationRepositoryConfig {
    
    @Bean
    public MySQLJdbcReservationRepository mySQLJdbcReservationRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new MySQLJdbcReservationRepository(namedParameterJdbcTemplate);
    }
}
