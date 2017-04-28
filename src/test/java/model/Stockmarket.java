package model;

import javax.persistence.EntityManager;

import entities.Company;
import entities.Share;
import entities.Shareholder;

public class Stockmarket {
	protected EntityManager entityManager;

	public Stockmarket(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public void buyShare(Shareholder shareholder, Company company) {
		entityManager.getTransaction().begin();
		for (Share s : shareholder.getShares())
			if (s.getCompany().equals(company)){
				s.setQuantity(s.getQuantity() + 1);
				entityManager.getTransaction().commit();
				return ;
			}
		Share s = new Share();
		s.setShareholder(shareholder);
		s.setGroup(company);
		shareholder.getShares().add(s);
		entityManager.getTransaction().commit();
	}

}
