package entities;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "share")
@AssociationOverrides({ //
		@AssociationOverride(name = "pk.company", joinColumns = @JoinColumn(name = "company_id")), //
		@AssociationOverride(name = "pk.shareholder", joinColumns = @JoinColumn(name = "shareholder_id")) //
})
public class Share {

	@EmbeddedId
	private ShareId pk = new ShareId();

	@Column(name = "quantity")
	private int quantity=1;

	public ShareId getPk() {
		return pk;
	}

	public void setPk(ShareId pk) {
		this.pk = pk;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	@Transient
	public Shareholder getShareholder() {
		return getPk().getShareholder();
	}

	public void setShareholder(Shareholder shareholder) {
		getPk().setShareholder(shareholder);
	}
	
	@Transient
	public Company getCompany() {
		return getPk().getCompany();
	}

	public void setGroup(Company company) {
		getPk().setCompany(company);
	}
}
