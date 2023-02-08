package com.griddynamics.lidlbooking.imports;


import com.griddynamics.lidlbooking.persistance.entity.CityEntity;
import com.griddynamics.lidlbooking.persistance.entity.CountryEntity;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import static com.griddynamics.lidlbooking.commons.StringConstants.CITY_TASK;

@Component(value = CITY_TASK)
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CityReaderTask implements Runnable {


    public static final int THREE = 3;
    public static final int FIFTEEN = 15;
    public static final int THIRTEEN = 13;
    @PersistenceContext
    private EntityManager entityManager;

    private Queue<String> queue;
    private Map<String, CountryEntity> insertedCountries;


    @Override
    @Transactional
    public void run() {
        List<CityEntity> cities = new ArrayList<>();
        while (!queue.isEmpty()) {
            String[] strings = queue.poll().split("\"");
            cities.add(new CityEntity(strings[THREE], strings[FIFTEEN], insertedCountries.get(strings[THIRTEEN])));
        }
        cities.forEach(
                city -> entityManager.persist(city)
        );
        entityManager.flush();
        entityManager.clear();
    }

    public void setQueue(final Queue<String> queue) {
        this.queue = queue;
    }

    public void setInsertedCountries(final Map<String, CountryEntity> insertedCountries) {
        this.insertedCountries = insertedCountries;
    }

}
