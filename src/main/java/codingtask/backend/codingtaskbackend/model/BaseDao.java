package codingtask.backend.codingtaskbackend.model;

import java.time.LocalDateTime;

import javax.persistence.*;
import javax.persistence.MappedSuperclass;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@MappedSuperclass
@NoArgsConstructor
public abstract class BaseDao {
    
    @Column(name = "modification_date")
    private LocalDateTime modificationDate;

    @PrePersist
    void onCreate(){
        this.modificationDate = LocalDateTime.now();
    }

    @PreUpdate
    void onUpdate(){
        this.modificationDate = LocalDateTime.now();
    }
}
