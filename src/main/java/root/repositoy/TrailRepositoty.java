package root.repositoy;

import org.springframework.data.jpa.repository.JpaRepository;

import root.entity.TrailEntity;

public interface TrailRepositoty extends JpaRepository<TrailEntity, String> {

}
