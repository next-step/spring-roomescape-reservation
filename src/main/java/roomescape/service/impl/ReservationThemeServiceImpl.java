package roomescape.service.impl;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import roomescape.dto.request.ReservationThemeRequest;
import roomescape.dto.response.ReservationThemeResponse;
import roomescape.service.ReservationThemeService;

@Service
public class ReservationThemeServiceImpl implements ReservationThemeService {

    private final JdbcTemplate jdbcTemplate;

    public ReservationThemeServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ReservationThemeResponse createReservationTheme(ReservationThemeRequest request) {
        final String sql = "INSERT INTO theme (name, description, thumbnail) values (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, request.getName());
            ps.setString(2, request.getDescription());
            ps.setString(3, request.getThumbnail());

            return ps;
        }, keyHolder);

        return new ReservationThemeResponse(keyHolder.getKey().longValue()
                , request.getName()
                , request.getDescription()
                , request.getThumbnail());
    }

    @Override
    public List<ReservationThemeResponse> findAllReservationThemes() {
        final String sql = "SELECT id, name, description, thumbnail FROM theme";

        return jdbcTemplate.query(sql, (rs, rowNum) -> new ReservationThemeResponse(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getString("thumbnail")
        ));
    }

    @Override
    public void deleteReservationTheme(Long id) {
        final String sql = "DELETE FROM theme WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
