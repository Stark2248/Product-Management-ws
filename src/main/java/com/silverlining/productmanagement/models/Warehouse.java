package com.silverlining.productmanagement.models;


import jakarta.persistence.*;

@Entity
@Table(name = "Warehouse")
public class Warehouse {

    @OneToOne(mappedBy = "Products", cascade = CascadeType.ALL)
    private String serialId;

    @Column(nullable = false)
    private int quantity;

    public String getSerialId() {
        return serialId;
    }

    public void setSerialId(String serialId) {
        this.serialId = serialId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
