package model;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import entities.Company;
import entities.Shareholder;

public class EnversTest extends AbstractTest {

	private Stockmarket stockmarket;

	public void openStockmarket() {
		stockmarket = new Stockmarket(entityManager);
	}

	@DataProvider(name = "pus")
	public static Object[][] persistenceUnits() {
		return new Object[][] { //
				{ "pu-test1" } };
	}

	@Test(dataProvider = "pus")
	public void test(String pu) throws InstantiationException, IllegalAccessException {
		init(pu);
		
		// test setup
		entityManager.getTransaction().begin();
		Shareholder alice = new Shareholder("alice", 400);
		Shareholder bob = new Shareholder("bob", 300);

		Company adidas = new Company("adidas", 1000, 10);
		Company vw = new Company("VW AG", 1000, 10);

		entityManager.persist(alice);
		entityManager.persist(bob);
		entityManager.persist(adidas);
		entityManager.persist(vw);
		entityManager.getTransaction().commit();
		// test setup ende

		openStockmarket();
		stockmarket.buyShare(alice, adidas);
		stockmarket.buyShare(alice, adidas);
		stockmarket.buyShare(bob, adidas);
		stockmarket.buyShare(bob, vw);
		stockmarket.buyShare(bob, vw);
		
		entityManager.getTransaction().begin();
		adidas.setValue(2000);
		entityManager.getTransaction().commit();
	}

}
