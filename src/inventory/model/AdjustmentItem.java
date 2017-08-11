package inventory.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
@Table(name = "adjustment_item")
public class AdjustmentItem implements Serializable {
    
    @Id
    @GeneratedValue
    @Column(name="adjustment_item_id")
    private Long adjustmentItemId;
    
    @ManyToOne
    @JoinColumn(name="adjustment_id", insertable=false, updatable=false, nullable=false)
    private Adjustment adjustment;
    
    @Column(name="old_quantity")
    private Integer oldQuantity;
    
    @Column(name="new_quantity")
    private Integer newQuantity;
    
    @Column(name="total_quantity")
    private Integer totalQuantity;
    
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;
    
    @OneToOne
    @JoinColumn(name = "store_id")
    private Store store;
    
    public AdjustmentItem() {
        
    }
    
    public AdjustmentItem(Store store, Product product, Integer oldQuantity, Integer newQuantity) {
        this.store = store;
        this.product = product;
        this.oldQuantity = oldQuantity;
        this.newQuantity = newQuantity;
    }

    public Adjustment getAdjustment() {
        return adjustment;
    }

    public void setAdjustment(Adjustment adjustment) {
        this.adjustment = adjustment;
    }

    public Long getAdjustmentItemId() {
        return adjustmentItemId;
    }

    public void setAdjustmentItemId(Long adjustmentItemId) {
        this.adjustmentItemId = adjustmentItemId;
    }
    
    public Integer getOldQuantity() {
        return this.oldQuantity;
    }
    
    public void setOldQuantity(Integer oldQuantity) {
        this.oldQuantity = oldQuantity;
    }
    
    public Integer getNewQuantity() {
        return this.newQuantity;
    }
    
    public void setNewQuantity(Integer newQuantity) {
        this.newQuantity = newQuantity;
    }
    
    public Integer getTotalQuantity() {
        return this.totalQuantity;
    }
    
    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
    
    public Product getProduct() {
        return this.product;
    }
    
    public void setProduct(Product product) {
        this.product = product;
    }
    
    public Store getStore() {
        return this.store;
    }
    
    public void setStore(Store store) {
        this.store = store;
    }
    
}
