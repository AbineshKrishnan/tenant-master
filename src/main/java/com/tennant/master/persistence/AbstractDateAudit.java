package com.tennant.master.persistence;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public abstract class AbstractDateAudit {

    @Id
    private UUID id;

    private Boolean isDeleted = false;

    private Instant createdAt;

    private Instant updatedAt;

    private UUID createdBy;

    private UUID updatedBy;

    public void setUpdates(Instant updatedAt, UUID updatedBy) {
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
    }

    public void setCreated(Instant createdAt, UUID createdBy) {
        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }

}
