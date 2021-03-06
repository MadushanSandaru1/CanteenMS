package uor.fot.canteenMS.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uor.fot.canteenMS.entities.Login;
import uor.fot.canteenMS.entities.User;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Repository
public interface LoginRepository extends CrudRepository<Login,Integer> {
    //get login details based on password
    @Query(value = "SELECT l FROM Login l WHERE l.password=?1 ")
    ArrayList<Login> findByPwd(String pwd);

    // update user password procedure
    @Transactional
    @Procedure(procedureName = "profile_password_update")
    void userPasswordUpdate(String reg,String old_pwd,String new_pwd);
}
