package uor.fot.canteenMS.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uor.fot.canteenMS.entities.User;
import uor.fot.canteenMS.repositories.UserRepository;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;


    public List<User> getUsers()
    {
        ArrayList<User> userArrayList = (ArrayList<User>) userRepository.findAll();
        return userArrayList;
    }

    public Integer getUserLastID()
    {
        Integer res=0;
        ArrayList<User> users = userRepository.findLastID();
        User lastUser = users.get(0);
        if (lastUser.getId() > 1)
            res = lastUser.getId();
        else
            res =0;
        return res;
    }

    public boolean addUserAccount(Integer id, String reg, String u_name, Integer user_role) {

        userRepository.userAccountCreate(id,reg,u_name,user_role);
        return true;
    }

    public boolean deleteUser(Integer id) {
        userRepository.userAccountDeleted(id);
        return true;
    }
}
