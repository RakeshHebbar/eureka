package com.eureka.events.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "interested_events")
@IdClass(InterestedEventModel.IEPrimaryKey.class)
@Getter @Setter
@EqualsAndHashCode
public class InterestedEventModel {

    @Id
    private String eventId;
    @Id
    private Integer userId;


    public static class IEPrimaryKey implements Serializable {
        protected String eventId;
        protected Integer userId;

        public IEPrimaryKey() {}

        public IEPrimaryKey(String eventId, Integer userId) {
            this.eventId = eventId;
            this.userId = userId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            IEPrimaryKey that = (IEPrimaryKey) o;
            return Objects.equals(eventId, that.eventId) && Objects.equals(userId, that.userId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(eventId, userId);
        }
    }

}
