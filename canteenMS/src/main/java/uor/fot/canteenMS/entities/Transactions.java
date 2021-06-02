package uor.fot.canteenMS.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Transactions {

    @Id
    private LocalDateTime transaction_date;
    private Float total_amount;

}

