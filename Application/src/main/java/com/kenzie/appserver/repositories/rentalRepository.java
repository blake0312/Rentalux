package com.kenzie.appserver.repositories;

import com.kenzie.appserver.repositories.model.vehicleRecord;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface rentalRepository extends CrudRepository<vehicleRecord, String> {
}
