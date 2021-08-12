package com.blablaprincess.springboot_simplejava.business.controllercalls;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public interface ControllerCallDescriptionsRepository extends JpaRepository<ControllerCallDescriptionEntity, UUID> {
    List<ControllerCallDescriptionEntity> findByTimestampIsBetween(OffsetDateTime from, OffsetDateTime to);
    Page<ControllerCallDescriptionEntity> findByTimestampIsBetween(OffsetDateTime from, OffsetDateTime to, Pageable pageable);
}
