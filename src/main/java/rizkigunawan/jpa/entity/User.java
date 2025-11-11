package rizkigunawan.jpa.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class User {
    
    @Id
    private String id;

    private String name;

    @OneToOne
    @PrimaryKeyJoinColumn(
        name = "id",
        referencedColumnName = "id"
    )
    private Credential credential;

    @OneToOne(mappedBy = "user")
    private Wallet wallet;

    @ManyToMany
    @JoinTable(
        name = "users_like_products",
        joinColumns = @JoinColumn(
                name = "user_id", 
                referencedColumnName = "id"
            ),
        inverseJoinColumns = @JoinColumn(
                name = "product_id",
                referencedColumnName = "id"
            )
    )
    private List<Product> likes;

}
