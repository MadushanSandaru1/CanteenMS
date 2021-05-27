package uor.fot.canteenMS.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uor.fot.canteenMS.entities.Login;
import uor.fot.canteenMS.entities.User;
import uor.fot.canteenMS.repositories.LoginRepository;
import uor.fot.canteenMS.repositories.UserRepository;

@Service
public class LoginServices {

    @Autowired
    UserRepository userRepository;
    @Autowired
    LoginRepository loginRepository;

    public User isUserValid(String user_name) {
        return userRepository.findByRegId(user_name);
    }

    public Login isPasswordValid(String password)
    {
        return loginRepository.findByPwd(password);
    }
}
