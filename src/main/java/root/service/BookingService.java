package root.service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import root.dao.BookingDao;
import root.dao.HikerDao;
import root.dao.TrailDao;
import root.dto.BookingHistoryDto;
import root.entity.BookingEntity;
import root.entity.HikerEntity;
import root.entity.TrailEntity;
import root.request.BookingRequest;
import root.response.BookingResponse;

@RequiredArgsConstructor
@Service
@Slf4j
public class BookingService {
    
    private final BookingDao bookingDao;
    private final HikerDao hikerDao;
    private final TrailDao trailDao;
    
    /**
     * createBooking 
     * @param bookingRequest
     * @return bookingId
     */
    public UUID createBooking(final BookingRequest bookingRequest) {
        if (Objects.isNull(bookingRequest.getTrailId()) 
                || Objects.isNull(bookingRequest.getHikers())
                || Objects.isNull(bookingRequest.getTrailId())) {
            final ResponseStatusException re = new ResponseStatusException(HttpStatus.BAD_REQUEST, "Following information must be provided : hiker id (who is booking), hikers, trailId.");
            log.info(re.getMessage());
            throw re;
        }
        
        final BookingEntity bookingEntity = createBookingEntity(bookingRequest);
        // save booking in db
        final BookingEntity bookedEntity = bookingDao.saveBookingEntity(bookingEntity);
        
        return bookedEntity.getBookingId();
    }
    
    /**
     * get booking be hiker id
     * @param hikerID
     * @return
     */
    public List<BookingHistoryDto> getBookingsByHiker(final UUID hikerID) {
        //log
        final HikerEntity hikerEntity = hikerDao.getHiker(hikerID).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                String.format("hiker : %s is not found.", hikerID)));
        
        return bookingDao.getBookingHistoryDto(hikerEntity.getHikerId());
    }
    
    /**
     * get booking by booking id
     * @param bookingId
     * @return
     */
    public BookingResponse getBooking(final UUID bookingId) {
        //TODO log出す
        final BookingEntity bookingEntity = bookingDao.getBooking(bookingId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                String.format("bookingID : %s is not found.", bookingId)));
        
        final Set<UUID> bookedMembers = 
                bookingEntity.getBookedHikers()
                .stream()
                .map(h -> h.getHikerId())
                .collect(Collectors.toSet());
        
        final BookingResponse bookingResponse 
            = new BookingResponse(
                bookingEntity.getBookingId(),
                bookingEntity.getHiker().getHikerId(), 
                bookingEntity.getTrail().getTrailId(),
                bookedMembers,
                bookingEntity.getCreationDateTime());

        return bookingResponse;
    }
    
    /**
     * cancel booking
     * @param bookingID
     */
    public void cancelBooking(final UUID bookingID) {
        //check if input bookingId exists
        final BookingEntity bookingEntity = bookingDao.getBooking(bookingID).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                String.format("bookingID : %s is not found.", bookingID)));
        
        //TODO check if status is to be held
        
        bookingEntity.setStatus("03");
                
        bookingDao.cancelBooking(bookingEntity);
    }
    
    /**
     * create booking entity
     * @param bookingRequest
     * @return
     */
    private BookingEntity createBookingEntity(final BookingRequest bookingRequest) {
        final Set<HikerEntity> bookedHikers = new HashSet<>();
        // get hikers to be booked
        bookingRequest.getHikers().forEach(h -> {
            final HikerEntity hikerEntity = hikerDao.getHiker(h).orElseThrow(() -> {
                final ResponseStatusException exep = new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        String.format("hiker : %s is not found.", h));
                log.info(exep.getMessage());
                return exep;
            });
            bookedHikers.add(hikerEntity);
        });
        // get booked by hiker entity
        final HikerEntity bookedByHiker = hikerDao.getHiker(bookingRequest.getBookedByHiker()).orElseThrow(() -> {
            final ResponseStatusException exep = new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("hiker : %s is not found.", bookingRequest.getBookedByHiker()));
            log.info(exep.getMessage());
            return exep;
        });
        // get a trail
        final TrailEntity trail = trailDao.getTrailById(bookingRequest.getTrailId()).orElseThrow(() -> {
            final ResponseStatusException exep = new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("trailId : %s is not found.", bookingRequest.getTrailId()));
            log.info(exep.getMessage());
            return exep;
        });
        return new BookingEntity(bookedByHiker, trail, bookedHikers);
    }

}
