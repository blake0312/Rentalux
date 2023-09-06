package com.kenzie.appserver.repositories;

import com.kenzie.appserver.repositories.model.VehicleRecord;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface RentalRepository extends CrudRepository<VehicleRecord, String> {
}
