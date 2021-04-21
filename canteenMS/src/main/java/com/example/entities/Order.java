package com.example.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Order {
    @Id
    private Integer order_id;
    private Integer customer_id;
    private String item_type;
    private Integer item_id;
    private Integer item_qty;
    private LocalDateTime ordered_at;

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public Integer getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Integer customer_id) {
        this.customer_id = customer_id;
    }

    public String getItem_type() {
        return item_type;
    }

    public void setItem_type(String item_type) {
        this.item_type = item_type;
    }

    public Integer getItem_id() {
        return item_id;
    }

    public void setItem_id(Integer item_id) {
        this.item_id = item_id;
    }

    public Integer getItem_qty() {
        return item_qty;
    }

    public void setItem_qty(Integer item_qty) {
        this.item_qty = item_qty;
    }

    public LocalDateTime getOrdered_at() {
        return ordered_at;
    }

    public void setOrdered_at(LocalDateTime ordered_at) {
        this.ordered_at = ordered_at;
    }
}
