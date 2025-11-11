package rizkigunawan.jpa.entity;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PostLoad;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "members")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Member {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String email;

    private String title;

    @Embedded
    private FullName fullName;

    @Transient
    private String professionalName;

    @PostLoad
    public void postLoad() {
        this.professionalName = String.format("%s %s %s, %s", 
                fullName.getFirstName(), 
                fullName.getMiddleName(), 
                fullName.getLastName(),
                title
            );
    }

}
