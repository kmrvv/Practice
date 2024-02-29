package ru.komarov.springtask.task.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.komarov.springtask.task.confing.MyUserDetails;
import ru.komarov.springtask.task.entity.Goods;
import ru.komarov.springtask.task.entity.Orders;
import ru.komarov.springtask.task.service.GoodsService;
import ru.komarov.springtask.task.service.OrdersService;


@AllArgsConstructor
@Controller
public class GoodsController {
    private final GoodsService goodsService;
    private final OrdersService ordersService;

    @GetMapping("/goods")
    public String viewHomePage(@AuthenticationPrincipal MyUserDetails userDetails, Model model) {
        Orders orders = ordersService.createOrder();
        model.addAttribute("listOfGoods", goodsService.getAllGoods());
        model.addAttribute("order", ordersService.createOrder());
        return "goods/goodsPage";
    }

    @GetMapping("goods/newGoods")
    public String showNewGoodsForm(Model model) {
        model.addAttribute("goods", goodsService.createGoods());
        return "goods/newGoods";
    }

    @PostMapping("goods/saveGoods")
    public String saveGoods(@ModelAttribute("goods") Goods goods) {
        goodsService.createGoods(goods);
        return "redirect:/goods";
    }
}
