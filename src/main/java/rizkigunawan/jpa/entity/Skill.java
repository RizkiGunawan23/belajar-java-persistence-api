package rizkigunawan.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "skills")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Skill {
    
    @Id
    private Integer id;

    private String name;

    private String value;

}
