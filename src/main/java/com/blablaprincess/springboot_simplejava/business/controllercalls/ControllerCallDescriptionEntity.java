package com.blablaprincess.springboot_simplejava.business.controllercalls;

import com.blablaprincess.springboot_simplejava.business.common.persistence.UuidEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.time.OffsetDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ControllerCallDescriptionEntity extends UuidEntity {

    @Transient
    public static final int MAX_RESPONSE_LENGTH = 255;

    private String mapping;

    @Column(length = MAX_RESPONSE_LENGTH)
    private String response;

    private OffsetDateTime timestamp;

}
