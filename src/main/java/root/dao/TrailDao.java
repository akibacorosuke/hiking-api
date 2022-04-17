package root.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import root.entity.TrailEntity;
import root.repositoy.TrailRepositoty;

@Repository
@RequiredArgsConstructor
public class TrailDao {
    
    private final TrailRepositoty trailRepositoty;
    
    public List<TrailEntity> getAllTrails() {
        return trailRepositoty.findAll();
    }
    
    public Optional<TrailEntity> getTrailById(final String trailId) {
        return trailRepositoty.findById(trailId);
    }

}
