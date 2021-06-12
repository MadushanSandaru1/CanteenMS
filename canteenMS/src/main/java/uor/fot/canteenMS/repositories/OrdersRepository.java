package uor.fot.canteenMS.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uor.fot.canteenMS.entities.Orders;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrdersRepository extends CrudRepository<Orders, Integer> {
    //place order procedure
    @Transactional
    @Procedure(procedureName = "place_order")
    void addInventory(Integer inventory_id,Integer qty, Float unit_price, Integer user_id);

    //my orders for customer procedure
    @Query(value ="CALL myorders_for_customers(?1)",nativeQuery = true)
    List<Orders> getMyOrdersForCustomers(String reg);

    //my orders for owner procedure
    @Query(value ="CALL myorders_for_owner()",nativeQuery = true)
    List<Orders> getMyOrdersForOwner();

    //cancel order procedure
    @Transactional
    @Procedure(procedureName = "cancel_order")
    void cancelOrder(Integer order_id);


}
