package com.signimus.employee2.Service;

import com.signimus.employee2.Entity.User;

import java.util.List;

public interface UserServiceInterface {
//    create user method
    User createUser(User user);

//    create multiple users method
    List<User> createUsers(List<User> users);

//    get all users method
    List<User> getAllUsers();

//    get user by id method
    User getUserById(Long id);

//    update user method
    User updateUser(Long id, User user);

//    delete user method
    void deleteUser(Long id);

//    method to get user by age range using @query and jpql
    List<User> findByAge(Integer minAge, Integer maxAge);

//    method to get user by firstName
    List<User> findByFirstName(String firstName);
}
