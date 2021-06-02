package uor.fot.canteenMS.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uor.fot.canteenMS.entities.Login;
import uor.fot.canteenMS.entities.User;
import uor.fot.canteenMS.services.LoginServices;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class LoginController {

    @Autowired
    LoginServices loginServices;


    @PostMapping("/loginAccount")
    public String login(@RequestParam("username") String user_name, @RequestParam("pwd") String password, HttpServletRequest request, HttpSession session)
    {
        int i,flag=0;
        User user = loginServices.isUserValid(user_name);
        ArrayList<Login> login = loginServices.isPasswordValid(password);

        //session
        List<String> users = (List<String>) request.getSession().getAttribute("USER_SESSION");

        if(Objects.nonNull(user) && Objects.nonNull(login)){
            for (i=0;i<login.size();i++)
            {
                if (user.getId() == login.get(i).getId()) {
                    flag =1;
                    break;
                }
                else
                    flag=0;
            }

            if(flag ==1)
            {
                if (users == null)
                {
                    users = new ArrayList<>();
                    request.getSession().setAttribute("USER_SESSION",users);
                }
                users.add(user.getId().toString());
                users.add(user.getRegistered_no());
                users.add(user.getName());
                users.add(user.getMobile());
                users.add(user.getEmail());
                users.add(user.getRole_id().toString());
                users.add(user.getRoom_no());

                //set newly created session values to session array
                request.getSession().setAttribute("USER_SESSION",users);
                if(Integer.parseInt(users.get(5)) == 1)
                    return "redirect:/dashboard";
                else if(Integer.parseInt(users.get(5)) == 2)
                    return "redirect:/dashboard";
                else
                    return "redirect:/profile";
            }
            else
                return "redirect:/login?login_error";
        }
        else
        {
            return "redirect:/login?login_error";
        }
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request)
    {
        request.getSession().invalidate();
        return "redirect:/";
    }
}
