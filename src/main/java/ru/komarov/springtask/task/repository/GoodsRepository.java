package ru.komarov.springtask.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.komarov.springtask.task.entity.Goods;
import ru.komarov.springtask.task.entity.Orders;

import java.util.Collection;

@Repository
public interface GoodsRepository extends JpaRepository<Goods, Long> {
    Collection<Goods> findBySalesmanID(long salesmanID);
}
