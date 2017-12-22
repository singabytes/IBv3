/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.singabytes.scripts;

import com.singabytes.marketdata.AlphavantageMD;
import com.singabytes.models.Bar;
import com.singabytes.utils.File;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 *
 * @author singafx
 */
@SpringBootApplication
public class GetAlphavantageMD implements CommandLineRunner {
    
    private final Logger logger = LoggerFactory.getLogger(GetAlphavantageMD.class);
    
    @Autowired
    private BarRepository repository;
        
    public static void main(String[] args) throws Exception {
        SpringApplication.run(GetEventsFromFxCalendar.class, args);
    }    
    
    @Override
    public void run(String... args) {
        AlphavantageMD md = new AlphavantageMD();
        //List<String> tickers = File.getTickersFromFile("C:\\Users\\singafx\\ibv3\\IBv3\\src\\main\\java\\com\\singabytes\\data\\nasdaq\\BasicIndustries");
        List<String> tickers = File.getTickersFromFile("C:\\Users\\singafx\\ibv3\\IBv3\\src\\main\\java\\com\\singabytes\\data\\nasdaq\\BasicIndustries");
        tickers.forEach(ticker-> {
            List<Bar> bars = md.getMarketData(ticker, "TODODAILY");
            for (int i=0; i<bars.size(); i++) {
                repository.save(bars.get(i));
            }
        logger.info("Done with pulling market data");
        });
    }
}
