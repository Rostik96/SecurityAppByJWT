package com.rost.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rost.models.Person;

@Repository
public interface UserRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByUsername(String username);
}
