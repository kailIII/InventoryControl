package inventory.model;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.transaction.Transactional;

/**
 *
 * @author VakSF
 */

@Embeddable
@Transactional
public class UserStoreId implements Serializable {
    
    private User user;
    private Store store;
    
    @ManyToOne
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    @ManyToOne
    public Store getStore() {
        return store;
    }
    
    public void setStore(Store store) {
        this.store = store;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        UserStoreId that = (UserStoreId) o;
        
        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        return !(store != null ? !store.equals(that.store) : that.store != null);
    }
    
    @Override
    public int hashCode() {
        int result;
        result = (user != null ? user.hashCode() : 0);
        result = 31 * result + (store != null ? store.hashCode() : 0);
        return result;
    }
    
}
