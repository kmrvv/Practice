package ru.komarov.springtask.task.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import ru.komarov.springtask.task.confing.MyUserDetails;
import ru.komarov.springtask.task.entity.Goods;
import ru.komarov.springtask.task.repository.GoodsRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class GoodsService {
    private GoodsRepository goodsRepository;

    public Goods createGoods(Goods goods, @AuthenticationPrincipal MyUserDetails userDetails) {
        goods.setSalesmanID(userDetails.getUserId());
        Goods savedGoods = goodsRepository.save(goods);
        return savedGoods;
    }

    public void save(Goods goods) {
        goodsRepository.save(goods);
    }

    public Goods createGoods() {
        return new Goods();
    }

    public Collection<Goods> getSalesmanGoods(Long salesmanID) {
        return goodsRepository.findBySalesmanID(salesmanID);
    }

    public Goods getGoodsById(Long goodsId) {
        Optional<Goods> goods = goodsRepository.findById(goodsId);
        return goods.get();
    }

    public Collection<Goods> getAllGoods() {
        List<Goods> goods = goodsRepository.findAll();
        return new ArrayList<>(goods);
    }

    public void deleteGoods(Long goodsId) {
        goodsRepository.deleteById(goodsId);
    }
}
