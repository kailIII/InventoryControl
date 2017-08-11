package com.kaizen.test.hibernate;

import inventory.model.Adjustment;
import inventory.model.AdjustmentItem;
import inventory.rmi.interfaces.CRUD;
import inventory.rmi.server.Server;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author VakSF
 */
public class HibernateTest {
    
    public static CRUD crud;
    
    public HibernateTest() {
        System.out.println("HibernateTest Constructor");
    }
    
    @BeforeClass
    public static void setUpClass() throws RemoteException {
        
        new Server();
        
        try {
            
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            
            if (crud == null) {
                crud = (CRUD) registry.lookup("crud");
            }
            
        } catch (RemoteException | NotBoundException ex) {
            
            System.out.println("Error: " + ex.toString());
            
            System.exit(0);
            
        }
        
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
    public void hibernateTest() throws RemoteException {
        crud.test();
    }
}
