package uor.fot.canteenMS.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uor.fot.canteenMS.repositories.UserRepository;

@Service
public class ProfileService {

    @Autowired
    private UserRepository userRepository;

    public boolean updateProfile(String reg, String name, String phone, String email, String room_id) {
        userRepository.userProfileUpdate(reg,name,phone,email,room_id);
        return true;
    }
}
