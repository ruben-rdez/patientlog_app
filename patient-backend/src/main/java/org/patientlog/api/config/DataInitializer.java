package org.patientlog.api.config;

import org.patientlog.api.model.Role;
import org.patientlog.api.model.User;
import org.patientlog.api.repository.RoleRepository;
import org.patientlog.api.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner init(RoleRepository roleRepo, UserRepository userRepo, BCryptPasswordEncoder encoder) {
        return args -> {
            Role adminRole = roleRepo.findByName("ADMIN");
            if (adminRole == null) {
                adminRole = roleRepo.save(new Role("ADMIN"));
            }

            Role userRole = roleRepo.findByName("CONSULTOR");
            if (userRole == null) {
                userRole = roleRepo.save(new Role("CONSULTOR"));
            }

            if (userRepo.findByUsername("admin").isEmpty()) {
                User admin = new User("admin", encoder.encode("admin123"));
                admin.setRoles(Set.of(adminRole));
                userRepo.save(admin);
            }

            if (userRepo.findByUsername("consultor").isEmpty()) {
                User consultor = new User("consultor", encoder.encode("consultor123"));
                consultor.setRoles(Set.of(userRole));
                userRepo.save(consultor);
            }
        };
    }
}
