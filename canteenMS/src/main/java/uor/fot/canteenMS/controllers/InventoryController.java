package uor.fot.canteenMS.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uor.fot.canteenMS.services.InventoryService;

import java.time.LocalDateTime;

@Controller
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/saveInventory")
    public String saveInventory(@RequestParam("p_name") Integer p_id, @RequestParam("p_qty") Integer qty, @RequestParam("p_exp_date")String date,@RequestParam("p_unit_price") Float unit_price)
    {
        LocalDateTime localDateTime = LocalDateTime.parse(date);
        boolean res = inventoryService.addInventory(p_id,qty,unit_price,localDateTime);
        if(res)
            return "redirect:/inventory?inventory_done";
        else
            return "redirect:/inventory?inventory_error";
    }

    @RequestMapping("deleteInventory/{id}")
    public String deleteInventory(@PathVariable("id") Integer id)
    {
        boolean res = inventoryService.deleteInventory(id);
        if(res)
            return "redirect:/inventory?inventory_delete_done";
        else
            return "redirect:/inventory?inventory_delete_error";
    }

    @RequestMapping("restoreInventory/{id}")
    public String restoreInventory(@PathVariable("id") Integer id)
    {
        boolean res = inventoryService.restoreInventory(id);
        if(res)
            return "redirect:/inventory?inventory_restore_done";
        else
            return "redirect:/inventory?inventory_restore_error";
    }
}
