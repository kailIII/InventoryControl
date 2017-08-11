package inventory.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.IndexColumn;

/**
 *
 * @author VakSF
 */
@Entity
@Table(name = "adjustment")
public class Adjustment implements Serializable {
    
    @Id
    @GeneratedValue
    @Column(name="adjustment_id")
    private Long adjustmentId;
    
    @Column(name="code")
    private String code;
    
    @Column(name="date")
    private Date date;
    
    @Column(name="observation")
    private String observation;
    
    @Column(name="status")
    private String status;
    
    @OneToMany(cascade={CascadeType.ALL})
    @JoinColumn(name="adjustment_id")
    @IndexColumn(name="idx")
    private List<AdjustmentItem> items = new ArrayList<>();

    public Adjustment() {
        
    }

    public Adjustment(String code, Date date, String status, String observation) {
        this.code = code;
        this.date = date;
        this.status = status;
        this.observation = observation;
    }

    public Long getAdjustmentId() {
        return adjustmentId;
    }

    public void setAdjustmentId(Long adjustmentId) {
        this.adjustmentId = adjustmentId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<AdjustmentItem> getItems() {
        return items;
    }

    public void setItems(List<AdjustmentItem> items) {
        this.items = items;
    }
    
}
