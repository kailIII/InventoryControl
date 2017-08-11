package inventory.model;

import java.io.Serializable;
import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.EmbeddedId;
import javax.persistence.Transient;
import javax.transaction.Transactional;

/**
 *
 * @author VakSF
 */

@Entity
@Transactional
@Table(name = "store_product")
@AssociationOverrides({
    @AssociationOverride(name = "pk.store",
            joinColumns = @JoinColumn(name = "store_id")),
    @AssociationOverride(name = "pk.product",
            joinColumns = @JoinColumn(name = "product_id")) })
public class StoreProduct implements Serializable {
    
    private StoreProductId pk = new StoreProductId();
    private Integer units;
    private Integer defaultUnits;
    private Integer reorderLevel;

    public StoreProduct() {
        
    }
    
    @EmbeddedId
    public StoreProductId getPk() {
        return pk;
    }
    
    public void setPk(StoreProductId pk) {
        this.pk = pk;
    }
    
    @Transient
    public Store getStore() {
        return getPk().getStore();
    }
    
    public void setStore(Store store) {
        getPk().setStore(store);
    }
    
    @Transient
    public Product getProduct() {
        return getPk().getProduct();
    }
    
    public void setProduct(Product product) {
        getPk().setProduct(product);
    }
    
    @Column(name = "units")
    public Integer getUnits() {
        return units;
    }
    
    public void setUnits(Integer units) {
        this.units = units;
    }
    
    @Column(name = "default_units")
    public Integer getDefaultUnits() {
        return defaultUnits;
    }
    
    public void setDefaultUnits(Integer defaultUnits) {
        this.defaultUnits = defaultUnits;
    }
    
    @Column(name = "reorder_level")
    public Integer getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(Integer reorderLevel) {
        this.reorderLevel = reorderLevel;
    }
    
    @Override
    public boolean equals(Object object) {
        
        if (this == object)
            return true;
        
        if (object == null || getClass() != object.getClass())
            return false;
        
        StoreProduct that = (StoreProduct) object;
        
        return !(getPk() != null ? !getPk().equals(that.getPk())
                : that.getPk() != null);
    }
    
    @Override
    public int hashCode() {
        return (getPk() != null ? getPk().hashCode() : 0);
    }

    @Override
    public String toString() {
        return "Product: " + this.pk.getProduct().toString() + 
               "\nStore: " + this.pk.getStore().getName() + 
               "\nDefault Units: " + this.defaultUnits + 
               "\nReorder Level: " + this.reorderLevel + 
               "\nUnits: " + this.units;
    }
    
}
