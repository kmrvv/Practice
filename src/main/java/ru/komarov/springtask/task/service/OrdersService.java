package ru.komarov.springtask.task.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.Order;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import ru.komarov.springtask.task.confing.MyUserDetails;
import ru.komarov.springtask.task.entity.Goods;
import ru.komarov.springtask.task.entity.Orders;
import ru.komarov.springtask.task.entity.User;
import ru.komarov.springtask.task.repository.GoodsRepository;
import ru.komarov.springtask.task.repository.OrdersRepository;
import ru.komarov.springtask.task.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class OrdersService {
    private OrdersRepository ordersRepository;
    private GoodsRepository goodsRepository;

    public void saveOrder(Orders orders) {
        ordersRepository.save(orders);
    }

    public Orders createOrder(){
        return new Orders();
    }

    public void setOrder(@AuthenticationPrincipal MyUserDetails userDetails, Orders orders, boolean status) {
        Collection<Orders> ordersUser = getUserActiveOrders(userDetails.getUserId());

        if (ordersUser.isEmpty()) {
            Collection<Orders> ordersList = ordersRepository.findAll();
            if (ordersList.isEmpty()) {
                orders.setUserID(userDetails.getUserId());
                orders.setStatus(status);
                orders.setOrderID(1);
            } else {
                long maxOrderId = ordersList.stream()
                        .map(Orders::getOrderID)
                        .max(Long::compare).get();
                orders.setUserID(userDetails.getUserId());
                orders.setStatus(status);
                orders.setOrderID(++maxOrderId);
            }
        } else {
            Orders order = ordersUser.iterator().next();
            orders.setUserID(userDetails.getUserId());
            orders.setStatus(status);
            orders.setOrderID(order.getOrderID());
        }
    }

    public void deleteOrder(@AuthenticationPrincipal MyUserDetails userDetails, int index) {
        List<Orders> ordersUser = (List<Orders>) getUserActiveOrders(userDetails.getUserId());
        ordersRepository.deleteById(ordersUser.get(index-1).getId());
    }

    public void deleteCart(@AuthenticationPrincipal MyUserDetails userDetails) {
        Collection<Orders> ordersUser = getUserActiveOrders(userDetails.getUserId());

        if (!ordersUser.isEmpty()) {
            for (Orders order : ordersUser) {
                ordersRepository.deleteById(order.getId());
            }
        }
    }

    public void buyCard(@AuthenticationPrincipal MyUserDetails userDetails) {
        Collection<Orders> ordersUser = getUserActiveOrders(userDetails.getUserId());

        if (!ordersUser.isEmpty()) {
            LocalDateTime now = LocalDateTime.now();
            String formattedDateTime = now.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
            for (Orders order : ordersUser) {
                order.setStatus(false);
                order.setDateOfPurchase(formattedDateTime);
                ordersRepository.save(order);
            }
        }
    }

    public List<String> getDateOfPurchase(Long userId) {
        Collection<List<Orders>> userOrders = getUserInactiveOrders(userId);

        List<String> dates = userOrders.stream()
                .map(orderList -> {
                    Optional<Orders> firstOrder = orderList.stream().findFirst();
                    return firstOrder.map(Orders::getDateOfPurchase).orElse("");
                })
                .collect(Collectors.toList());
        Collections.reverse(dates);

        return dates;
    }

    public Collection<Goods> getUserActiveGoods(Long userId) {
        Collection<Orders> orders = getUserActiveOrders(userId);
        return orders.stream()
                .map(order -> goodsRepository.findById(order.getGoodsID())
                        .orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public Collection<Orders> getUserActiveOrders(Long userId) {
        Collection<Orders> allOrders = ordersRepository.findByUserID(userId);
        return allOrders.stream()
                .filter(order -> order.getStatus())
                .collect(Collectors.toList());
    }

    public List<List<Goods>> getUserHistory(Long userId) {
        Collection<List<Orders>> userOrders = getUserInactiveOrders(userId);

        List<List<Goods>> goodsLists = userOrders.stream()
                .map(orderList -> orderList.stream()
                        .map(order -> goodsRepository.findById(order.getGoodsID())
                                .orElse(null))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
        Collections.reverse(goodsLists);

        return goodsLists;
    }

    public Collection<List<Orders>> getUserInactiveOrders(Long userId) {
        Collection<Orders> allOrders = ordersRepository.findByUserID(userId);
        return allOrders.stream()
                .filter(order -> !order.getStatus())
                .collect(Collectors.groupingBy(Orders::getOrderID))
                .values();
    }

}

