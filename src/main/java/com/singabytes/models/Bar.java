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
    
    private String ticker;
    private String epoch;
    private String timestamp;
    private String period;
    private double open;
    private double high;
    private double low;
    private double close;
    private long volume;
    private String metadata;

    public Bar() {};
    
    public Bar(String ticker, String timestamp, String epochTimestamp, 
            String period, double open, double high, double low, double close,
            long volume, String metadata) {
        this.ticker = ticker;
        this.timestamp = timestamp;
        this.epoch = epochTimestamp;
        this.period = period;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
        this.metadata = metadata;
    }
    
    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getEpoch() {
        return epoch;
    }

    public void setEpoch(String epochTimestamp) {
        this.epoch = epochTimestamp;
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
    
    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }
    
    @Override
    public String toString() {
        return "Bar{" + "id=" + id + ", ticker=" + ticker + ", "
                + "epoch=" + epoch + ", timestamp=" + timestamp + 
                ", period=" + period + ", open=" + open + ", high=" + high + 
                ", low=" + low + ", close=" + close + ", volume=" + volume + 
                 ", metadata=" + metadata + '}';
    }
}
