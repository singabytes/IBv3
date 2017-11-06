/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.singabytes.models;

import org.springframework.data.annotation.Id;

/**
 *
 * @author Apple
 */
public class CalendarEvent {
    
    @Id
    private String id;
    
    private String date;
    private String time;
    private String currency;
    private String impact;
    private String event;
    private String actual;
    private String previous;
    private String forecast;
    private String revised;
    private String revisedText;

    public CalendarEvent() {};
    
    public CalendarEvent(String date, String time, String currency, 
            String impact, String event, String actual, String previous, 
            String forecast, String revised, String revisedText) {
        this.date = date;
        this.time = time;
        this.currency = currency;
        this.impact = impact;
        this.event = event;
        this.actual = actual;
        this.previous = previous;
        this.forecast = forecast;
        this.revised = revised;
        this.revisedText = revisedText;
    }

    
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getImpact() {
        return impact;
    }

    public void setImpact(String impact) {
        this.impact = impact;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getActual() {
        return actual;
    }

    public void setActual(String actual) {
        this.actual = actual;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public String getForecast() {
        return forecast;
    }

    public void setForecast(String forecast) {
        this.forecast = forecast;
    }

    public String getRevised() {
        return revised;
    }

    public void setRevised(String revised) {
        this.revised = revised;
    }
    
    public void setRevisedText(String revised) {
        this.revisedText = revised;
    }

    @Override
    public String toString() {
        return "CalendarEvent{" + "id=" + id + ", date=" + date + 
                ", time=" + time + ", currency=" + currency + 
                ", impact=" + impact + ", event=" + event + 
                ", actual=" + actual + ", previous=" + previous + 
                ", forecast=" + forecast + ", revised=" + revised + 
                ", revisedText=" + revisedText + '}';
    }

    private boolean isValid() {
        // Event must contain date + time + event at least
        if (date.isEmpty() || time.isEmpty() || currency.isEmpty() || event.isEmpty())
            return false;
        return true;
    }
    
    
}

