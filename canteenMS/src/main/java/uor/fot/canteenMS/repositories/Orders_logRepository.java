package uor.fot.canteenMS.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uor.fot.canteenMS.entities.Orders_log;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
public interface Orders_logRepository extends CrudRepository<Orders_log, Integer> {

    // add order log  procedure
    @Transactional
    @Procedure(procedureName = "add_orders_log")
    void addOrdersLog(Integer item, Integer user_id, Integer quantity, Float total, Date ordered);
}
