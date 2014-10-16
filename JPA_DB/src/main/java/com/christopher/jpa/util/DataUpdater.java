package com.christopher.jpa.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.christopher.jpa.entitis.Version;

public class DataUpdater {

    private static EntityManagerFactory factory;
    private static EntityManager        manager;

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

    public static void update(Object entity) {
        openConnection();

        manager.merge(entity);
        manager.getTransaction().commit();

        closeConnection();
    }

    public static void deleteVersion(Version entity) {
        openConnection();
        Version object = manager.find(Version.class, entity.getId());
        manager.remove(object);
        manager.getTransaction().commit();
        closeConnection();
    }

    public static void insert(Object entity) {
        openConnection();
        manager.persist(entity);
        manager.getTransaction().commit();
        closeConnection();
    }
}
