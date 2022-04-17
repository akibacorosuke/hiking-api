package root.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import root.entity.TrailEntity;
import root.service.TrailService;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/trails")
public class TrailSearchController {
    
    private final TrailService trailService;
    
    /**
     * View all trails. 
     * @return trailEntities
     */
    @GetMapping
    private List<TrailEntity> getAllTrails() {
      log.info("Giving all trails info to a client.");
      final List<TrailEntity> trailEntities = trailService.getAllTrails();
      trailEntities.forEach(t -> log.info(t.toString()));
      return trailEntities;
    }
    
    /**
     * View selected trail.
     * @param tailId
     * @return trailEntiry
     */
    @GetMapping("/{tailId}")
    private TrailEntity getTrailById(@PathVariable @NonNull final String tailId) {
        log.info(String.format("Giving a trail info to a client. trail   id : %s", tailId));
        final TrailEntity trailEntity = trailService.getTrailById(tailId);
        log.info("selected trail : " + trailEntity.toString());
        return trailEntity;
    }

}
