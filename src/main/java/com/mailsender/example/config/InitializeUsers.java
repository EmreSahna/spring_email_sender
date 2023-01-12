package com.mailsender.example.config;

import com.mailsender.example.entity.User;
import com.mailsender.example.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitializeUsers implements CommandLineRunner {
    private final UserRepository userRepository;

    public InitializeUsers(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        userRepository.deleteAll();

        userRepository.save(User.builder()
            .name("John")
            .mail("john_example@gmail.com")
            .build()
        );

        userRepository.save(User.builder()
            .name("Alice")
            .mail("alice_example@gmail.com")
            .build()
        );
    }
}
