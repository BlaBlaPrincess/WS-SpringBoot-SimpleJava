package com.blablaprincess.springboot_simplejava.business.controllercalls;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.UUID;

public interface ControllerCallDescriptionsRepository extends JpaRepository<ControllerCallDescriptionEntity, UUID>,
        QuerydslPredicateExecutor<ControllerCallDescriptionEntity> {
}
