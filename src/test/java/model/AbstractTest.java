package model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;

public abstract class AbstractTest {
    protected EntityManagerFactory entityManagerFactory;
    protected EntityManager entityManager;

    public void init(String pu) {
        entityManagerFactory = Persistence.createEntityManagerFactory(pu);
        Assert.assertNotNull(entityManagerFactory);
        entityManager = entityManagerFactory.createEntityManager();
        Assert.assertNotNull(entityManager);
    }

    public void begin() {
        entityManager.getTransaction().begin();
    }

    public void commit() {
        entityManager.getTransaction().commit();
    }
    
    public Share save(Share e){
        begin();
        e = entityManager.merge(e);
        commit();
        return e;
    }
    public Shareholder save(Shareholder e){
        begin();
        e = entityManager.merge(e);
        commit();
        return e;
    }
    @AfterMethod
    public void rollbackTransaction() {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }

        if (entityManager.isOpen()) {
            entityManager.close();
        }
    }

    @AfterClass
    public void closeEntityManagerFactory() {
        entityManagerFactory.close();
    }

}
