package com.example.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Food {
    @Id
    private Integer food_id;
    private String food_name;
    private String food_desc;
    private Float food_price;
    private LocalDateTime inserted_at;
    private String is_deleted;

    public Integer getFood_id() {
        return food_id;
    }

    public void setFood_id(Integer food_id) {
        this.food_id = food_id;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public String getFood_desc() {
        return food_desc;
    }

    public void setFood_desc(String food_desc) {
        this.food_desc = food_desc;
    }

    public Float getFood_price() {
        return food_price;
    }

    public void setFood_price(Float food_price) {
        this.food_price = food_price;
    }

    public LocalDateTime getInserted_at() {
        return inserted_at;
    }

    public void setInserted_at(LocalDateTime inserted_at) {
        this.inserted_at = inserted_at;
    }

    public String getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(String is_deleted) {
        this.is_deleted = is_deleted;
    }
}
