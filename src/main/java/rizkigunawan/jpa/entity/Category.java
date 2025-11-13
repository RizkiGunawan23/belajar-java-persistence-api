package rizkigunawan.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rizkigunawan.jpa.entity.helper.AuditableIdentityEntity;

@Entity
@Table(name = "categories")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Category extends AuditableIdentityEntity<Integer> {

    private String name;

    private String description;

}
