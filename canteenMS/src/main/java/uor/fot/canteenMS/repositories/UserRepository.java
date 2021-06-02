package uor.fot.canteenMS.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uor.fot.canteenMS.entities.User;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Repository
public interface UserRepository extends CrudRepository<User,Integer> {

    //These is use for validate a particular user(login)
    @Query(value = "SELECT u FROM User u WHERE u.registered_no=?1 AND u.is_deleted=0")
    User findByRegId(String id);

    //These is use for get last user id
    @Query(value = "SELECT u FROM User u order by u.id  DESC ")
    ArrayList<User> findLastID();

    //Calling Stored Procedures

    //insert user procedure
    @Query(value = "{CALL user_account_create(?1,?2,?3,?4)}",nativeQuery = true)
    int userAccountCreate(Integer id,String user_registered_no, String name, Integer user_role_id);

    //delete user procedure
    @Query(value = "{CALL user_account_delete(?1)}",nativeQuery = true)
    void userAccountDelete(Integer id);

    @Transactional
    @Procedure(procedureName = "user_account_delete")
    void userAccountDeleted(Integer id);

}
