package model;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "share")
public class ShareEntity extends Share {

    @Column(name = "value")
    private BigDecimal value;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="share_id")
    private Shareholder shareholder;

    @Override
    public BigDecimal getValue() {
        return value;
    }

    @Override
    public void setValue(BigDecimal value) {
        this.value = value;

    }

    @Override
    public Shareholder getShareholder() {
        return shareholder;
    }

    @Override
    public void setShareholder(Shareholder shareholder) {
        this.shareholder = shareholder;

    }

}
