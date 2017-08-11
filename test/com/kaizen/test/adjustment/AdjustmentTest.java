package com.kaizen.test.adjustment;

import inventory.model.Adjustment;
import inventory.model.AdjustmentItem;
import inventory.model.Product;
import inventory.model.Store;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import com.kaizen.test.hibernate.HibernateTest;
import static com.kaizen.test.hibernate.HibernateTest.crud;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author VakSF
 */
public class AdjustmentTest {
    
    public AdjustmentTest() {
        System.out.println("AdjustmentTest Constructor");
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
        
//        Product product = new Product("Code 01", "Name 01", 10.0);
//        Store store = new Store("Name 01", "Location 01");
//        
//        product.setId(crud.save(product));
//        
//        store.setId(crud.save(store));
//        
//        Adjustment adjustment = new Adjustment("Code 01", new Date(), "Status", "Observation");
//        
//        for (int i = 0; i < 10; i++) {
//            
//            AdjustmentItem adjustmentItem = new AdjustmentItem(
//                    store, product,
//                    i + 10, i + 20
//            );
//            
//            adjustment.getItems().add(adjustmentItem);
//            
//        }
//        
//        crud.save(adjustment);
        
        System.out.println("adjustments = " + crud.getList(Adjustment.class).size());
        System.out.println("items = " + crud.getList(AdjustmentItem.class).size());
        
        // Adjustments        
        compareSizeList(crud.getList(Adjustment.class), 1);
        
        // AdjustmentItems        
        compareSizeList(crud.getList(AdjustmentItem.class), 10);
        
    }
    
    private <T> void compareSizeList(List<T> list, int expectedSize) {
        
        String message = "The list doesn't have the expected quantity";
        
        assertTrue(message, list.size() == expectedSize);
        
    }
    
}