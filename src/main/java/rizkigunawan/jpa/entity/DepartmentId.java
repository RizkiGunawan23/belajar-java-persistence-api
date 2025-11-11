package rizkigunawan.jpa.entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DepartmentId implements Serializable {
    
    private String companyId;

    private String departmentId;

}
