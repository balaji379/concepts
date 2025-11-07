package com.tutorial.excel_batch_application.cofig;

import com.tutorial.excel_batch_application.entity.WeatherReport;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExcelWriter implements ItemWriter<WeatherReport> {

    private static final int BATCH_SIZE = 1000; // tune based on your DB

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public void write(Chunk<? extends WeatherReport> chunk) throws Exception {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        int count = 0;
        entityTransaction.begin();
        try {
            for (WeatherReport weatherReport : chunk.getItems()) {
                entityManager.persist(weatherReport);
                count += 1;
                if (count % BATCH_SIZE == 0) {
                    entityManager.flush();
                    entityManager.clear();
                }
            }
            entityTransaction.commit();
        } catch (Exception e) {
            entityTransaction.rollback();
            throw e;
        } finally {
            entityManager.close();
        }
    }

//    @Override
//    public void write(Chunk<? extends WeatherReport> items) throws Exception {
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        EntityTransaction transaction = entityManager.getTransaction();
//        transaction.begin();
//
//        try {
//            int count = 0;
//            for (WeatherReport report : items) {
//                entityManager.persist(report);
//                count++;
//
//                if (count % BATCH_SIZE == 0) {
//                    entityManager.flush();
//                    entityManager.clear();
//                }
//            }
//            transaction.commit();
//        } catch (Exception e) {
//            transaction.rollback();
//            throw e;
//        } finally {
//            entityManager.close();
//        }
//    }

}
