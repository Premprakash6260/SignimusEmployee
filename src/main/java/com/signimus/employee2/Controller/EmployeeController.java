package com.signimus.employee2.Controller;

import com.signimus.employee2.Entity.User;
import com.signimus.employee2.Exception.CustomException;
import com.signimus.employee2.Service.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.signimus.employee2.Exception.CustomException.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
public class EmployeeController {
    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/employees/{id}")
    public ResponseEntity<User> getEmployeeById(@PathVariable Long id) {
        log.info("Fetching employee with id: {}", id);
        User user = userService.getUserById(id);
        log.debug("Successfully fetched employee with id: {}", id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/employees")
    public ResponseEntity<List<User>> getAllEmployees() {
        log.info("Fetching all employees");
        List<User> users = userService.getAllUsers();
        log.debug("Successfully fetched {} employees", users.size());
        return ResponseEntity.ok(users);
    }

    @PostMapping("/employees")
    public ResponseEntity<User> createEmployee(@Valid @RequestBody User user) {
        log.info("Creating new employee with email: {}", user.getEmail());
        User createdUser = userService.createUser(user);
        log.info("Successfully created employee with id: {}", createdUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PostMapping("/employees/batch")
    public ResponseEntity<List<User>> createEmployees(@Valid @RequestBody List<User> users) {
        log.info("Creating batch of {} employees", users.size());
        List<User> createdUsers = userService.createUsers(users);
        log.info("Successfully created {} employees in batch", createdUsers.size());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUsers);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<User> updateEmployee(@PathVariable Long id, @Valid @RequestBody User user) {
        log.info("Updating employee with id: {}", id);
        User updatedUser = userService.updateUser(id, user);
        log.info("Successfully updated employee with id: {}", id);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        log.info("Deleting employee with id: {}", id);
        userService.deleteUser(id);
        log.info("Successfully deleted employee with id: {}", id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/employees/get-by-age")
    public ResponseEntity<List<User>> getEmployeesByAge(
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge) {
        log.info("Fetching employees by age range: {}-{}", minAge, maxAge);
        List<User> users = userService.findByAge(minAge, maxAge);
        log.debug("Successfully fetched {} employees by age range: {}-{}", users.size(), minAge, maxAge);
        return ResponseEntity.ok(users);
    }

    @GetMapping("employees/get-by-first-name")
    public ResponseEntity<List<User>> getEmployeesByFirstName(
            @RequestParam(required = false) String firstName
    ) {
        log.info("Fetching employees by first name: {}", firstName);
        if(firstName == null || firstName.isEmpty()) {
            log.error("First name is required");
            throw new InvalidArgumentException("First name is required");
        }
        List<User> users = userService.findByFirstName(firstName);
        log.debug("Successfully fetched {} employees by first name: {}", users.size(), firstName);
        return ResponseEntity.ok(users);
    }
}
