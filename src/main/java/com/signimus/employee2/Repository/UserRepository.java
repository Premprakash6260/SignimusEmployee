package com.signimus.employee2.Repository;

import com.signimus.employee2.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
//    method to get user by firstName
    List<User> findByFirstName(String firstName);

//    method to get users by age range using @query and jpql
    @Query("Select u from User u where u.age >= :minAge and u.age <= :maxAge order by u.age asc")
    List<User> findByAge(@Param("minAge") int minAge, @Param("maxAge") int maxAge);


//    List<User> findByAgeBetween(Integer minAge, Integer maxAge);

    //    method to check if email already exists in database
    boolean existsByEmail(String email);

//    method to return list of users by firstName and using custom jpql query
    @Query("Select u from User u where u.firstName = :firstName")
    List<User> findByFirstNameCustom(@Param("firstName") String firstName);
}

