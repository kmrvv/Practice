package ru.komarov.springtask.task.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.komarov.springtask.task.confing.MyUserDetails;
import ru.komarov.springtask.task.entity.Orders;
import ru.komarov.springtask.task.service.OrdersService;

@Slf4j
@AllArgsConstructor
@Controller
public class OrderController {
    private final OrdersService ordersService;

    @GetMapping("cart")
    public String showCard(@AuthenticationPrincipal MyUserDetails userDetails, Model model) {
        model.addAttribute("c", ordersService.getUserActiveGoods(userDetails.getUserId()));
        model.addAttribute("history", ordersService.getUserHistory(userDetails.getUserId()));
        model.addAttribute("dates", ordersService.getDateOfPurchase(userDetails.getUserId()));
        return "orders/cart";
    }

    @PostMapping("cart/saveOrder")
    public String saveOrder(@AuthenticationPrincipal MyUserDetails userDetails, @ModelAttribute("order") Orders orders) {
        ordersService.setOrder(userDetails, orders, true);
        ordersService.saveOrder(orders);
        return "redirect:/cart";
    }

    @PostMapping("cart/deleteOrder/{index}")
    public String deleteOrder(@AuthenticationPrincipal MyUserDetails userDetails,@PathVariable("index") int index) {
        ordersService.deleteOrder(userDetails, index);
        return "redirect:/cart";
    }

    @PostMapping("cart/deleteCart")
    public String deleteCart(@AuthenticationPrincipal MyUserDetails userDetails) {
        ordersService.deleteCart(userDetails);
        return "redirect:/cart";
    }

    @PostMapping("cart/buyCart")
    public String buyCart(@AuthenticationPrincipal MyUserDetails userDetails) {
        ordersService.buyCard(userDetails);
        return "redirect:/cart";
    }
}
