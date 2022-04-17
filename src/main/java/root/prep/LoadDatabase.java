package root.prep;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;
import root.entity.HikerEntity;
import root.entity.TrailEntity;
import root.repositoy.HikerRepositoty;
import root.repositoy.TrailRepositoty;

@Slf4j
@Configuration
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(final TrailRepositoty trailRepositoty,
            final HikerRepositoty hikerRepositoty) {
        return args -> {
            //TODO execute inserting data with SQL scripts
            log.info("The trails' data are being initialized..");
            log.info("Preloading " + trailRepositoty.save(new TrailEntity("001", "Shire", "07:00", "09:00", new BigDecimal("29.90"))));
            log.info("Preloading " + trailRepositoty.save(new TrailEntity("002", "Gondor", "10:00", "13:00", new BigDecimal("59.90"))));
            log.info("Preloading " + trailRepositoty.save(new TrailEntity("003", "Mordor", "14:00", "19:00", new BigDecimal("99.90"))));
            log.info("Preloading " + hikerRepositoty.save(new HikerEntity(UUID.fromString("4eb28f91-46b0-4629-9662-29451bba591b"), "John", "Lennon", 80, "updater", new Date())));
            log.info("Preloading " + hikerRepositoty.save(new HikerEntity(UUID.fromString("f2601b5e-bd3c-11ec-a860-8cf31db9473a"), "Paul", "Maccartny", 82, "updater", new Date())));
        };
    }

}
