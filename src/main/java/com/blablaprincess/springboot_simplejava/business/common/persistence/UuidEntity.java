package com.blablaprincess.springboot_simplejava.business.common.persistence;

import lombok.Data;
import org.hibernate.Hibernate;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Objects;
import java.util.UUID;

@Data
@MappedSuperclass
public class UuidEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj != null && Hibernate.getClass(this) == Hibernate.getClass(obj)) {
            UuidEntity that = (UuidEntity) obj;
            return Objects.equals(this.id, that.getId());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

}
