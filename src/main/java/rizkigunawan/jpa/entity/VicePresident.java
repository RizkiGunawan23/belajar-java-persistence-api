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
@DiscriminatorValue("VICE_PRESIDENT")
// Mengguanakan SuperBuilder daripada builder agar bisa digunakan di child class.
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class VicePresident extends Employee {
    
    @Column(name = "total_manager")
    private Integer totalManager;

}
