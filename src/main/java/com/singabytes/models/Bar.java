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
public class Bar {
  
    @Id
    private String id;
    
    private String instrument;
    private String epochTimestamp;
    private String timestamp;
    private String period;
    private Double open;
    private Double high;
    private Double low;
    private Double close;

    public Bar() {};
    
    public Bar(String instrument, String epochTimestamp, String timestamp, 
            String period, Double open, Double high, Double low, Double close) {
        this.instrument = instrument;
        this.epochTimestamp = epochTimestamp;
        this.timestamp = timestamp;
        this.period = period;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
    }

    
    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public String getEpochTimestamp() {
        return epochTimestamp;
    }

    public void setEpochTimestamp(String epochTimestamp) {
        this.epochTimestamp = epochTimestamp;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Double getOpen() {
        return open;
    }

    public void setOpen(Double open) {
        this.open = open;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public Double getLow() {
        return low;
    }

    public void setLow(Double low) {
        this.low = low;
    }

    public Double getClose() {
        return close;
    }

    public void setClose(Double close) {
        this.close = close;
    }

    @Override
    public String toString() {
        return "Bar{" + "id=" + id + ", instrument=" + instrument + ", "
                + "epochTimestamp=" + epochTimestamp + ", timestamp=" + timestamp + 
                ", period=" + period + ", open=" + open + ", high=" + high + 
                ", low=" + low + ", close=" + close + '}';
    }
    
    
            
            
  
}
