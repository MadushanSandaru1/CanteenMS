package uor.fot.canteenMS.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uor.fot.canteenMS.entities.Login;
import uor.fot.canteenMS.entities.User;

@Repository
public interface LoginRepository extends CrudRepository<Login,Integer> {
    @Query(value = "SELECT l FROM Login l WHERE l.password=?1 ")
    Login findByPwd(String pwd);
}
