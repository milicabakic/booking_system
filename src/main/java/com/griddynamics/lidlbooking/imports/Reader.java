package com.griddynamics.lidlbooking.imports;

import com.griddynamics.lidlbooking.persistance.entity.CountryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Component
public class Reader {

    private final CountryTaskFactory countryTaskFactory;
    private final CityTaskFactory cityTaskFactory;
    @Value("${reader.threads}")
    private int threads;
    @Value("${reader.lines}")
    private int lines;
    private ThreadPoolExecutor executorService;

    private String file;

    @Autowired
    public Reader(final CountryTaskFactory countryTaskFactory, final CityTaskFactory cityTaskFactory) {
        this.cityTaskFactory = cityTaskFactory;
        this.countryTaskFactory = countryTaskFactory;
    }

    public void read() throws IOException {

        Map<String, CountryEntity> insertedCountries = new ConcurrentHashMap<>();
        executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(threads);

        int size = lines / threads;

        startReadingCountries(insertedCountries, size);

        startReadingCities(insertedCountries, size);

    }

    private void startReadingCities(final Map<String, CountryEntity> insertedCountries, final int size)
            throws IOException {
        FileInputStream inputStream = new FileInputStream(file);
        Scanner sc = new Scanner(inputStream, StandardCharsets.UTF_8);
        sc.nextLine();
        while (sc.hasNextLine()) {
            for (int i = 0; i < threads; i++) {
                Queue<String> queue = getQueue(size, sc);
                CityReaderTask cityReaderTask = cityTaskFactory.createTask();
                cityReaderTask.setQueue(queue);
                cityReaderTask.setInsertedCountries(insertedCountries);
                executorService.submit(cityReaderTask);
            }
        }
        inputStream.close();
    }

    private void startReadingCountries(final Map<String, CountryEntity> insertedCountries, final int size)
            throws IOException {
        FileInputStream inputStream = new FileInputStream(file);
        Scanner sc = new Scanner(inputStream, StandardCharsets.UTF_8);
        sc.nextLine();
        while (sc.hasNextLine()) {
            for (int i = 0; i < threads; i++) {
                Queue<String> queue = getQueue(size, sc);
                CountryReaderTask countryReaderTask = countryTaskFactory.createTask();
                countryReaderTask.setQueue(queue);
                countryReaderTask.setInsertedCountries(insertedCountries);
                executorService.submit(countryReaderTask);
            }
        }
        inputStream.close();
    }

    private Queue<String> getQueue(final int size, final Scanner sc) {
        Queue<String> queue = new PriorityQueue<>();
        for (int j = 0; j < size; j++) {
            if (sc.hasNextLine()) {
                queue.add(sc.nextLine());
            } else {
                break;
            }
        }
        return queue;
    }


    public void setFile(final String file) {
        this.file = file;
    }
}
