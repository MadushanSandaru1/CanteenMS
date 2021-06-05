package uor.fot.canteenMS.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uor.fot.canteenMS.entities.Product;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product,Integer> {

    //insert product procedure
    @Transactional
    @Procedure(procedureName = "product_create")
    void productCreate(String product_name,Integer product_category_id);

    // get all products
    @Query(value = "SELECT p FROM Product p ")
    List<Product> getAllProducts();

    // get all active products
    @Query(value = "SELECT p FROM Product p WHERE p.is_deleted=0")
    List<Product> getAllActiveProducts();

    //delete product procedure
    @Transactional
    @Procedure(procedureName = "product_delete")
    void productDelete(Integer id);
}
