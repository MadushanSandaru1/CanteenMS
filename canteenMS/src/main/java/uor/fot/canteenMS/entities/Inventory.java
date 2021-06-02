package uor.fot.canteenMS.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Inventory {

    @Id
    @GeneratedValue
    private Integer id;
    private Integer product_id;
    private Integer quantity;
    private Float unit_price;
    private LocalDateTime inserted_at;
    private LocalDateTime expiry_at;
    private Integer is_deleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
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

    public void setUnit_plrice(Float unit_price) {
        this.unit_price = unit_price;
    }

    public LocalDateTime getInserted_at() {
        return inserted_at;
    }

    public void setInserted_at(LocalDateTime inserted_at) {
        this.inserted_at = inserted_at;
    }

    public LocalDateTime getExpiry_at() {
        return expiry_at;
    }

    public void setExpiry_at(LocalDateTime expiry_at) {
        this.expiry_at = expiry_at;
    }

    public Integer getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(Integer is_deleted) {
        this.is_deleted = is_deleted;
    }
}
