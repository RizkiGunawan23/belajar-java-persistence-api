package rizkigunawan.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "payments_gopay")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PaymentGopay extends Payment {
    
    @Column(name = "gopay_id")
    private String gopayId;

}
