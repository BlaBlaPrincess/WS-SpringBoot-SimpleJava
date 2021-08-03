package com.blablaprincess.springboot_simplejava.business.common.persistence;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
public class UuidEntity {

    @Id
    @GeneratedValue
    private UUID id;

}
