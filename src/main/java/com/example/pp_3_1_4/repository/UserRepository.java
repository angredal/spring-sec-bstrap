package com.example.pp_3_1_4.repository;

import com.example.pp_3_1_4.model.User;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Transactional
    @Query("select user from User user join fetch user.roles role where user.email = :email")
    User findUserByEmail(@Param("email") String email);

    @Override
    @Transactional
    @Query("select user from User user join fetch user.roles role where user.id = :id")
    Optional<User> findById(Long id);

}
