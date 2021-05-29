package uor.fot.canteenMS.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uor.fot.canteenMS.entities.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role,Integer> {
}
