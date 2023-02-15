package com.eureka.events.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "events")
@Getter @Setter
@EqualsAndHashCode
public class EventModel {

    @Id
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "event_url")
    private String eventUrl;



}
