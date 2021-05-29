package uor.fot.canteenMS.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uor.fot.canteenMS.entities.Inventory;

@Repository
public interface InventoryRepository extends CrudRepository<Inventory, Integer>{
}
