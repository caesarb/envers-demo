package entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "shareholder")
public class Shareholder {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "name")
	private String name;
	
	@Column(name = "credit")
	private int credit;

	@OneToMany(mappedBy = "pk.shareholder", cascade = CascadeType.ALL)
	private List<Share> shares = new ArrayList<>();

	protected Shareholder() {
		// satisfy hibernate
	}

	public Shareholder(String name, int credit) {
		this.name = name;
		this.credit=credit;
	}

	public List<Share> getShares() {
		return shares;
	}
	
	
	
	

}
