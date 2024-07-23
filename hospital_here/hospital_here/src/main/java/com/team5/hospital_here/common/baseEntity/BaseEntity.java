package com.team5.hospital_here.common.baseEntity;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;
import lombok.Getter;

@MappedSuperclass
@Getter
public abstract class BaseEntity {

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    @PrePersist
    public void PerPersist(){
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    public void PerUpdated(){
        updatedAt = LocalDateTime.now();
    }
}
