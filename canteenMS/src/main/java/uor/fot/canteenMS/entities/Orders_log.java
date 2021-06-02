package uor.fot.canteenMS.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Orders_log {

    @Id
    @GeneratedValue
    private Integer id;
    private Integer item_id;
    private Integer user_id;
    private Integer quantity;
    private Float total_price;
    private LocalDateTime ordered_at;
    private LocalDateTime delivered_at;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getItem_id() {
        return item_id;
    }

    public void setItem_id(Integer item_id) {
        this.item_id = item_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getTotal_price() {
        return total_price;
    }

    public void setTotal_price(Float total_price) {
        this.total_price = total_price;
    }

    public LocalDateTime getOrdered_at() {
        return ordered_at;
    }

    public void setOrdered_at(LocalDateTime ordered_at) {
        this.ordered_at = ordered_at;
    }

    public LocalDateTime getDelivered_at() {
        return delivered_at;
    }

    public void setDelivered_at(LocalDateTime delivered_at) {
        this.delivered_at = delivered_at;
    }
}
