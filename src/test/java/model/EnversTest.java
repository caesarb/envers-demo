package model;

import static org.testng.Assert.assertTrue;

import java.util.List;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.query.AuditEntity;
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
	public void test1(String pu) {
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
		adidas.setValue(1200);
		entityManager.getTransaction().commit();

	}

	/**
	 * Audit
	 * 
	 * @param pu
	 */
	@Test(dataProvider = "pus")
	public void test2(String pu) {
		test1(pu);

		Company adidas = entityManager.createQuery("select c from Company c where c.name='adidas'", Company.class).getSingleResult();

		entityManager.getTransaction().begin();
		adidas.setValue(1400);
		entityManager.getTransaction().commit();
		entityManager.getTransaction().begin();
		adidas.setValue(1300);
		entityManager.getTransaction().commit();
		entityManager.getTransaction().begin();
		adidas.setValue(900);
		entityManager.getTransaction().commit();
		
		AuditReader reader = AuditReaderFactory.get(entityManager);
		@SuppressWarnings("unchecked")
		List<Object[]> adidasHistory = reader.createQuery().forRevisionsOfEntity(Company.class, false, true)//
				.add(AuditEntity.property("name").eq("adidas"))//
				.getResultList();

		assertTrue(!adidasHistory.isEmpty());
		printStockchart(adidasHistory);
	}
	
	private void printStockchart(List<Object[]> history){
		StringBuilder sb = new StringBuilder();
		
		for (Object[] h : history) {
		for(int x=0; x<20; x++){
			
				if(((Company) h[0]).getValue() == x*100)
					sb.append("*");
				else
					sb.append(" ");
			}
			sb.append("\r\n");
		}
		
		System.out.println(sb.toString());
	}
}
