package com.example.order_system.bootstrap;

import com.example.order_system.controller.AuthController;
import com.example.order_system.domain.Restaurant;
import com.example.order_system.domain.Role;
import com.example.order_system.domain.User;
import com.example.order_system.repository.RestaurantRepository;
import com.example.order_system.repository.RoleRepository;
import com.example.order_system.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@Component
public class DatabaseLoader implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private RestaurantRepository restaurantRepository;

    private Map<String, User> users = new HashMap<>();

    public DatabaseLoader(UserRepository userRepository, RoleRepository roleRepository, RestaurantRepository restaurantRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public void run(String... args) {

        // add users and roles
        addUsersAndRoles();

    }

    private void addUsersAndRoles() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String secret = "{bcrypt}" + encoder.encode("password");

        Role userRole = new Role("ROLE_USER");
        roleRepository.save(userRole);
        Role adminRole = new Role("ROLE_ADMIN");
        roleRepository.save(adminRole);

        User user = new User("user@gmail.com",secret,true,"Joe","User","joedirt");
        user.addRole(userRole);
        user.setConfirmPassword(secret);
        userRepository.save(user);
        users.put("user@gmail.com",user);

        User user2 = new User("user2@gmail.com",secret,true,"Joe","User","joedirt11");
        user2.addRole(userRole);
        user2.setConfirmPassword(secret);
        userRepository.save(user2);
        users.put("user2@gmail.com",user2);

        User admin = new User("admin@gmail.com",secret,true,"Joe","Admin","masteradmin");
        admin.setAlias("joeadmin");
        admin.setConfirmPassword(secret);
        admin.addRole(adminRole);
        userRepository.save(admin);
        users.put("admin@gmail.com",admin);

        User master = new User("super@gmail.com",secret,true,"Super","User","superduper");
        master.addRoles(new HashSet<>(Arrays.asList(userRole,adminRole)));
        master.setConfirmPassword(secret);
        userRepository.save(master);
        users.put("super@gmail.com",master);

        Restaurant restaurant1 = new Restaurant("Orgada Burger", "The best burger ever!", "Nablus");
        restaurantRepository.save(restaurant1);

        Restaurant restaurant2 = new Restaurant("Pizza House", "Several Italian dishes, Yummy Taste!", "Ramallah");
        restaurantRepository.save(restaurant2);

        logger.info("finished loading data");
    }

}