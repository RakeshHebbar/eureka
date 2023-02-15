package com.eureka.eventsuggestion.dao;

import com.eureka.eventsuggestion.dto.LatLong;
import org.hibernate.SessionFactory;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PopularEventsDao {

    private static final int MIN_MILES = 100;
    private static final int MIN_INTERESTED_EVENTS = 5; // TODO: 02/01/2023 change after testing

    @Autowired
    private final SessionFactory sessionFactory;

    public PopularEventsDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @SuppressWarnings(value = "unchecked")
    public List<String> getEventsBasedOnPopularity(LatLong latLong) {
        return sessionFactory.getCurrentSession().createSQLQuery("SELECT   eventid\n" +
                        "FROM     (\n" +
                        "                  SELECT   ie.event_id   AS eventid,\n" +
                        "                           count(*) AS event_count\n" +
                        "                  FROM     interested_events ie\n" +
                        "                  JOIN     venue v ON  v.event_id = ie.event_id\n" +
                        "                  WHERE    (\n" +
                        "                                    point(:long, :lat) <@> point(LONGITUDE\\:\\: DOUBLE PRECISION, LATITUDE\\:\\: DOUBLE PRECISION)\n" +
                        "                           ) <= :minMiles\n" +
                        "                  GROUP BY eventid\n" +
                        "                  HAVING   count(*) >= :minEvents\n" +
                        "        ) _events;")
        .addScalar("eventId", StringType.INSTANCE)
                .setParameter("lat", latLong.getLatitude())
                .setParameter("long", latLong.getLongitude())
                .setParameter("minMiles", MIN_MILES)
                .setParameter("minEvents", MIN_INTERESTED_EVENTS)
                .getResultList();
    }

}
