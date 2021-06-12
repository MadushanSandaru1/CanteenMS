package uor.fot.canteenMS.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uor.fot.canteenMS.entities.Transactions;

import java.time.LocalDateTime;

@Repository
public interface TransactionsRepository extends CrudRepository<Transactions, LocalDateTime> {
    //get letest transactions
    @Query(value = "SELECT total_amount FROM transactions ORDER BY transaction_date DESC LIMIT 1    ",nativeQuery = true)
    float getTransaction();
}
