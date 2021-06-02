package uor.fot.canteenMS.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uor.fot.canteenMS.repositories.UserRepository;

@Service
public class DashboardService {
    @Autowired
    private UserRepository userRepository;
    public int getActiveUsers() {
        return userRepository.getActiveUsers();
    }
}
