package roomescape.service.impl;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import roomescape.dto.request.ReservationTimeRequest;
import roomescape.dto.response.ReservationTimeResponse;
import roomescape.service.ReservationTimeService;

@Service
public class ReservationTimeServiceImpl implements ReservationTimeService {

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ReservationTimeResponse createReservationTime(ReservationTimeRequest reservationTimeRequest) {
        final String sql = "INSERT INTO reservation_time (start_at) values (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservationTimeRequest.getStartAt());
            return ps;
        }, keyHolder);

        return new ReservationTimeResponse(keyHolder.getKey().longValue(), reservationTimeRequest.getStartAt());
    }

    @Override
    public List<ReservationTimeResponse> findAllReservationTimes() {
        final String sql = "SELECT id, start_at FROM reservation_time";
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> new ReservationTimeResponse(
                        rs.getLong("id")
                        , rs.getString("start_at")
                )
        );
    }

    @Override
    public void deleteReservationTime(Long id) {
        final String sql = "DELETE FROM reservation_time WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
