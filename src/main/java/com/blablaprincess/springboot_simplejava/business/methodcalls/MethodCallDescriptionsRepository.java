package com.blablaprincess.springboot_simplejava.business.methodcalls;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.UUID;

public interface MethodCallDescriptionsRepository extends JpaRepository<MethodCallDescriptionEntity, UUID>,
        QuerydslPredicateExecutor<MethodCallDescriptionEntity> {
}
