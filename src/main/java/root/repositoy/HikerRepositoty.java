package root.repositoy;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import root.entity.HikerEntity;

public interface HikerRepositoty extends JpaRepository<HikerEntity, UUID> {

}
