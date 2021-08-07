package com.blablaprincess.springboot_simplejava.business.controllercalls;

import com.blablaprincess.springboot_simplejava.business.common.persistence.UuidEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.Date;

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

    private Date timestamp;

    private static String cropResponseByMaxLength(String response) {
        int length = response.length();
        if (length > MAX_RESPONSE_LENGTH) {
            int half = MAX_RESPONSE_LENGTH / 2;
            return response.substring(0, half - 5) + "… … …" + response.substring(length - half, length - 1);
        }
        return response;
    }

    public void setResponse(String response) {
        this.response = cropResponseByMaxLength(response);
    }

    @SuppressWarnings("FieldCanBeLocal")
    public static class ControllerCallDescriptionEntityBuilder {
        private String response;

        public ControllerCallDescriptionEntityBuilder response(String response) {
            this.response = cropResponseByMaxLength(response);
            return this;
        }
    }

}
