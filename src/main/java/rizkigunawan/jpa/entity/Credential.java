package rizkigunawan.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "credentials")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Credential {
    
    @Id
    private String id;

    private String email;

    private String password;

    @OneToOne(mappedBy = "credential")
    private User user;

}
