/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.singabytes.marketdata;

import co.alphavantage.AlphaVantageConnector;
import co.alphavantage.TimeSeries;
import co.alphavantage.input.timeseries.OutputSize;
import co.alphavantage.output.AlphaVantageException;
import co.alphavantage.output.timeseries.Daily;
import co.alphavantage.output.timeseries.data.StockData;
import com.singabytes.models.Bar;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author singafx
 */
public class AlphavantageMD {
    private final Logger logger = LoggerFactory.getLogger(AlphavantageMD.class);
    
    public AlphavantageMD() {
    }
    
    public List<Bar> getMarketData(String ticker, String period) {
        String apiKey = "E0E1ZLFRM7WG1X0R";
        int timeout = 30000;
        AlphaVantageConnector apiConnector = new AlphaVantageConnector(apiKey, timeout);
        TimeSeries stockTimeSeries = new TimeSeries(apiConnector);

        List<Bar> bars = new ArrayList<>();

        try {
            //IntraDay response = stockTimeSeries.intraDay("MSFT", Interval.ONE_MIN, OutputSize.COMPACT);
            Daily response = stockTimeSeries.daily(ticker, OutputSize.COMPACT);
            Map<String, String> metaData = response.getMetaData();
            logger.info("Information: " + metaData.get("1. Information"));
            logger.info("Stock: " + metaData.get("2. Symbol"));
            logger.info("Timezone: " + metaData.get("5. Time Zone"));
      
            List<StockData> stockData = response.getStockData();
            stockData.forEach(stock -> {
            //System.out.println("*****************************");
            //System.out.println("date:   " + stock.getDateTime());
            //System.out.println("open:   " + stock.getOpen());
            //System.out.println("high:   " + stock.getHigh());
            //System.out.println("low:    " + stock.getLow());
            //System.out.println("close:  " + stock.getClose());
            //System.out.println("volume: " + stock.getVolume());
            //System.out.println("*****************************");
            
            // date stuff
            //Test : LocalDateTime ldt = LocalDateTime.parse( "2016-04-04T08:00" );
            //Test : ZoneId zoneId = ZoneId.of("US/Eastern"); 
            LocalDateTime ldt = stock.getDateTime();
            String timezone = metaData.get("5. Time Zone");
            ZoneId zoneId = ZoneId.of(timezone);
            ZoneId zoneIdUTC = ZoneId.of("UTC");
            ZonedDateTime zdtUTC = ldt.atZone( zoneId ).withZoneSameInstant(zoneIdUTC);
            String date = zdtUTC.toString();
            
            String epoch = String.valueOf(zdtUTC.toEpochSecond());
            String periodTxt = "daily"; //todo
            double open = stock.getOpen();
            double high = stock.getHigh();
            double low = stock.getLow();
            double close = stock.getClose();
            long volume = stock.getVolume();
            String comment = ldt.toString() + "[" + timezone + "]";
            
            Bar bar = new Bar(ticker, date, epoch, periodTxt, open, high, low, close, volume, comment);
            bars.add(bar);
        });
        } catch (AlphaVantageException e) {
            logger.error("something went wrong : " + e.getMessage());
        }
   return bars;
  }
}
