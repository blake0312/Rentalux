package com.kenzie.appserver.repositories;

import com.kenzie.appserver.repositories.model.RentalRecord;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface RentalRepository extends CrudRepository<RentalRecord, String> {
}
