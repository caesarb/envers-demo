package model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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
public class Shareholder implements Serializable {

    private static final long serialVersionUID = 4508737351170028533L;

    @Id
    @GeneratedValue(strategy=GenerationType.TABLE)
    private long id;
    
    @OneToMany(mappedBy="shareholder", cascade=CascadeType.ALL, targetEntity=ShareAudited.class)
    private Set<Share> shares = new HashSet<>();

    @Column(name = "name")
    private String name;

    //satisfy hibernate
    protected Shareholder() {
    }

    public Shareholder(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Share> getShares() {
        return shares;
    }

    public void addShare(Share share) {
        this.shares.add(share);
    }
    
    public void removeShare(Share share){
        this.shares.remove(share);
    }

}
