package rizkigunawan.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
// Anotasi untuk memberi nilai pada column pembeda di tabel.
@DiscriminatorValue("MANAGER")
// Mengguanakan SuperBuilder daripada builder agar bisa digunakan di child class.
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Manager extends Employee {
    
    @Column(name = "total_employee")
    private Integer totalEmployee;

}
