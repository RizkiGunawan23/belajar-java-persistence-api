package rizkigunawan.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "transactions_debit")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TransactionDebit extends Transaction {
    
    @Column(name = "debit_amount")
    private Long debitAmount;

}
