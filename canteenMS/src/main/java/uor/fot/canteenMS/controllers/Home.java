package uor.fot.canteenMS.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class Home {
    @RequestMapping(path = "/",method = RequestMethod.GET)
    public  String getIndex()
    {
        return "tmp_cms/index";
    }

    @RequestMapping(path = "/menu")
    public String getMenuPage()
    {
        return "tmp_cms/views/menu";
    }

    @RequestMapping(path = "/orders")
    public  String getOrdersPage()
    {
        return "tmp_cms/views/orders";
    }

    @RequestMapping(path = "/profile")
    public  String getProfilePage()
    {
        return "tmp_cms/views/profile";
    }

    @RequestMapping(path = "/dashboard")
    public  String getDashboardPage()
    {
        return "tmp_cms/views/dashboard";
    }

    @RequestMapping(path = "/user")
    public  String getUserPage()
    {
        return "tmp_cms/views/user";
    }

    @RequestMapping(path = "/product")
    public  String getProductPage()
    {
        return "tmp_cms/views/product";
    }

    @RequestMapping(path = "/inventory")
    public  String getInventoryPage()
    {
        return "tmp_cms/views/inventory";
    }

    @RequestMapping(path = "/meal")
    public  String getMealPage()
    {
        return "tmp_cms/views/meal";
    }

    @RequestMapping(path = "/login")
    public  String getLoginPage()
    {
        return "tmp_cms/views/login";
    }

    @RequestMapping(path = "/logout")
    public  String getLogoutPage()
    {
        return "tmp_cms/views/logout";
    }
}
