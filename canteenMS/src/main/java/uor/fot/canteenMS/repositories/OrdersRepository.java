package uor.fot.canteenMS.repositories;

import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uor.fot.canteenMS.entities.Orders;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Repository
public interface OrdersRepository extends CrudRepository<Orders, Integer> {
    //place order procedure
    @Transactional
    @Procedure(procedureName = "place_order")
    void addInventory(Integer inventory_id,Integer qty, Float unit_price, Integer user_id);
}
