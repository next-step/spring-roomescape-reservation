package roomescape.application.error.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExceptionLogger {

    private final Logger logger;

    public ExceptionLogger(Class<?> cls) {
        this.logger = LoggerFactory.getLogger(cls);
    }

    public void log(Exception ex) {
        logger.error(ExceptionLog.from(ex).toString());
    }
}
