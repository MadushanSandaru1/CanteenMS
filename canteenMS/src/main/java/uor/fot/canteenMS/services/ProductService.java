package uor.fot.canteenMS.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uor.fot.canteenMS.entities.Product;
import uor.fot.canteenMS.repositories.ProductRepository;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public boolean addProduct(String p_name, Integer p_category) {
        productRepository.productCreate(p_name,p_category);
        return true;
    }

    public List<Product> getAllProducts()
    {
        return productRepository.getAllProducts();
    }

    public boolean deleteProduct(Integer id) {
        productRepository.productDelete(id);
        return true;
    }

    public boolean restoreProduct(Integer id) {
        Product product;
        if (productRepository.findById(id).isPresent())
        {
            product = productRepository.findById(id).get();
            product.setIs_deleted(0);
            productRepository.save(product);
            return true;
        }
        else
            return false;
    }

    public List<Product> getAllActiveProducts()
    {
        return productRepository.getAllActiveProducts();
    }

    public Product getProduct(Integer id)
    {
        return  productRepository.findById(id).get();
    }
}
