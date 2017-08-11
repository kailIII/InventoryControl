package com.kaizen.test.store;

import inventory.model.Store;
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
public class StoreTest {
    
    public StoreTest() {
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
        
        Store store = new Store("somename", "somelocation");
        Integer save = crud.save(store);
        
        System.out.println(save);
        
    }
    
}