package root.dao;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import root.dto.BookingHistoryDto;
import root.entity.BookingEntity;
import root.repositoy.BookingRepository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class BookingDao {
    
    private final BookingRepository bookingRepository;
    
    private final NamedParameterJdbcTemplate jdbcTemplate; 
    
    public BookingEntity saveBookingEntity(final BookingEntity bookingEntity) {
        return bookingRepository.save(bookingEntity);
    }
    
    public List<BookingHistoryDto> getBookingHistoryDto(final UUID hikerId) {
        final String sql = ""
                + "select "
                + "BIN_TO_UUID(b.booking_id) as booking_id,"
                + "t.trail_id as trail_id, "
                + "t.start_at as start_at, "
                + "t.end_at as end_at, "
                + "DATE_FORMAT(b.creation_date_time, '%W, %M %e, %Y') as booked_time, "
                + "bh.status as status "
                + "from booking b "
                + "inner join booking_hiker bh "
                + "on bh.booking_id = b.booking_id "
                + "inner join trail t "
                + "on t.trail_id = b.trail_id "
                + "where bh.hiker_id = :hikerId;";

        //convert uuid to binary
        final byte[] uuidBytes = new byte[16];
        ByteBuffer.wrap(uuidBytes).order(ByteOrder.BIG_ENDIAN)
            .putLong(hikerId.getMostSignificantBits())
            .putLong(hikerId.getLeastSignificantBits());
        
        final Map<String, Object> params = new HashMap<>();
        params.put("hikerId", uuidBytes);
        
        return jdbcTemplate.query(sql, params,
                BeanPropertyRowMapper.newInstance(BookingHistoryDto.class));
    }
    
    public Optional<BookingEntity> getBooking(final UUID bookingId) {
        return bookingRepository.findById(bookingId);
    }

    public void cancelBooking(final BookingEntity bookingEntity) {
        final BookingEntity savedBookingEntity = bookingRepository.save(bookingEntity);
        log.debug("booking canceled : " + savedBookingEntity);
    }
}
