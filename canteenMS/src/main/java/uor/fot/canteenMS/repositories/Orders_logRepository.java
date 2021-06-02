package uor.fot.canteenMS.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uor.fot.canteenMS.entities.Orders_log;

@Repository
public interface Orders_logRepository extends CrudRepository<Orders_log, Integer> {
}
