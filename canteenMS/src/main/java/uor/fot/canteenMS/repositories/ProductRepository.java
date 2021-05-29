package uor.fot.canteenMS.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uor.fot.canteenMS.entities.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product,Integer> {
}
