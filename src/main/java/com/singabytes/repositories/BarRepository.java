/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.singabytes.repositories;

import com.singabytes.models.Bar;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author Apple
 */
public interface BarRepository extends MongoRepository<Bar, String> {

    public Bar findByInstrument(String instrument);
    public List<Bar> findByPeriod(String period);

}
