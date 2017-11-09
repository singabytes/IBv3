/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.singabytes.scripts;

import com.singabytes.models.CalendarEvent;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Apple
 */
@Repository
public interface CalendarEventRepository extends MongoRepository<CalendarEvent, String> {
    public List<CalendarEvent> findByDate(String date);
}
