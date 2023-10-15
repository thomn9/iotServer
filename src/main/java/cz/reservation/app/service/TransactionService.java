package cz.reservation.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class TransactionService {
    private final TransactionTemplate transactionTemplate;

    @Autowired
    public TransactionService(PlatformTransactionManager transactionManager){
        transactionTemplate = new TransactionTemplate(transactionManager);
    }

    public void executeTransaction(ITransactionService task){
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                task.transactionExecute();
            }
        });
    }
}
