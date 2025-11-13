package rizkigunawan.jpa.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedNativeQueries;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rizkigunawan.jpa.entity.helper.AuditableUUIDEntity;

@Entity
@Table(name = "brands")
@NamedQueries({
    @NamedQuery(name = "Brand.findAll", query = "SELECT b FROM Brand b"),
    @NamedQuery(name = "Brand.findByName", query = "SELECT b FROM Brand b WHERE b.name = :name")
})
@NamedNativeQueries({
    @NamedNativeQuery(name = "Brand.native.findAll", query = "SELECT * FROM brands", resultClass = Brand.class)
})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Brand extends AuditableUUIDEntity {

    private String name;

    private String description;

    @OneToMany(mappedBy = "brand")
    private List<Product> products;

    @Version
    private Long version;

}
