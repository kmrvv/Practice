package ru.komarov.springtask.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.komarov.springtask.task.entity.User;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
//    @Query("SELECT  FROM User u WHERE u.email = ?1")
//    List<User> findAll();
    Optional<User> findByEmail(String email);
}
