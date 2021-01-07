package com.example.exercise_4_internet.repositories;

import com.example.exercise_4_internet.model.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    @Query(value = "SELECT * FROM users t WHERE t.username = ?1", nativeQuery = true)
    Optional<User> findByUsername(String username);

    @Query(value = "DELETE FROM users t WHERE t.username=?1", nativeQuery = true)
    void deleteByUsername(String username);
}
