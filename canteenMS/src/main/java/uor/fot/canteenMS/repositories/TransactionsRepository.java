package uor.fot.canteenMS.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uor.fot.canteenMS.entities.Transactions;

import java.time.LocalDateTime;

@Repository
public interface TransactionsRepository extends CrudRepository<Transactions, LocalDateTime> {
}
