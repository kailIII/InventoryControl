package inventory.rmi.implementations;

import inventory.model.User;
import inventory.util.HibernateUtil;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import inventory.rmi.interfaces.CRUD;
import java.io.Serializable;
import java.math.BigInteger;

/**
 *
 * @author VakSF
 */
public class CRUDImpl extends UnicastRemoteObject implements CRUD {
    
    private SessionFactory sessionFactory;
    
    public CRUDImpl() throws RemoteException {
        super();
    }
    
    /**
     * Permite generar una nueva sesión mediante
     * el sessionFactory
     * 
     * @return Una sesión de hibernate
     */
    private Session getSession() {
        return this.sessionFactory.openSession();
    }
    
    @Override
    public void test() {
        
        this.sessionFactory = HibernateUtil.getSessionFactory();
        
        Session session = this.getSession();
        
        Transaction transaction = null;
        
        try {
            
            transaction = session.beginTransaction();
            
            transaction.commit();
            
        } catch (HibernateException ex) {
            
            this.rollback(transaction);
            
        } finally {
            
            session.flush();
            session.close();
            
        }
        
    }

    @Override
    public boolean login(String username, String password) throws RemoteException {
        
        User user = null;
        
        this.sessionFactory = HibernateUtil.getSessionFactory();
        
        Session session = this.getSession();
        
        Transaction transaction = null;
        
        try {
            
            transaction = session.beginTransaction();
            
            /* Se obtiene un registro correspondiente al usuario y contraseña */
            Query query = session.createQuery(
                    "FROM User user WHERE "
                  + "user.username = :username AND "
                  + "user.password = :password"
            );

            query.setParameter("username", username);
            query.setParameter("password", password);
            
            user = (User) query.setMaxResults(1).uniqueResult();
            
            transaction.commit();
            
        } catch (HibernateException ex) {
            
            this.rollback(transaction);
            
        } finally {
            
            session.flush();
            session.close();
            
        }
        
        return  user != null ? 
                user.getUsername().equals(username) && 
                user.getPassword().equals(password) : false;
    }
    
    @Override
    public <T> List<T> getList(Class<T> type) throws RemoteException {
        
        this.sessionFactory = HibernateUtil.getSessionFactory();
        
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        
        /* Se crea una lista para almacenar elementos */
        List<T> list = new ArrayList<>();
        
        /* Se obtienen los elementos existentes. */
        try {
            
            list = session.createCriteria(type).list();
            
            transaction.commit();
             
        } catch (HibernateException ex) {
            
            System.out.println("Here");
            
            System.out.println("Error = " + ex.toString());
            
            if (transaction != null) transaction.rollback();
            
        } finally {
            
            session.close();
            
        }
        
        return list;
        
    }

    @Override
    public Integer save(Object object) throws RemoteException {
        
        this.sessionFactory = HibernateUtil.getSessionFactory();
        
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        
        boolean saved = false;
        
        Integer id = null;
        
        try {
            
            try {
                
                Serializable save = session.save(object);
                
                id = (Integer) save;
                
            } catch (ClassCastException ex) {
                
                session.save(object);
                
            }
            
            transaction.commit();
            
            saved = true;
             
        } catch (HibernateException ex) {
            
            this.rollback(transaction);
            
            saved = false;
            
        } finally {
            
            session.close();
            
        }
        
        return id;
        
    }

    @Override
    public boolean update(Object object) throws RemoteException {
        
        this.sessionFactory = HibernateUtil.getSessionFactory();
        
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        
        boolean updated = false;
        
        try {
            
            session.saveOrUpdate(object);
            transaction.commit();
            
            updated = true;
             
        } catch (HibernateException ex) {
            
            System.out.println("Exception: " + ex.toString());
            
            this.rollback(transaction);
            
            updated = false;
            
        } finally {
            
            session.close();
            
        }
        
        return updated;
        
    }
    
    @Override
    public boolean delete(Object object) throws RemoteException {
        
        this.sessionFactory = HibernateUtil.getSessionFactory();
        
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        
        boolean deleted = false;
        
        try {
            
            session.delete(object);
            transaction.commit();
            
            deleted = true;
             
        } catch (HibernateException ex) {
            
            this.rollback(transaction);
            
            deleted = false;
            
        } finally {
            
            session.flush();
            session.close();
            
        }
        
        return deleted;
        
    }
    
    
    @Override
    public int getLastId(String entity) {
            
        this.sessionFactory = HibernateUtil.getSessionFactory();

        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        
        int id = 0;
        
        try {
            
            Object lastId = session.createSQLQuery(
                    "SELECT LAST_INSERT_ID() AS last_id FROM " + entity
            ).uniqueResult();
            
            if (lastId != null) {
                
                System.out.println(lastId);
                
                id = ((BigInteger) lastId).intValue();
                
                System.out.println(id);
                
            } else {
                
                id = 0;
                
            }
            
            transaction.commit();
             
        } catch (HibernateException ex) {
            
            this.rollback(transaction);
            
        } finally {
            
            session.close();
            
        }
        
        return id;
    }
    
    private void rollback(Transaction transaction) {
        if (transaction != null) transaction.rollback();
    }

    
}
