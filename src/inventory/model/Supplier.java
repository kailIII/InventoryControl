package inventory.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author VakSF
 */

@Entity
@Table (name = "supplier")
public class Supplier implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    
    @Column
    private String companyName;
    
    @Column
    private String contactName;
    
    @Column
    private String contactTitle;
    
    @Column
    private String address;
    
    @Column
    private String city;
    
    @Column
    private String country;
    
    @Column
    private String phone;
    
    public Supplier() {
        
    }
    
    public Supplier(String companyName, String contactName, String contactTitle, String address, String city, String country, String phone) {
        this.companyName = companyName;
        this.contactName = contactName;
        this.contactTitle = contactTitle;
        this.address = address;
        this.city = city;
        this.country = country;
        this.phone = phone;
    }
    
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public String getCompanyName() {
        return this.companyName;
    }
    
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public String getContactName() {
        return this.contactName;
    }
    
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
    public String getContactTitle() {
        return this.contactTitle;
    }
    
    public void setContactTitle(String contactTitle) {
        this.contactTitle = contactTitle;
    }
    public String getAddress() {
        return this.address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    public String getCity() {
        return this.city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    public String getCountry() {
        return this.country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
    public String getPhone() {
        return this.phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + this.id;
        hash = 83 * hash + Objects.hashCode(this.companyName);
        hash = 83 * hash + Objects.hashCode(this.contactName);
        hash = 83 * hash + Objects.hashCode(this.contactTitle);
        hash = 83 * hash + Objects.hashCode(this.address);
        hash = 83 * hash + Objects.hashCode(this.country);
        hash = 83 * hash + Objects.hashCode(this.phone);
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
        final Supplier other = (Supplier) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.companyName, other.companyName)) {
            return false;
        }
        if (!Objects.equals(this.contactName, other.contactName)) {
            return false;
        }
        if (!Objects.equals(this.contactTitle, other.contactTitle)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        if (!Objects.equals(this.city, other.city)) {
            return false;
        }
        if (!Objects.equals(this.country, other.country)) {
            return false;
        }
        
        return Objects.equals(this.phone, other.phone);
    }

    @Override
    public String toString() {
        return this.companyName + " : " + this.contactName;
    }
    
}
