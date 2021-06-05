package uor.fot.canteenMS.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uor.fot.canteenMS.entities.Inventory;
import uor.fot.canteenMS.repositories.InventoryRepository;

import java.util.List;

@Service
public class MealsService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public List<Inventory> getMealsForOwner()
    {
        return inventoryRepository.getMealsForOwner();
    }
}
