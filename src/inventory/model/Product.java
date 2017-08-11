package inventory.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import static javax.persistence.GenerationType.IDENTITY;
import javax.transaction.Transactional;

/**
 *
 * @author VakSF
 */

@Entity
@Transactional
@Table(name = "product")
public class Product implements Serializable {
    
    private int id;
    private String code;
    private String description;
    private String name;
    private String brand;
    private double unitPrice;
    
    private Category category;
    private Supplier supplier;
    
    public Product() {
        
    }
    
    public Product(String code, String name, double price) {
        this.code = code;
        this.name = name;
        this.unitPrice = price;
    }
    
    public Product(String code, String description, String name, 
            double unitPrice, String brand, Category category, Supplier supplier) {
        this.code = code;
        this.description = description;
        this.name = name;
        this.brand = brand;
        this.unitPrice = unitPrice;
        this.category = category;
        this.supplier = supplier;
    }
    
    public Product(String description, String name, double unitPrice, 
            String brand, Category category, Supplier supplier) {
        this.description = description;
        this.name = name;
        this.brand = brand;
        this.unitPrice = unitPrice;
        this.category = category;
        this.supplier = supplier;
    }

    public Product(int id, String code, String description, String name, String brand, double unitPrice, Category category, Supplier supplier) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.name = name;
        this.brand = brand;
        this.unitPrice = unitPrice;
        this.category = category;
        this.supplier = supplier;
    }
    
    public void setProduct(Product product) {
        this.code = product.getCode();
        this.description = product.getDescription();
        this.name = product.getName();
        this.unitPrice = product.getUnitPrice();
        this.brand = product.getBrand();
        this.category = product.getCategory();
        this.supplier = product.getSupplier();
    }
    
    @Id
    @GeneratedValue(strategy = IDENTITY)
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    @Column(name = "code", nullable = false)
    public String getCode() {
        return this.code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    @Column(name = "description", nullable = true)
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Column(name = "name", nullable = false)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name = "unitPrice", nullable = false)
    public double getUnitPrice() {
        return unitPrice;
    }
    
    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
    
    @Column(name = "brand", nullable = true)
    public String getBrand() {
        return this.brand;
    }
    
    public void setBrand(String brand) {
        this.brand = brand;
    }
    
    @ManyToOne
    @JoinColumn(name = "category_id")
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    
    @ManyToOne
    @JoinColumn(name = "supplier_id")
    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
    
//    @OneToMany(fetch = FetchType.EAGER, mappedBy = "pk.product")
//    public Set<StockProduct> getStockProducts() {
//        return this.stockProducts;
//    }
//    
//    public void setStockProducts(Set<StockProduct> stockProducts) {
//        this.stockProducts = stockProducts;
//    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.id;
        hash = 67 * hash + Objects.hashCode(this.code);
        hash = 67 * hash + Objects.hashCode(this.description);
        hash = 67 * hash + Objects.hashCode(this.name);
        hash = 67 * hash + Objects.hashCode(this.brand);
        hash = 67 * hash + (int) (Double.doubleToLongBits(this.unitPrice) ^ (Double.doubleToLongBits(this.unitPrice) >>> 32));
        hash = 67 * hash + Objects.hashCode(this.category);
        hash = 67 * hash + Objects.hashCode(this.supplier);
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
        final Product other = (Product) obj;
        if (this.id != other.id) {
            return false;
        }
        if (Double.doubleToLongBits(this.unitPrice) != Double.doubleToLongBits(other.unitPrice)) {
            return false;
        }
        if (!Objects.equals(this.code, other.code)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.brand, other.brand)) {
            return false;
        }
        if (!Objects.equals(this.category, other.category)) {
            return false;
        }
        return Objects.equals(this.supplier, other.supplier);
    }

    @Override
    public String toString() {
        return this.name;
    }
    
}