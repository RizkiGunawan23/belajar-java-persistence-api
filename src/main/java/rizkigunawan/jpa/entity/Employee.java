package rizkigunawan.jpa.entity;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import rizkigunawan.jpa.entity.helper.AuditableUUIDEntity;

@Entity
@Table(name = "employees")
// Parent class perlu ada anotasi ini agar nanti ada column pembeda di tabel.
@DiscriminatorColumn(name = "type")
// Parent dan child class perlu ada anotasi ini untuk memberi nilai secara otomatis pada column pembeda.
@DiscriminatorValue("EMPLOYEE")
// Menentukan strategi inheritance, disini menggunakan Single Table Inheritance.
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
// Mengguanakan SuperBuilder daripada builder agar bisa digunakan di class turunannya.
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Employee extends AuditableUUIDEntity {

    private String name;

}
