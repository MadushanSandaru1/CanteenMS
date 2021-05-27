package uor.fot.canteenMS.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uor.fot.canteenMS.entities.User;

@Repository
public interface UserRepository extends CrudRepository<User,Integer> {
    @Query(value = "SELECT u FROM User u WHERE u.registered_no=?1 AND u.is_deleted=0")
    User findByRegId(String id);
}
