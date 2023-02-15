package com.eureka.events.dao;

import com.eureka.events.model.EventModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EventsDao extends CrudRepository<EventModel, String> {

    @Query(value = "Select * from events where id =:eventId", nativeQuery = true)
    EventModel getById(@Param(value = "eventId") String eventId);

}
