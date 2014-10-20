package com.christopher.jpa.util;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DataSlecter {

    private static EntityManagerFactory factory;
    private static EntityManager manager;

    private static void openConnection() {
        factory = Persistence.createEntityManagerFactory("persistenceUnit");
        manager = factory.createEntityManager();
        manager.getTransaction().begin();
    }

    private static void closeConnection() {
        if (manager.isOpen())
            manager.close();
        if (factory.isOpen())
            factory.close();
    }

    public static <T> List<T> getList(String name, Class<T> resultClass) {
        openConnection();
        List<T> results = manager.createNamedQuery(name, resultClass).getResultList();
        closeConnection();
        return results;
    }

    public static <T> T find(String id, Class<T> reultClass) {
        openConnection();
        T result = manager.find(reultClass, id);
        closeConnection();
        return result;
    }
}
