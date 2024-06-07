package roomescape.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationDto;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ReservationRepositoryImpl implements ReservationRepository{

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ReservationDto> findReservations() {
        String sql = "select id, name, date, time from reservation";

        List<ReservationDto> dto = jdbcTemplate.query(
                        sql, (rs, rowNum) -> Reservation.toEntity(
                                rs.getLong("id"),
                                rs.getString("name"),
                                rs.getString("date"),
                                rs.getString("time"))
                ).stream().map(ReservationDto::new)
                .collect(Collectors.toList());


        return dto;
    }

    @Override
    public Long create(ReservationDto dto) {
        String sql = "insert into reservation(name, date, time) values(?,?,?)";
        Reservation reservation = new Reservation(dto.getName(), dto.getDate(), dto.getTime());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    sql, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate().toString());
            ps.setString(3, reservation.getTime().toString());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public ReservationDto findReservationByid(Long id) {
        String sql = "select id, name, date, time from reservation where id = ?";

        return jdbcTemplate.queryForObject(
                sql, (rs,rowNum) -> {
                        ReservationDto dto = new ReservationDto(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("date"),
                        rs.getString("time")
                );
                return dto;
             }, id);
    }

    @Override
    public void deleteReservation(Long id) {
        String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, Long.valueOf(id));
    }
}
