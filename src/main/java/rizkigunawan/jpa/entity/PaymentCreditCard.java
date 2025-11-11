package rizkigunawan.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "payments_credit_card")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PaymentCreditCard extends Payment {
    
    @Column(name = "masked_card")
    private String maskedCard;

    private String bank;

}
