/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.singabytes.scripts;

import com.singabytes.models.CalendarEvent;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


/**
 *
 * @author Apple
 */
@RepositoryRestResource(collectionResourceRel  = "event", path = "event")
public interface CalendarEventRepository extends MongoRepository<CalendarEvent, String> {
    public List<CalendarEvent> findByDate(String date);
}
