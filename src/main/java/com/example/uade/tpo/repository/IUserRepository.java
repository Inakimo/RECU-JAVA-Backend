package com.example.uade.tpo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.uade.tpo.entity.Role;
import com.example.uade.tpo.entity.User;



@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByRole(Role role);
    Optional<User> findById(Long id);
    Optional<User> findByUserName(String userName);
}
