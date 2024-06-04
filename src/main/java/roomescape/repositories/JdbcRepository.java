package roomescape.repositories;

import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class JdbcRepository implements ReservationRepository{
    private final DataSource dataSource;

    public JdbcRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }


}
