package inventory.model;

import java.io.Serializable;
import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.transaction.Transactional;

/**
 *
 * @author VakSF
 */

@Entity
@Transactional
@Table(name = "user_store")
@AssociationOverrides({
    @AssociationOverride(name = "pk.user",
            joinColumns = @JoinColumn(name = "user_id")),
    @AssociationOverride(name = "pk.store",
            joinColumns = @JoinColumn(name = "store_id")) })
public class UserStore implements Serializable {
    
    private UserStoreId pk = new UserStoreId();
    
    public UserStore() {
        
    }
    
    public UserStore(User user, Store store) {
        this.setUser(user);
        this.setStore(store);
    }
    
    @EmbeddedId
    public UserStoreId getPk() {
        return pk;
    }
    
    public void setPk(UserStoreId pk) {
        this.pk = pk;
    }
    
    @Transient
    public User getStock() {
        return getPk().getUser();
    }
    
    public void setUser(User user) {
        getPk().setUser(user);
    }
    
    @Transient
    public Store getStore() {
        return getPk().getStore();
    }
    
    public void setStore(Store store) {
        getPk().setStore(store);
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
        return "User: " + this.pk.getUser().toString() + 
             "\nStore: " + this.pk.getStore().getName();
    }
    
}
