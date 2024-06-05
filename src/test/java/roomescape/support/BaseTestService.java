package roomescape.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;

@JdbcTest
@AutoConfigureTestDatabase
public class BaseTestService {

    @Autowired
    protected JdbcTemplate template;
    @Autowired
    protected PlatformTransactionManager transactionManager;
    protected TransactionStatus transactionStatus;

}
