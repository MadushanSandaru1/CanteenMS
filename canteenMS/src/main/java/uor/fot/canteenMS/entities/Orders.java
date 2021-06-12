package uor.fot.canteenMS.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer item_id;
    private Integer quantity;
    private Float unit_price;
    private LocalDateTime ordered_at;
    private Integer user_id;
    private Integer is_canceled;

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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(Float unit_price) {
        this.unit_price = unit_price;
    }

    public LocalDateTime getOrdered_at() {
        return ordered_at;
    }

    public void setOrdered_at(LocalDateTime ordered_at) {
        this.ordered_at = ordered_at;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getIs_canceled() {
        return is_canceled;
    }

    public void setIs_canceled(Integer is_canceled) {
        this.is_canceled = is_canceled;
    }
}
