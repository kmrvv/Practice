package ru.komarov.springtask.task.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "goods")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Goods {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "salesmanID")
    private long salesmanID;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private float price;

    public Goods(String name, long salesmanID, String description, float price) {
        this.name = name;
        this.salesmanID = salesmanID;
        this.description = description;
        this.price = price;
    }
}
