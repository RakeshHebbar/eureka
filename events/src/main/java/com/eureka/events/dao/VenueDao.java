package com.eureka.events.dao;

import com.eureka.events.model.VenueModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VenueDao extends CrudRepository<VenueModel, String> {

    @Query(value = "Select * from venue where id =:venueId", nativeQuery = true)
    VenueModel getById(@Param(value = "venueId") String venueId);
}
