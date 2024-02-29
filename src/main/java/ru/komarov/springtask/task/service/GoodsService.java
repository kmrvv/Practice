package ru.komarov.springtask.task.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.komarov.springtask.task.entity.Goods;
import ru.komarov.springtask.task.repository.GoodsRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GoodsService {
    private GoodsRepository goodsRepository;

    public Goods createGoods(Goods goods) {
        Goods savedGoods = goodsRepository.save(goods);
        return savedGoods;
    }

    public Goods createGoods() {
        return new Goods();
    }

    public Optional<Goods> getGoodsById(Long goodsId) {
        Optional<Goods> goods = goodsRepository.findById(goodsId);
        return goods;
    }

    public Collection<Goods> getAllGoods() {
        List<Goods> goods = goodsRepository.findAll();
        return new ArrayList<>(goods);
    }

    public Goods updateGoods(Goods goods){
        Goods existingGoods = goodsRepository.findById(goods.getId()).get();
        existingGoods.setName(goods.getName());
        existingGoods.setDescription(goods.getDescription());
        existingGoods.setCount(goods.getCount());
        existingGoods.setPrice(goods.getPrice());
        Goods updateGoods = goodsRepository.save(existingGoods);
        return updateGoods;
    }

    public void deleteGoods(Long goodsId) {
        goodsRepository.deleteById(goodsId);
    }
}
