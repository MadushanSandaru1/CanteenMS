package uor.fot.canteenMS.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uor.fot.canteenMS.services.OrdersServices;

@Controller
public class OrdersController {
    @Autowired
    private OrdersServices ordersServices;

    @PostMapping("/orderBakery")
    public String saveBakeryOrders(@RequestParam("acc_id") Integer user_id,@RequestParam("inventory_id") Integer inventory_id,@RequestParam("unit_price") Float unit_price,@RequestParam("qty") Integer qty)
    {
        if(user_id == 0)
            return "redirect:/menu?order_login_error";
        else
        {
            boolean res = ordersServices.placeOrder(inventory_id,qty,unit_price,user_id);
            if(res)
            {
                return "redirect:/menu?order_done";
            }
            else
                return "redirect:/menu?order_error";
        }
    }

    @PostMapping("/orderBeverage")
    public String saveBeverageOrders(@RequestParam("acc_id") Integer user_id,@RequestParam("inventory_id") Integer inventory_id,@RequestParam("unit_price") Float unit_price,@RequestParam("qty") Integer qty)
    {
        if(user_id == 0)
            return "redirect:/menu?order_login_error";
        else
        {
            boolean res = ordersServices.placeOrder(inventory_id,qty,unit_price,user_id);
            if(res)
            {
                return "redirect:/menu?order_done";
            }
            else
                return "redirect:/menu?order_error";
        }
    }

    @PostMapping("/orderChilled")
    public String saveChilledOrders(@RequestParam("acc_id") Integer user_id,@RequestParam("inventory_id") Integer inventory_id,@RequestParam("unit_price") Float unit_price,@RequestParam("qty") Integer qty)
    {
        if(user_id == 0)
            return "redirect:/menu?order_login_error";
        else
        {
            boolean res = ordersServices.placeOrder(inventory_id,qty,unit_price,user_id);
            if(res)
            {
                return "redirect:/menu?order_done";
            }
            else
                return "redirect:/menu?order_error";
        }
    }

    @PostMapping("/orderGrocery")
    public String saveGroceryOrders(@RequestParam("acc_id") Integer user_id,@RequestParam("inventory_id") Integer inventory_id,@RequestParam("unit_price") Float unit_price,@RequestParam("qty") Integer qty)
    {
        if(user_id == 0)
            return "redirect:/menu?order_login_error";
        else
        {
            boolean res = ordersServices.placeOrder(inventory_id,qty,unit_price,user_id);
            if(res)
            {
                return "redirect:/menu?order_done";
            }
            else
                return "redirect:/menu?order_error";
        }
    }

    @PostMapping("/orderMeals")
    public String saveMealsOrders(@RequestParam("acc_id") Integer user_id,@RequestParam("inventory_id") Integer inventory_id,@RequestParam("unit_price") Float unit_price,@RequestParam("qty") Integer qty)
    {
        if(user_id == 0)
            return "redirect:/menu?order_login_error";
        else
        {
            boolean res = ordersServices.placeOrder(inventory_id,qty,unit_price,user_id);
            if(res)
            {
                return "redirect:/menu?order_done";
            }
            else
                return "redirect:/menu?order_error";
        }
    }

    @PostMapping("/orderHouesehold")
    public String saveHouseholdOrders(@RequestParam("acc_id") Integer user_id,@RequestParam("inventory_id") Integer inventory_id,@RequestParam("unit_price") Float unit_price,@RequestParam("qty") Integer qty)
    {
        if(user_id == 0)
            return "redirect:/menu?order_login_error";
        else
        {
            boolean res = ordersServices.placeOrder(inventory_id,qty,unit_price,user_id);
            if(res)
            {
                return "redirect:/menu?order_done";
            }
            else
                return "redirect:/menu?order_error";
        }
    }

    @PostMapping("/orderPharmacy")
    public String savePharmacyOrders(@RequestParam("acc_id") Integer user_id,@RequestParam("inventory_id") Integer inventory_id,@RequestParam("unit_price") Float unit_price,@RequestParam("qty") Integer qty)
    {
        if(user_id == 0)
            return "redirect:/menu?order_login_error";
        else
        {
            boolean res = ordersServices.placeOrder(inventory_id,qty,unit_price,user_id);
            if(res)
            {
                return "redirect:/menu?order_done";
            }
            else
                return "redirect:/menu?order_error";
        }
    }
}
