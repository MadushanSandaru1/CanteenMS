package uor.fot.canteenMS.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uor.fot.canteenMS.services.ProductService;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/saveProduct")
    public String addProduct(@RequestParam("p_name") String p_name,@RequestParam("p_category") Integer p_category)
    {
        boolean res = productService.addProduct(p_name,p_category);
        if(res)
            return "redirect:/product?product_done";
        else
            return "redirect:/product?product_error";
    }

    @RequestMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable("id") Integer id)
    {
        boolean res = productService.deleteProduct(id);
        if(res)
           return  "redirect:/product?product_delete_done";
        else
            return "redirect:/product?product_delete_error";
    }

    @RequestMapping("/restoreProduct/{id}")
    public String redirectProduct(@PathVariable("id") Integer id)
    {
        boolean res = productService.restoreProduct(id);
        if(res)
            return  "redirect:/product?product_restore_done";
        else
            return "redirect:/product?product_restore_error";
    }
}
