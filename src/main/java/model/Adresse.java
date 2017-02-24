package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Adresse implements Serializable {

	private static final long serialVersionUID = 4508737351170028533L;

	@Id
	private long id;
	
	@Column(name = "strasse")
	private String strasse;

	public String getStrasse() {
		return strasse;
	}

	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}

}
