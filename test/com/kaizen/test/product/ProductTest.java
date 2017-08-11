package com.kaizen.test.product;

import inventory.model.Product;
import java.rmi.RemoteException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import com.kaizen.test.hibernate.HibernateTest;
import static com.kaizen.test.hibernate.HibernateTest.crud;

/**
 *
 * @author VakSF
 */
public class ProductTest {
    
    public ProductTest() {
        System.out.println("ProductTest Constructor");
    }
    
    @BeforeClass
    public static void setUpClass() throws RemoteException {
        HibernateTest.setUpClass();
    }
    
    @AfterClass
    public static void tearDownClass() {
    
    }
    
    @Before
    public void setUp() {
    
    }
    
    @After
    public void tearDown() {
    
    }
    
    @Test
    public void createAdjustments() throws RemoteException {
        
        Product product = new Product("somecode", "somename", 10.0);
        Integer save = crud.save(product);
        
        System.out.println(save);
        
    }
    
}