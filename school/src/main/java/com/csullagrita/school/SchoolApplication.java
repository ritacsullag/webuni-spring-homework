package com.csullagrita.school;

import com.csullagrita.school.service.DbInitService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@RequiredArgsConstructor
@SpringBootApplication
@EnableCaching
//@EnableJpaAuditing
public class SchoolApplication implements CommandLineRunner {
    private final DbInitService dbInitService;


    public static void main(String[] args) {
        SpringApplication.run(SchoolApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        dbInitService.deleteData();
        dbInitService.deleteAudData();
        dbInitService.addInitData();
    }


//    @Bean
//    public FlywayMigrationStrategy repairFlyway() {
//        return flyway -> {
//            // repair each script's checksum
//            flyway.repair();
//            // before new migrations are executed
//            flyway.migrate();
//        };
//    }
}
