package roomescape.service.impl;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import roomescape.dto.request.ReservationRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.service.ReservationService;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final JdbcTemplate jdbcTemplate;

    public ReservationServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ReservationResponse createReservation(ReservationRequest request) {
        final String sql = "INSERT INTO reservation (name, \"date\", time_id, theme_id) values (?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, request.getName());
            ps.setString(2, request.getDate());
            ps.setString(3, request.getTimeId());
            ps.setString(4, request.getThemeId());

            return ps;
        }, keyHolder);

        return new ReservationResponse(keyHolder.getKey().longValue()
                , request.getName()
                , request.getDate()
                , request.getTimeId()
                , request.getThemeId()
        );
    }

    @Override
    public List<ReservationResponse> findAllReservations() {
        final String sql = "SELECT "
                + "r.id as reservation_id"
                + ", r.name as reservation_name"
                + ", r.\"date\" as reservation_date"
                + ", t.start_at as time_start_at"
                + ", theme.name as theme_name"
                + " FROM reservation as r"
                + " INNER JOIN reservation_time as t ON r.time_id = t.id"
                + " INNER JOIN theme ON r.theme_id = theme.id";

        return jdbcTemplate.query(sql, (resultSet, rowNum) ->
                new ReservationResponse(resultSet.getLong("id")
                        , resultSet.getString("name")
                        , resultSet.getString("reservation_date")
                        , resultSet.getString("time_start_at")
                        , resultSet.getString("theme_name"))
        );
    }

    @Override
    public void deleteReservation(Long id) {
        final String sql = "DELETE FROM reservation where id = ?";
        jdbcTemplate.update(sql, id);
    }
}
