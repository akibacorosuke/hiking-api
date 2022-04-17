package root.repositoy;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import root.entity.BookingEntity;

public interface BookingRepository extends JpaRepository<BookingEntity, UUID> {

}
