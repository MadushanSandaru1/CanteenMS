package uor.fot.canteenMS.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uor.fot.canteenMS.entities.User;
import uor.fot.canteenMS.services.UserService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class Home {

    @Autowired
    UserService userService;

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
        if (users == null)
        {
            model.addAttribute("account","");
            return "tmp_cms/views/menu";
        }
        return "tmp_cms/views/menu";
    }

    @RequestMapping(path = "/orders")
    public  String getOrdersPage(HttpSession session,Model model)
    {
        List<String> users = (List<String>) session.getAttribute("USER_SESSION");
        if (users == null)
        {
            //users = new ArrayList<>();
            return "redirect:/login";
        }
        model.addAttribute("user_details",users);
        return "tmp_cms/views/orders";
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
        List<String> users = (List<String>) session.getAttribute("USER_SESSION");

        if (users == null)
        {
            //users = new ArrayList<>();
            return "redirect:/login";
        }
        model.addAttribute("user_details",users);
        return "tmp_cms/views/dashboard";
    }

    @RequestMapping(path = "/user")
    public  String getUserPage(Model model,HttpSession session)
    {
        List<String> users = (List<String>) session.getAttribute("USER_SESSION");
        List<User> userList = userService.getUsers();
        if (users == null)
        {
            //users = new ArrayList<>();
            return "redirect:/login";
        }
        model.addAttribute("user_details",users);
        model.addAttribute("user_list",userList);
        return "tmp_cms/views/user";
    }

    @RequestMapping(path = "/product")
    public  String getProductPage(HttpSession session,Model model)
    {
        List<String> users = (List<String>) session.getAttribute("USER_SESSION");
        if (users == null)
        {
            //users = new ArrayList<>();
            return "redirect:/login";
        }
        model.addAttribute("user_details",users);
        return "tmp_cms/views/product";
    }

    @RequestMapping(path = "/inventory")
    public  String getInventoryPage(HttpSession session,Model model)
    {
        List<String> users = (List<String>) session.getAttribute("USER_SESSION");
        if (users == null)
        {
            //users = new ArrayList<>();
            return "redirect:/login";
        }
        model.addAttribute("user_details",users);
        return "tmp_cms/views/inventory";
    }

    @RequestMapping(path = "/meal")
    public  String getMealPage(HttpSession session,Model model)
    {
        List<String> users = (List<String>) session.getAttribute("USER_SESSION");
        if (users == null)
        {
            //users = new ArrayList<>();
            return "redirect:/login";
        }
        model.addAttribute("user_details",users);
        return "tmp_cms/views/meal";
    }

    @RequestMapping(path = "/login")
    public  String getLoginPage(HttpSession session)
    {
        return "tmp_cms/views/login";
    }

//    @RequestMapping(path = "/logout")
//    public  String getLogoutPage(HttpSession session)
//    {
//
//        return "tmp_cms/views/logout";
//    }
}
