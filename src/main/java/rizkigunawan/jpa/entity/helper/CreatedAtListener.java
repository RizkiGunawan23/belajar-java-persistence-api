package rizkigunawan.jpa.entity.helper;

import java.time.LocalDateTime;

import jakarta.persistence.PrePersist;

public class CreatedAtListener {
    
    @PrePersist
    public void setCreatedAt(CreatedAtAware entity) {
        entity.setCreatedAt(LocalDateTime.now());
    }

}
