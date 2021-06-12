package uor.fot.canteenMS.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uor.fot.canteenMS.repositories.TransactionsRepository;

@Service
public class TransactionService {
    @Autowired
    private TransactionsRepository transactionsRepository;

    public float getTransactionAmount()
    {
        return transactionsRepository.getTransaction();
    }
}
