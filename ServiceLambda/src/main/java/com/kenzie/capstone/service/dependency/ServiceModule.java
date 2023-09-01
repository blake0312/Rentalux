package com.kenzie.capstone.service.dependency;

import com.kenzie.capstone.service.ReservationService;
import com.kenzie.capstone.service.dao.reservationDao;

import dagger.Module;
import dagger.Provides;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Module(
    includes = DaoModule.class
)
public class ServiceModule {

    @Singleton
    @Provides
    @Inject
    public ReservationService provideLambdaService(@Named("ExampleDao") reservationDao reservationDao) {
        return new ReservationService(reservationDao);
    }
}

