package ru.komarov.springtask.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.komarov.springtask.task.entity.Goods;

@Repository
public interface GoodsRepository extends JpaRepository<Goods, Long> {
}
