package com.nabil.managers.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.nabil.managers.entity.User;
import com.nabil.managers.repository.UserRepository;

@Configuration
public class DataSeeder {
    
    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository){
        return args -> {
            if(userRepository.findByUsername("admin")==null){
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                String hashedPassword = encoder.encode("admin123");
                String hashedPassword2 = encoder.encode("admin123");

                User admin = new User("admin",hashedPassword);
                userRepository.save(admin);

                User user1 = new User("user1",hashedPassword2);
                userRepository.save(user1);
                
                System.out.println(">>> Admin was created with username: admin and password: admin123");
            }else{
                System.out.println(">>> Admin user already exist, skipping seeding");
            }
        };
    }
}
