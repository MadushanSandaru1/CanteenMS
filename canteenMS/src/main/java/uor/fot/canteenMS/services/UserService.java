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
}
