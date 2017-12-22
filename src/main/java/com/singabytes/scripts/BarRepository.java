/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.singabytes.scripts;

import com.singabytes.models.Bar;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 *
 * @author Apple
 */
@RepositoryRestResource(collectionResourceRel = "marketdata", path = "marketdata")
public interface BarRepository extends MongoRepository<Bar, String> {
    public Bar findByTicker(@Param("ticker") String ticker);    
    
    public Bar findmyticker(@Param("ticker") String ticker) {
        Bar b = new Bar();

    }
}



