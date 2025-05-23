package com.signimus.employee2.Service;

import com.signimus.employee2.Entity.User;
import com.signimus.employee2.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.signimus.employee2.Exception.CustomException.*;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserServiceInterface {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        log.info("Creating user {}", user);
        user.setId(null);
        if(user.getEmail() != null && userRepository.existsByEmail(user.getEmail())) {
            log.error("User with email {} already exists", user.getEmail());
            throw new UserAlreadyExistsException("User with email " + user.getEmail() + " already exists");
        }
        log.info("Saving user {}", user);
        return userRepository.save(user);
    }

    @Override
    public List<User> createUsers(List<User> users) {
        log.info("Creating {} users", users.size());
        for(User user : users) {
            if(user.getEmail() != null && userRepository.existsByEmail(user.getEmail())) {
                log.error("User with email {} already exists", user.getEmail());
                throw new UserAlreadyExistsException("User with email " + user.getEmail() + " already exists");
            }
        }
        log.info("Saving {} users", users.size());
        return userRepository.saveAll(users);
    }

    @Override
    public List<User> getAllUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        log.info("Fetching user with id {}", id);
        return userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("User with id " + id + " not found"));
    }

    @Override
    public User updateUser(Long id, User user) {
        log.info("Updating user {}", user);
        Optional<User> existingUser = userRepository.findById(id);
        if(!existingUser.isPresent()) {
            log.error("User with id {} not found", id);
            throw new UserNotFoundException("User with id " + id + " not found");
        }
        existingUser.get().setFirstName(user.getFirstName());
        existingUser.get().setLastName(user.getLastName());
        existingUser.get().setEmail(user.getEmail());
        existingUser.get().setPassword(user.getPassword());
        existingUser.get().setRole(user.getRole());
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        log.info("Deleting user with id {}", id);
        Optional<User> existingUser = userRepository.findById(id);
        if(!existingUser.isPresent()) {
            log.error("User with id {} not found", id);
            throw new UserNotFoundException("User with id " + id + " not found");
        }
        log.info("deleting user");
        userRepository.deleteById(id);
    }

    @Override
    public List<User> findByAge(Integer minAge, Integer maxAge) {
        log.info("Fetching users with age between {} and {}", minAge, maxAge);
        if(minAge == null){
            minAge = 0;
        }
        if(maxAge == null){
            maxAge = Integer.MAX_VALUE;
        }
        return userRepository.findByAge(minAge, maxAge);
    }

    @Override
    public List<User> findByFirstName(String firstName) {
        log.info("Fetching user with first name {}", firstName);
        List<User> users = userRepository.findByFirstName(firstName);
        if(users.isEmpty()) {
            log.error("User with first name {} not found", firstName);
            throw new UserNotFoundException("User with first name " + firstName + " not found");
        }
        log.info("returning user with first name {}", firstName);
        return userRepository.findByFirstName(firstName);
    }
}
