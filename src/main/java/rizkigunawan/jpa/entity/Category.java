package rizkigunawan.jpa.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rizkigunawan.jpa.entity.helper.CreatedAtAware;
import rizkigunawan.jpa.entity.helper.CreatedAtListener;
import rizkigunawan.jpa.entity.helper.UpdatedAtAware;
import rizkigunawan.jpa.entity.helper.UpdatedAtListener;

@Entity
@Table(name = "categories")
@EntityListeners({ CreatedAtListener.class, UpdatedAtListener.class })
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Category implements CreatedAtAware, UpdatedAtAware {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Override
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}
