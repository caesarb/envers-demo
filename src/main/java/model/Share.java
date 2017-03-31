package model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * 
 * 
 * @author kaiserbe
 */
@MappedSuperclass
public abstract class Share {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private long id;

    @Column
    private String name;

    public long getId() {
        return id;
    }

    public abstract BigDecimal getValue();

    public abstract void setValue(BigDecimal value);

    public abstract Shareholder getShareholder();

    public abstract void setShareholder(Shareholder shareholder);

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

}
