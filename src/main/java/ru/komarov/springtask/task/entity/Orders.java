package ru.komarov.springtask.task.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Random;

@Entity
@Table(name = "Orders")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;
    @Column(name = "userID")
    private long userID;

    @Column(name = "orderID")
    private long orderID;

    @Column(name = "goodsID")
    private long goodsID;

//    @Column(name = "countOfGoods")
//    private long countOfGoods;

    @Column(name = "Status")
    private Boolean status;
//    @Column(name = "dateOfPurchase")
//    private String dateOfPurchase;
//
//    @Column(name = "dateOfReceiving")
//    private String dateOfReceiving;

    public Orders(long userID, long goodsID, long orderID) {
        this.userID = userID;
        this.orderID = orderID;
        this.goodsID = goodsID;
//        this.countOfGoods = 0;
        this.status = true;
//        this.dateOfPurchase = dateOfPurchase;
//        this.dateOfReceiving = dateOfReceiving;
    }

    public Orders(long userID, long orderID) {
        this.userID = userID;
        this.orderID = orderID;
        this.status = true;
    }
}
