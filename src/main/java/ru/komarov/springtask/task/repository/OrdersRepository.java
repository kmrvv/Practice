package ru.komarov.springtask.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.komarov.springtask.task.entity.Orders;
import ru.komarov.springtask.task.entity.User;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
    Collection<Orders> findByUserID(long userID);
}
