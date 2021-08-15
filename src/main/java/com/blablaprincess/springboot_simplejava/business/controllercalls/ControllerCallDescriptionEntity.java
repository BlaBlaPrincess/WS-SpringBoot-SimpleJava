package com.blablaprincess.springboot_simplejava.business.controllercalls;

import com.blablaprincess.springboot_simplejava.business.common.persistence.UuidEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.time.LocalDateTime;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ControllerCallDescriptionEntity extends UuidEntity {

    @Transient
    public static final int MAX_RESPONSE_LENGTH = 255;

    private String mapping;

    @Column(length = MAX_RESPONSE_LENGTH)
    private String response;

    private LocalDateTime timestamp;

}
