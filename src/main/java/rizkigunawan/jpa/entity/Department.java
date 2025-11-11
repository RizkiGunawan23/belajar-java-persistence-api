package rizkigunawan.jpa.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "departments")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Department {
    
    @EmbeddedId
    private DepartmentId id;

    private String name;
    
}
