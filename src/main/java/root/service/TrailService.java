package root.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import root.dao.TrailDao;
import root.entity.TrailEntity;

@Service
@RequiredArgsConstructor
public class TrailService {
    
    private final TrailDao trailDao;
    
    public List<TrailEntity> getAllTrails() {
        final List<TrailEntity> trailEntities = trailDao.getAllTrails();
        if (trailEntities.isEmpty()) {
            new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    String.format("trailId : %s is not found.", trailEntities));
        }
        return trailEntities;
    }
    
    public TrailEntity getTrailById(final String trailId) {
        final TrailEntity trailEntity = trailDao.getTrailById(trailId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        String.format("trailId : %s is not found.", trailId)));
        return trailEntity;
    }
}
