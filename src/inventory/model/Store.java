package inventory.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.transaction.Transactional;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import static javax.persistence.GenerationType.IDENTITY;

/**
 *
 * @author VakSF
 */

@Entity
@Transactional
@Table (name = "store")
public class Store implements Serializable {
    
    private Integer id;
    private String name;
    private String location;
    private List<StoreProduct> storeProducts = new ArrayList<>(0);
    
    public Store() {
        
    }

    public Store(Integer id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }
    
    public Store(String name, String location) {
        this.name = name;
        this.location = location;
    }
    
    public void setStore(Store store) {
        this.name = store.getName();
        this.location = store.getLocation();
    }
    
    
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name = "location")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "pk.store", cascade = CascadeType.ALL)
    public List<StoreProduct> getStoreProducts() {
        return this.storeProducts;
    }
    
    public void setStoreProducts(List<StoreProduct> storeProducts) {
        this.storeProducts = storeProducts;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.id;
        hash = 59 * hash + Objects.hashCode(this.name);
        hash = 59 * hash + Objects.hashCode(this.location);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Store other = (Store) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.location, other.location)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
}