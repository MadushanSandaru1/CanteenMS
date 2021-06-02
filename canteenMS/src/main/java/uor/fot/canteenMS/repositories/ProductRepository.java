package uor.fot.canteenMS.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uor.fot.canteenMS.entities.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product,Integer> {

    //insert product procedure
    @Query(value = "{CALL product_create(?1,?2)}",nativeQuery = true)
    int productCreate(String product_name,Integer product_category_id);
}
