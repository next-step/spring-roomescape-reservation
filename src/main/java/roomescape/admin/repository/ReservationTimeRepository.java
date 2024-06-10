package roomescape.admin.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.admin.dto.SaveReservationTimeRequest;
import roomescape.admin.entity.ReservationTime;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ReservationTime> findAll() {
        return this.jdbcTemplate.query(ReservationTimeQuery.FIND_ALL, readReservationTimeRowMapper());
    }

    public ReservationTime findById(Long id) {
        return this.jdbcTemplate.queryForObject(ReservationTimeQuery.FIND_BY_ID, readReservationTimeRowMapper(),id);
    }

    private RowMapper<ReservationTime> readReservationTimeRowMapper() {
        return (resultSet, rowNum) -> mapToReservationTime(resultSet);
    }

    private ReservationTime mapToReservationTime(ResultSet rs) throws SQLException {
        long id = rs.getLong("time_id");
        String startAt = rs.getString("time_start_at");

        return ReservationTime.of(id, startAt);
    }

    public Long save(SaveReservationTimeRequest saveReservationTimeRequest) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcTemplate.update(con -> saveReserveTimeStatement(con, saveReservationTimeRequest), keyHolder);
        return keyHolder.getKey().longValue();
    }

    private PreparedStatement saveReserveTimeStatement(Connection con, SaveReservationTimeRequest saveReservationTimeRequest) throws SQLException {
        String startAt = saveReservationTimeRequest.startAt();
        PreparedStatement ps = con.prepareStatement(ReservationTimeQuery.SAVE, new String[]{"id"});
        ps.setString(1, startAt);

        return ps;
    }

    public void delete(Long id) {
        this.jdbcTemplate.update(ReservationTimeQuery.DELETE, id);
    }

}