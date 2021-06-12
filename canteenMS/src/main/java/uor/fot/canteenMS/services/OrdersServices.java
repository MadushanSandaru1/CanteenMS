package uor.fot.canteenMS.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uor.fot.canteenMS.entities.Orders;
import uor.fot.canteenMS.repositories.OrdersRepository;

import java.util.List;

@Service
public class OrdersServices {
    @Autowired
    private OrdersRepository ordersRepository;

    public boolean placeOrder(Integer inventory_id, Integer qty, Float unit_price, Integer user_id) {
        ordersRepository.addInventory(inventory_id,qty,unit_price,user_id);
        return true;
    }

    public List<Orders> getMyOrdersForCustomers(String reg)
    {
        return ordersRepository.getMyOrdersForCustomers(reg);
    }

    public List<Orders> getMyOrdersForOwners()
    {
        return ordersRepository.getMyOrdersForOwner();
    }

    public boolean canselOrder(Integer id) {
        ordersRepository.cancelOrder(id);
        return true;
    }

    public Orders findOrders(Integer id) {
        return  ordersRepository.findById(id).get();
    }


}
