package root.dao;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import root.entity.HikerEntity;
import root.repositoy.HikerRepositoty;

@Repository
@RequiredArgsConstructor
public class HikerDao {
    
    private final HikerRepositoty hikerRepositoty;
    
    public Optional<HikerEntity> getHiker(final UUID hiker) {
        
        return hikerRepositoty.findById(hiker);
    }
    

}
