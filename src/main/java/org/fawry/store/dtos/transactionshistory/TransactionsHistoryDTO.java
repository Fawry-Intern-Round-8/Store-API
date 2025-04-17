package org.fawry.store.dtos.transactionshistory;


import lombok.*;
import org.fawry.store.entities.TransactionType;

import java.util.Date;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionsHistoryDTO {
    private Long transactionId;
    private Long storeId;
    private Long productId;
    private int oldQuantity;
    private int newQuantity;
    private TransactionType transactionType;
    private String consumerEmail;
    private Date createdAt;

}
