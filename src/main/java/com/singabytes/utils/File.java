/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.singabytes.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author singafx
 */
public class File {
    
    private static Logger logger = LoggerFactory.getLogger(File.class);
    
    public static List<String> getTickersFromFile(String filename) {
        
        List<String> out = new ArrayList<String>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = br.readLine()) != null) {
                out.add(line.split(",")[0]);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return out;
    }
}
