package uor.fot.canteenMS.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uor.fot.canteenMS.entities.*;
import uor.fot.canteenMS.services.*;


import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class Home {

    @Autowired
    private UserService userService;
    @Autowired
    private DashboardController dashboardController;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private MealsService mealsService;
    @Autowired
    private OrdersServices ordersServices;
    @Autowired
    private TransactionService transactionService;

    @RequestMapping(path = "/",method = RequestMethod.GET)
    public  String getIndex(Model model,HttpSession session)
    {
        List<String> users = (List<String>) session.getAttribute("USER_SESSION");
        if (users == null)
        {
            model.addAttribute("account","");
            return "tmp_cms/index";
        }
        model.addAttribute("account",users.get(2));
        return "tmp_cms/index";
    }

    @RequestMapping(path = "/menu")
    public String getMenuPage(HttpSession session,Model model)
    {
        List<String> users = (List<String>) session.getAttribute("USER_SESSION");
        List<Inventory> inventories = inventoryService.getInventoryForCustomers();
        List<Product> products = productService.getAllProducts();

        if (users == null)
        {
            model.addAttribute("user_details",this.getEmptyUsers());
            model.addAttribute("products",products);
            model.addAttribute("category",categoryService);
            model.addAttribute("inventories",inventories);
            model.addAttribute("account","");
            model.addAttribute("account_id","0");
            return "tmp_cms/views/menu";
        }
        else
        {
            model.addAttribute("products",products);
            model.addAttribute("category",categoryService);
            model.addAttribute("account",users.get(2));
            model.addAttribute("account_id",users.get(0));
            model.addAttribute("user_details",users);
            model.addAttribute("inventories",inventories);

            return "tmp_cms/views/menu";
        }
    }

    @RequestMapping(path = "/orders")
    public  String getOrdersPage(HttpSession session,Model model)
    {
        List<String> users = (List<String>) session.getAttribute("USER_SESSION");
        List<Product> products = productService.getAllProducts();
        if (users == null)
        {
            //users = new ArrayList<>();
            return "redirect:/login";
        }
        else
        {
            List<Orders> orders_for_owner = ordersServices.getMyOrdersForOwners();
            List<Orders> orders_for_customers = ordersServices.getMyOrdersForCustomers(users.get(1));
            model.addAttribute("user_details",users);
            model.addAttribute("category",categoryService);
            model.addAttribute("inventory",inventoryService);
            model.addAttribute("orders_customer",orders_for_customers);
            model.addAttribute("orders_owner",orders_for_owner);
            model.addAttribute("products",products);
            return "tmp_cms/views/orders";
        }

    }

    @RequestMapping(path = "/payBills")
    public  String payBills(HttpSession session,Model model)
    {
        List<String> users = (List<String>) session.getAttribute("USER_SESSION");
        List<Product> products = productService.getAllProducts();
        List<User> userList = userService.getUsers();
        if (users == null)
        {
            //users = new ArrayList<>();
            return "redirect:/login";
        }
        else
        {
            List<Orders> orders_for_owner = ordersServices.getMyOrdersForOwners();
            model.addAttribute("user_details",users);
            model.addAttribute("users",userService);
            model.addAttribute("category",categoryService);
            model.addAttribute("user_list",userList);
            model.addAttribute("inventory",inventoryService);
            model.addAttribute("orders_owner",orders_for_owner);
            model.addAttribute("products",products);
            return "tmp_cms/views/pay";
        }

    }

    @RequestMapping(path = "/profile")
    public  String getProfilePage(Model model,HttpSession session)
    {
        List<String> users = (List<String>) session.getAttribute("USER_SESSION");
        if (users == null)
        {
            //users = new ArrayList<>();
            return "redirect:/login";
        }
        model.addAttribute("user_details",users);
        return "tmp_cms/views/profile";
    }

    @RequestMapping(path = "/dashboard")
    public  String getDashboardPage(Model model,HttpSession session)
    {
        String active_users;
        List<String> users = (List<String>) session.getAttribute("USER_SESSION");
        Integer active_user_count = dashboardController.getActiveUsers();
        String transaction = transactionService.getTransactionAmount()+" LKR/-";
        Integer active_products = productService.getActiveProducts();
        String active_product;

        if(active_products < 10)
            active_product="0"+active_products;
        else
            active_product = String.valueOf(active_products);

        if(active_user_count < 10)
            active_users="0"+active_user_count;
        else
            active_users = String.valueOf(active_user_count);

        if (users == null)
        {
            //users = new ArrayList<>();
            return "redirect:/login";
        }
        model.addAttribute("active_products",active_product);
        model.addAttribute("active_users",active_users);
        model.addAttribute("transaction",transaction);
        model.addAttribute("user_details",users);
        return "tmp_cms/views/dashboard";
    }

    @RequestMapping(path = "/user")
    public  String getUserPage(Model model,HttpSession session)
    {
        List<String> users = (List<String>) session.getAttribute("USER_SESSION");
        List<User> userList = userService.getUsers();
        //Integer user_last_id = userService.getUserLastID()+1;
        if (users == null)
        {
            //users = new ArrayList<>();
            return "redirect:/login";
        }
        model.addAttribute("user_details",users);
        model.addAttribute("user_list",userList);
        //model.addAttribute("user_last_id",user_last_id);
        return "tmp_cms/views/user";
    }

    @RequestMapping(path = "/product")
    public  String getProductPage(HttpSession session,Model model)
    {
        List<String> users = (List<String>) session.getAttribute("USER_SESSION");
        List<Category> categories = categoryService.getAllCategories();
        List<Product> products = productService.getAllProducts();
        if (users == null)
        {
            //users = new ArrayList<>();
            return "redirect:/login";
        }
        model.addAttribute("user_details",users);
        model.addAttribute("categories",categories);
        model.addAttribute("products",products);
        return "tmp_cms/views/product";
    }

    @RequestMapping(path = "/inventory")
    public  String getInventoryPage(HttpSession session,Model model)
    {
        List<String> users = (List<String>) session.getAttribute("USER_SESSION");
        List<Product> products = productService.getAllActiveProducts();
        List<Category> categories = categoryService.getAllCategories();
        List<Inventory> inventories = inventoryService.getAllInventory();
        if (users == null)
        {
            //users = new ArrayList<>();
            return "redirect:/login";
        }
        model.addAttribute("user_details",users);
        model.addAttribute("products",products);
        model.addAttribute("category",categoryService);
        model.addAttribute("inventories",inventories);
        return "tmp_cms/views/inventory";
    }

    @RequestMapping(path = "/meal")
    public  String getMealPage(HttpSession session,Model model)
    {
        List<String> users = (List<String>) session.getAttribute("USER_SESSION");
        List<Inventory> meals = mealsService.getMealsForOwner();
        List<Product> products = productService.getAllProducts();
        if (users == null)
        {
            //users = new ArrayList<>();
            return "redirect:/login";
        }
        model.addAttribute("user_details",users);
        model.addAttribute("meals",meals);
        model.addAttribute("products",products);
        return "tmp_cms/views/meal";
    }

    @RequestMapping(path = "/login")
    public  String getLoginPage(HttpSession session)
    {
        return "tmp_cms/views/login";
    }

    public List<String> getEmptyUsers()
    {
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("0");

        return list;
    }
}
