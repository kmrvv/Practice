package ru.komarov.springtask.task.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.komarov.springtask.task.confing.MyUserDetails;
import ru.komarov.springtask.task.entity.Goods;
import ru.komarov.springtask.task.service.GoodsService;
import ru.komarov.springtask.task.service.OrdersService;


@AllArgsConstructor
@Controller
public class GoodsController {
    private final GoodsService goodsService;
    private final OrdersService ordersService;

    @GetMapping("/goods")
    public String viewHomePage(@AuthenticationPrincipal MyUserDetails userDetails, Model model) {
        model.addAttribute("listOfGoods", goodsService.getAllGoods());
        model.addAttribute("order", ordersService.createOrder());
        model.addAttribute("userDetails", userDetails);
        return "goods/goodsPage";
    }

    @GetMapping("salesman/newGoods")
    public String showNewGoodsForm(Model model) {
        model.addAttribute("goods", goodsService.createGoods());
        return "salesman/newGoods";
    }

    @GetMapping("salesman/FormUpdateGoods/{id}")
    public String showFormForUpdate(@PathVariable("id") Long id, Model model) {
        model.addAttribute("goods", goodsService.getGoodsById(id));
        return "salesman/goodsUpdate";
    }

    @PostMapping("salesman/updateGoods")
    public String updateGoods(@ModelAttribute("goods") Goods goods) {
        goodsService.save(goods);
        return "redirect:/salesman";
    }

    @PostMapping("salesman/saveGoods")
    public String saveGoods(@AuthenticationPrincipal MyUserDetails userDetails, @ModelAttribute("goods") Goods goods,
    Model model) {
        model.addAttribute("userDetails", userDetails);
        goodsService.createGoods(goods, userDetails);
        return "redirect:/salesman";
    }

    @GetMapping("salesman")
    public String showSalesman(@AuthenticationPrincipal MyUserDetails userDetails, Model model) {
        model.addAttribute("goodsList", goodsService.getSalesmanGoods(userDetails.getUserId()));
        return "salesman/listOfGoods";
    }

    @PostMapping("salesman/deleteGoods/{id}")
    public String deleteOrder(@PathVariable("id") long id) {
        goodsService.deleteGoods(id);
        return "redirect:/salesman";
    }
}
