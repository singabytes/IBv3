/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.singabytes.scripts;

import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.singabytes.models.CalendarEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Apple
 */
//@SpringBootApplication
@SpringBootApplication(scanBasePackages = { "com.singabytes", "com.singabytes.repositories" })
//@EnableJpaRepositories("com.singabytes.repositories")
public class GetEventsFromFxCalendar implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(GetEventsFromFxCalendar.class);
    private BufferedWriter writer = null; 
    
    @Autowired
    private CalendarEventRepository repository;
     
    public static void main(String[] args) throws Exception {
        SpringApplication.run(GetEventsFromFxCalendar.class, args);
    }
    
    @Override
    public void run(String... args) {
        String date = "Oct9.2017";    
        logger.info("Query calendar events for date " + date);
        //List<CalendarEvent> event = queryCalendarEvent(date);
        getAllEventsOnMongo("2017");
        getAllEventsFromMongo();
    }

    private void getAllEventsOnMongo(String year) {
        List<String> month = new ArrayList<>();
        month.add("Jan"); month.add("Feb"); month.add("Mar"); month.add("Apr");
        month.add("May"); month.add("Jun"); month.add("Jul"); month.add("Aug");
        month.add("Sep"); month.add("Oct"); month.add("Nov"); month.add("Dec");
        
        for (int j=0; j<month.size(); j++) {
            for (int i=1; i<32; i++) {
                String date = month.get(j) + i + "." + year;
                logger.info("Retrieving events for date = " + date);
                List<CalendarEvent> event = queryCalendarEvent(date);
                
                for (int e=0; e<event.size(); e++) {
                    // Write event to file   
                    if (writer == null) {
                        try {
                            writer = new BufferedWriter(new FileWriter("CalendarEvents"+year));
                        } catch (IOException ex) {
                            logger.error("Error when creating event file");
                            logger.error(ex.getMessage());
                        }
                    }
                    try {
                        writer.write(event.get(e).toString() + "\n");
                    } catch (IOException ex) {
                            logger.error("Error when writing in event file");
                            logger.error(ex.getMessage());
                    }
                    
                    // Write event to Mongo
                    try {
                        repository.save(event);
                    } catch (Exception ex) {
                        logger.error("Issue when saving calendar event to Mongo");
                        logger.error(ex.getMessage());
                    }
                }
           }
        }
    }
    
    private void getAllEventsFromMongo() {
        System.out.println("Calendar Event found with findAll():");
	System.out.println("-------------------------------");
	for (CalendarEvent event : repository.findAll()) {
            System.out.println(event.toString());
	}
	System.out.println();
    }
    
    
    private List<CalendarEvent> queryCalendarEvent(String eventDate) {

        Document doc = null;
        //String url = "https://www.forexfactory.com/calendar.php?day=nov21.2017";        
        String url = "https://www.forexfactory.com/calendar.php?day=" + eventDate;        
        
        try {
            doc = Jsoup.connect(url).get();
        } catch (Exception e) {
            logger.error("Error getting url " + url);
            logger.error(e.getMessage());
        }
        
        if (doc == null)
            logger.error("Problem : doc is null !");
        
        Elements rows = doc.select("tr");
        List<CalendarEvent> eventArray = new ArrayList<>();
        
        // Variables to remember the previous velue
        // useful for date and time that are not always set
        String date = "";
        String time = "";
            
        for (int i = 0; i < rows.size(); i++) {
            Element row = rows.get(i);
            
            CalendarEvent event = new CalendarEvent();
            
            if (!row.hasClass("calendar__row calendar_row calendar__row--grey calendar__row--new-day newday") &&
                !row.hasClass("calendar__row calendar_row calendar__row--grey") &&
                !row.hasClass("calendar__row calendar_row") && // only date + time + event or only event
                !row.hasClass("calendar__row calendar_row calendar__row--grey calendar__row--no-grid nogrid") &&
                !row.hasClass("calendar__row calendar_row calendar__row--new-day newday") /* 21/11/2017*/ &&
                !row.hasClass("calendar__row calendar_row calendar__row--no-grid nogrid") )
                            continue;
        
            Elements cols = row.select("td");
            logger.debug(row.className());
                
            for (int j = 0; j < cols.size(); j++) {
                
                Element column = cols.get(j);
                
                if (column.hasClass("calendar__cell calendar__date date")) {
                    if (!column.text().isEmpty()) {
                        logger.debug("date = " + column.text());
                        date = column.text(); // store if empty next time
                    } 
                    event.setDate(date);
                }
                if (column.hasClass("calendar__cell calendar__time time")) {
                    if (!column.text().isEmpty()) {
                        logger.debug("time = " + column.text());
                        time = column.text();
                    }
                    event.setTime(time);
                }
                if (column.hasClass("calendar__cell calendar__currency currency"))
                {
                    logger.debug("date = " + column.text());
                    event.setCurrency(column.text());
                }
                if (column.hasClass("calendar__cell calendar__impact impact calendar__impact calendar__impact--low")) {
                    logger.debug("impact low");
                    event.setImpact("low");
                }
                if (column.hasClass("calendar__cell calendar__impact impact calendar__impact calendar__impact--medium")) {
                    logger.debug("impact medium");
                    event.setImpact("medium");
                }
                if (column.hasClass("calendar__cell calendar__impact impact calendar__impact calendar__impact--high")) {
                    logger.debug("impact high");
                    event.setImpact("high");
                }
                if (column.hasClass("calendar__cell calendar__event event")) {
                    logger.debug("event = " + column.text());
                    event.setEvent(column.text());
                }
                if (column.hasClass("calendar__cell calendar__actual actual")) {
                    logger.debug("actual = " + column.text());
                    event.setActual(column.text());
                }
                if (column.hasClass("calendar__cell calendar__forecast forecast")) {
                    logger.debug("actual = " + column.text());
                    event.setForecast(column.text());
                }
                if (column.hasClass("calendar__cell calendar__previous previous")) {
                    logger.debug("previous = " + column.text());
                    event.setPrevious(column.text());
                    
                    List<Node> nodes = column.childNodes();
                    if (!nodes.isEmpty()) {
                        String temp = nodes.get(0).attr("class");
                        if (!temp.isEmpty() && !temp.contains("null")) {
                            logger.info("-> " + temp);
                            event.setRevised(temp);
                        }
                        temp = nodes.get(0).attr("title");
                        if (!temp.isEmpty() && !temp.contains("null")) {
                            logger.info("-> " + temp);
                            event.setRevisedText(temp);
                        }
                    }
                }
            }
            //if (event.isValid) {
            logger.info("Retrieve event : " + event.toString());
            eventArray.add(event);
            //}
            
        }
        return eventArray;
    }
}

