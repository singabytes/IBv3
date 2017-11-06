package com.singabytes.ibv3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Apple
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String path = "//Users//Apple//NetBeansProjects//data//2016//";
        
        // First read files in folder
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        
        List<String> results = new ArrayList<String>();
        
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                results.add(listOfFiles[i].getName());
            } else if (listOfFiles[i].isDirectory()) {
                // Do nothing
            }
        }
        
        // Secod read content of files and dump to Mongo
        MongoUtils mongo = MongoUtils.createConnectionInstance();
        for (String csvFile : results) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(path + csvFile));
                String line;
                while ((line = br.readLine()) != null) {
                    String[] bar = line.split(",");
                    
                    // Set timestamp in UTC in DB
                    LocalDateTime ldt = LocalDateTime.parse(bar[0], DateTimeFormatter.ofPattern("yyyyMMdd  HH:mm:ss"));
                    ZoneId singaporeZoneId = ZoneId.of("Asia/Singapore");
                    ZonedDateTime asiaZoneDateTime = ldt.atZone(singaporeZoneId);
                    
                    ZoneId utcZoneId = ZoneId.of("UTC");
                    ZonedDateTime utcZoneDateTime = asiaZoneDateTime.withZoneSameInstant(utcZoneId);
                    bar[0] = utcZoneDateTime.toString();
                    
                    mongo.writeToDb(bar);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        mongo.queryFromDb();
    }
}