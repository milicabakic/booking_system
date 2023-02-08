package com.griddynamics.lidlbooking.imports;

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

import static com.griddynamics.lidlbooking.commons.StringConstants.COUNTRY_TASK;


@Component(value = COUNTRY_TASK)
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CountryReaderTask implements Runnable {

    public static final int THIRTEEN = 13;
    public static final int NINE = 9;
    private final List<CountryEntity> countries;
    @PersistenceContext
    private EntityManager entityManager;
    private Queue<String> queue;
    private Map<String, CountryEntity> insertedCountries;

    public CountryReaderTask() {
        this.countries = new ArrayList<>();
    }

    @Override
    @Transactional
    public void run() {
        while (!queue.isEmpty()) {
            String[] strings = queue.poll().split("\"");
            countries.add(new CountryEntity(strings[THIRTEEN], strings[NINE]));
        }

        countries.stream()
                .filter((entry) -> insertedCountries.putIfAbsent(entry.getIso3(), entry) == null)
                .forEach(entity -> entityManager.persist(entity));

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
