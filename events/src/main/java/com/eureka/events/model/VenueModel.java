package com.eureka.events.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.geo.Point;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "venue")
@Getter @Setter
@EqualsAndHashCode
public class VenueModel {

    @Id
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "event_id")
    private String eventId;
    @Column(name = "latitude")
    private String latitude;
    @Column(name = "longitude")
    private String longitude;

}
