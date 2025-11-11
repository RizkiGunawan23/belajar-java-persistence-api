package rizkigunawan.jpa.entity.helper;

import java.time.LocalDateTime;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class UpdatedAtListener {
    
    @PrePersist
    @PreUpdate
    public void setLastUpdatedAt(UpdatedAtAware entity) {
        entity.setUpdatedAt(LocalDateTime.now());
    }

}
