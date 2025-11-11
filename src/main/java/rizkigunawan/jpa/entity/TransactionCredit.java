package rizkigunawan.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "transactions_credit")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TransactionCredit extends Transaction {
    
    @Column(name = "credit_amount")
    private Long creditAmount;

}
