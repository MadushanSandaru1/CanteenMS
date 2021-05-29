package uor.fot.canteenMS.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uor.fot.canteenMS.entities.Orders;

@Repository
public interface OrdersRepository extends CrudRepository<Orders, Integer> {
}
