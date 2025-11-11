package rizkigunawan.jpa.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import rizkigunawan.jpa.entity.helper.CreatedAtAware;
import rizkigunawan.jpa.entity.helper.CreatedAtListener;

@Entity
@Table(name = "transactions")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@EntityListeners({ CreatedAtListener.class })
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Transaction implements CreatedAtAware {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private Long balance;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Override
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
