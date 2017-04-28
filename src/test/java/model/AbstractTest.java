package model;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

/**
 * Abstract Class for all Testsuites for multiple persistence units.
 * 
 * @author benny.kaiser
 *
 */
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

	@BeforeClass
	private void clearDatabase() throws SQLException {
		// Disable FK
		Query q = entityManager.createQuery("SET REFERENTIAL_INTEGRITY FALSE");
		q.executeUpdate();

		// Find all tables and truncate them
		List<String> tables = entityManager
				.createQuery("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES  where TABLE_SCHEMA='PUBLIC'",
						String.class)
				.getResultList();
		for (String table : tables) {
			entityManager.createQuery("TRUNCATE TABLE " + table).executeUpdate();
		}

		// Reset Sequences
		List<String> sequences = entityManager
				.createQuery("SELECT SEQUENCE_NAME FROM INFORMATION_SCHEMA.SEQUENCES WHERE SEQUENCE_SCHEMA='PUBLIC'",
						String.class)
				.getResultList();
		for (String sequence : sequences) {
			entityManager.createQuery("ALTER SEQUENCE " + sequence + " RESTART WITH 1").executeUpdate();
		}

		// Enable FK
		entityManager.createQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();

	}
}
