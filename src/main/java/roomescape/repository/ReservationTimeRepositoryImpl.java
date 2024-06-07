package roomescape.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationDto;
import roomescape.dto.ReservationTimeDto;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ReservationTimeRepositoryImpl implements ReservationTimeRepository{

    private JdbcTemplate jdbcTemplate;

    public ReservationTimeRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ReservationTimeDto addTime(ReservationTimeDto dto) {
        String sql = "insert into reservation_time(start_at) values(?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    sql,new String[]{"id"});
            ps.setString(1, dto.getStartAt());
            return ps;
        },keyHolder);

        long timeId = keyHolder.getKey().longValue();
        return findReservationTimeById(timeId);
    }
    @Override
    public ReservationTimeDto findReservationTimeById(Long id) {
        String sql = "select id, start_at from reservation_time where id = ?";
        return jdbcTemplate.queryForObject(
                sql, (rs, rowNum) -> {
                    ReservationTimeDto reservationTimeDto = new ReservationTimeDto(
                            rs.getLong("id"),
                            rs.getString("start_at")
                    );
                    return reservationTimeDto;
                }, id);
    }

    @Override
    public List<ReservationTime> findTimeList() {
        String sql = "select id, start_at from reservation_time";

        return jdbcTemplate.query(
                sql, (rs, rowNum) -> {
                    ReservationTime reservationTime = new ReservationTime(
                            rs.getLong("id"),
                            rs.getString("start_at")
                    );
                    return reservationTime;
                });
    }

    @Override
    public void deleteTime(Long id) {
        String sql = "delete from reservation_time where id = ?";
        jdbcTemplate.update(sql, Long.valueOf(id));
    }
}
