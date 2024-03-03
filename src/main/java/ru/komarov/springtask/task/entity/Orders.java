package ru.komarov.springtask.task.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


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

    @Column(name = "Status")
    private Boolean status;

    @Column(name = "dateOfPurchase")
    private String dateOfPurchase;

}
