package model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class PersonTest {

	//private static final Logger LOGGER = LoggerFactory.getLogger(PersonTest.class);

	protected static EntityManagerFactory entityManagerFactory;
	protected static EntityManager entityManager;
	// protected static IDatabaseConnection connection;
	// protected static IDataSet dataset;

	@BeforeClass
	public static void initEntityManager() throws Exception {
		entityManagerFactory = Persistence
				.createEntityManagerFactory("pu");

		// connection = new
		// DatabaseConnection(((SessionImpl)(entityManager.getDelegate())).connection());
		// connection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY,
		// new H2DataTypeFactory());

		// FlatXmlDataSetBuilder flatXmlDataSetBuilder = new
		// FlatXmlDataSetBuilder();
		// flatXmlDataSetBuilder.setColumnSensing(true);
		// dataset = flatXmlDataSetBuilder.build(
		// Thread.currentThread().getContextClassLoader().getResourceAsStream("test-dataset.xml"));
	}

	@Before
	public void beginTransaction() {
		entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
	}

	@After
	public void rollbackTransaction() {
		if (entityManager.getTransaction().isActive()) {
			entityManager.getTransaction().rollback();
		}

		if (entityManager.isOpen()) {
			entityManager.close();
		}
	}

	@AfterClass
	public static void closeEntityManagerFactory() {
		entityManagerFactory.close();
	}

	@Test
	public void test1() {
		//LOGGER.info("bla blubb");
		Assert.assertNotNull(entityManagerFactory);
		Assert.assertNotNull(entityManager);

		Person p1 = new Person();
		p1.setName("Benny");
		
		entityManager.persist(p1);
	}
}
