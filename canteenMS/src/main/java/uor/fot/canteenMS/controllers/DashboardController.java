package uor.fot.canteenMS.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import uor.fot.canteenMS.services.DashboardService;

@Controller
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    public int getActiveUsers()
    {
        return dashboardService.getActiveUsers();
    }
}
