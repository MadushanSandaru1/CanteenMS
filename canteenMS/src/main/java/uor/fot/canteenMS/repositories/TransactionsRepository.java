package uor.fot.canteenMS.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uor.fot.canteenMS.entities.Transactions;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Repository
public interface TransactionsRepository extends CrudRepository<Transactions, LocalDateTime> {
    //get last transactions
    @Query(value = "call get_last_transactions_amount()",nativeQuery = true)
    Transactions getTransaction();
}
