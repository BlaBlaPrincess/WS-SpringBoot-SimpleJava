package com.blablaprincess.springboot_simplejava.business.controllercalls;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface ControllerCallDescriptionsRepository extends JpaRepository<ControllerCallDescriptionEntity, UUID> {
    List<ControllerCallDescriptionEntity> findByTimestampIsBetween(Date from, Date to);
    Page<ControllerCallDescriptionEntity> findByTimestampIsBetween(Date from, Date to, Pageable pageable);
}
