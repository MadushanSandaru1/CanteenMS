package uor.fot.canteenMS.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uor.fot.canteenMS.services.ProfileService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @PostMapping("/saveUpdateProfile")
    public String updateProfile(HttpSession session, HttpServletRequest request,@RequestParam("id_no") String reg, @RequestParam("name") String name, @RequestParam("phone") String phone, @RequestParam("email") String email, @RequestParam("room_id") String room_id)
    {
        List<String> new_session;
        List<String> users = (List<String>) session.getAttribute("USER_SESSION");

        boolean res = profileService.updateProfile(reg,name,phone,email,room_id);
        if (res) {
            new_session = new ArrayList<>();
            new_session.add(users.get(0));
            new_session.add(reg);
            new_session.add(name);
            new_session.add(phone);
            new_session.add(email);
            new_session.add(users.get(5));
            new_session.add(room_id);
            request.getSession().setAttribute("USER_SESSION",new_session);
            return "redirect:/profile?profile_done";
        }
        else
            return "redirect:/profile?profile_error";
    }
}
