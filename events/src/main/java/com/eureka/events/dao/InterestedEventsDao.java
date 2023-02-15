package com.eureka.events.dao;

import com.eureka.events.model.InterestedEventModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InterestedEventsDao extends CrudRepository<InterestedEventModel, InterestedEventModel.IEPrimaryKey> {

    @Query(value = "Select * from interested_events where event_id =:eventId and user_id =:userId", nativeQuery = true)
    InterestedEventModel getModel(@Param(value = "eventId") String eventId, @Param(value = "userId") Integer userId);

}
