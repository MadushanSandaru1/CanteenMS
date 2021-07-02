package uor.fot.canteenMS.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uor.fot.canteenMS.services.UserService;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/saveUser")
    public String addUserAccount(@RequestParam("user_id") String reg_no,@RequestParam("user_name") String name,@RequestParam("user_role") Integer user_role)
    {
        //Integer id = userService.getUserLastID()+1;
        boolean res = userService.addUserAccount(reg_no,name,user_role);
        if(res)
            return  "redirect:/user?done";
        else
            return  "redirect:/user?error";
    }

    @RequestMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") Integer id)
    {
        boolean res = userService.deleteUser(id);
        if(res)
            return  "redirect:/user?delete_done";
        else
            return  "redirect:/user?delete_error";
    }

    @RequestMapping("/restoreUser/{id}")
    public String restoreUser(@PathVariable("id") Integer id)
    {
        boolean res = userService.restoreUser(id);
        if(res)
            return  "redirect:/user?restore_done";
        else
            return  "redirect:/user?restore_error";
    }
}
