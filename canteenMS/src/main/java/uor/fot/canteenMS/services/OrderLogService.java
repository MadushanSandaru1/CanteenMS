package uor.fot.canteenMS.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uor.fot.canteenMS.entities.Orders;
import uor.fot.canteenMS.entities.Orders_log;
import uor.fot.canteenMS.repositories.Orders_logRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;

@Service
public class OrderLogService {
    @Autowired
    private Orders_logRepository orders_logRepository;
    @Autowired
    private UserService userService;

    private ArrayList<Orders> orders;


    public boolean addOrderLog(ArrayList<Orders> orders) {

        this.orders = orders;
        int i;
        int size = orders.size();

        for(i=0;i<size;i++)
        {
            System.out.println(i+"kjkbhj");
            Orders_log orders_log = new Orders_log();
            orders_log.setItem_id(orders.get(i).getItem_id());
            orders_log.setUser_id(orders.get(i).getUser_id());
            orders_log.setQuantity(orders.get(i).getQuantity());
            orders_log.setTotal_price(orders.get(i).getUnit_price());
            orders_log.setOrdered_at(orders.get(i).getOrdered_at());
            orders_log.setDelivered_at(LocalDateTime.now());
            orders_logRepository.save(orders_log);
            //orders_logRepository.addOrdersLog(order.getItem_id(),order.getUser_id(),order.getQuantity(),order.getUnit_price(), new Date(order.getOrdered_at().toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli()));
        }
        return true;
    }
}
