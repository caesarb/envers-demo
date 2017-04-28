package entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class ShareId implements Serializable {
	private static final long serialVersionUID = -4913099003623551442L;

	@ManyToOne(cascade = CascadeType.ALL)
	private Shareholder shareholder;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Company company;

	public Shareholder getShareholder() {
		return shareholder;
	}

	public void setShareholder(Shareholder shareholder) {
		this.shareholder = shareholder;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

}
