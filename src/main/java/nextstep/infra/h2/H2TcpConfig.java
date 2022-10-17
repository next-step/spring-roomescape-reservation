package nextstep.infra.h2;

import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

@Configuration
public class H2TcpConfig {
    @Bean
    public Server h2Server() throws SQLException {
        return Server.createTcpServer().start(); // default port 9092
    }
}
