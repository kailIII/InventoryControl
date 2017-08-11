package inventory.model;

import java.io.Serializable;
import java.util.ArrayList;
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

@Entity
@Table(name="department")
public class Department implements Serializable {
    
    @Id
    @GeneratedValue
    @Column(name="DEPARTMENT_ID")
    private Long departmentId;
    
    @Column(name="DEPT_NAME")
    private String departmentName;
    
    @OneToMany(cascade={CascadeType.ALL})
    @JoinColumn(name="department_id")
    @IndexColumn(name="idx")
    private List<Employee> employees = new ArrayList<>();
    
    public Long getDepartmentId() {
        return departmentId;
    }
    
    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }
    
    public String getDepartmentName() {
        return departmentName;
    }
    
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
    
    public List<Employee> getEmployees() {
        return employees;
    }
    
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
