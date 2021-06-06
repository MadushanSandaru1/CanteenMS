package uor.fot.canteenMS.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uor.fot.canteenMS.repositories.OrdersRepository;

@Service
public class OrdersServices {
    @Autowired
    private OrdersRepository ordersRepository;

    public boolean placeOrder(Integer inventory_id, Integer qty, Float unit_price, Integer user_id) {
        ordersRepository.addInventory(inventory_id,qty,unit_price,user_id);
        return true;
    }
}
