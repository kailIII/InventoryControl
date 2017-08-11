package inventory.model;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.transaction.Transactional;

@Embeddable
@Transactional
public class StoreProductId implements Serializable {
    
    private Store store;
    private Product product;
    
    @ManyToOne
    public Store getStore() {
        return store;
    }
    
    public void setStore(Store store) {
        this.store = store;
    }
    
    @ManyToOne
    public Product getProduct() {
        return product;
    }
    
    public void setProduct(Product product) {
        this.product = product;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        StoreProductId that = (StoreProductId) o;
        
        if (store != null ? !store.equals(that.store) : that.store != null) return false;
        return !(product != null ? !product.equals(that.product) : that.product != null);
    }
    
    @Override
    public int hashCode() {
        int result;
        result = (store != null ? store.hashCode() : 0);
        result = 31 * result + (product != null ? product.hashCode() : 0);
        return result;
    }
    
}