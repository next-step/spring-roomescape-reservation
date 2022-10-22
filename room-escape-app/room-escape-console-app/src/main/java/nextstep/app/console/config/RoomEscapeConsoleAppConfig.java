package nextstep.app.console.config;

import nextstep.core.config.RoomEscapeCoreConfig;
import nextstep.infra.h2.config.RoomEscapeH2Config;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({
        RoomEscapeCoreConfig.class,
        RoomEscapeH2Config.class
})
@Configuration
public class RoomEscapeConsoleAppConfig {
}
