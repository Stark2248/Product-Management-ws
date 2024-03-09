package com.silverlining.productmanagement.models;


import jakarta.persistence.*;

@Entity
@Table(name = "Warehouse")
public class Warehouse {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "serialId", referencedColumnName = "serialId")
    private Products product;

    @Column(nullable = false)
    private int quantity;


    @Column(name = "location")
    private String location;

    public Warehouse(){}

    public Warehouse(Products product, int quantity, String location) {
        this.product = product;
        this.quantity = quantity;
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
