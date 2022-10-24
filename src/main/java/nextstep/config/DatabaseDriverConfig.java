package nextstep.config;

import nextstep.domain.reservation.repository.JdbcReservationRepository;
import nextstep.domain.reservation.repository.ReservationRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration(proxyBeanMethods = false)
public class DatabaseDriverConfig {

  @Bean
  public ReservationRepository repository(JdbcTemplate jdbcTemplate) {
    return new JdbcReservationRepository(jdbcTemplate);
  }
}
