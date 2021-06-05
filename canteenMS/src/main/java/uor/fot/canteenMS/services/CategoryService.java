package uor.fot.canteenMS.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uor.fot.canteenMS.entities.Category;
import uor.fot.canteenMS.entities.Product;
import uor.fot.canteenMS.repositories.CategoryRepository;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductService productService;

    public List<Category> getAllCategories()
    {
        return categoryRepository.getAllCategories();
    }

    public String getCategoryName(Integer id)
    {
        Product product = productService.getProduct(id);
        Integer cat_id = product.getCategory_id();
        Category category = categoryRepository.findById(cat_id).get();
        return  category.getType();
    }
}
