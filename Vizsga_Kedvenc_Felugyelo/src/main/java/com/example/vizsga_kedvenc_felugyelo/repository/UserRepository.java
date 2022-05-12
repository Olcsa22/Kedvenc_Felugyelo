package com.example.vizsga_kedvenc_felugyelo.repository;

import com.example.vizsga_kedvenc_felugyelo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer>
{
    @Query(value = "select u from User u where u.username=:username")
    public User findByUsername(@Param("username") String username);

    @Query(value = "select u from User u where u.email=:email")
    public User findByEmail(@Param("email") String email);

    //Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}