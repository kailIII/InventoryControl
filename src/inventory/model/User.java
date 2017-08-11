package inventory.model;

import java.util.Date;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 *
 * @author VakSF
 */
@Entity
@Table (name = "user")
public class User implements Serializable, Comparable<User> {
    
    private Integer id;
    private String username;
    private Date date;
    private String password;
    private List<UserStore> userStores = new ArrayList<>(0);

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.date = new Date();
    }
    
    public void setUser(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Column
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "pk.user", cascade=CascadeType.ALL)
    public List<UserStore> getUserStores() {
        return this.userStores;
    }
    
    @Transient
    public List<Store> getStores() {
        
        List<Store> stores = new ArrayList<>();
        
        this.userStores.forEach((userStore) -> {
            stores.add(userStore.getStore());
        });
        
        return stores;
    }
    
    public void setUserStores(List<UserStore> userStores) {
        this.userStores = userStores;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.id;
        hash = 97 * hash + Objects.hashCode(this.username);
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
        final User other = (User) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        return true;
    }
    
    @Override
    public int compareTo(User user) {
        
        int result = this.username.compareTo(user.getUsername());
        
        return result;
    }

    @Override
    public String toString() {
        return this.username + " - " + this.getDate().toString();
    }
    
}