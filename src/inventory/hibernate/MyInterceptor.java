package inventory.hibernate;

import java.io.Serializable;
import java.util.Iterator;
import org.hibernate.CallbackException;
import org.hibernate.EmptyInterceptor;
import org.hibernate.EntityMode;
import org.hibernate.Transaction;
import org.hibernate.type.Type;

/**
 *
 * @author VakSF
 */
public class MyInterceptor extends EmptyInterceptor {

    @Override
    public void onCollectionUpdate(Object collection, Serializable key) throws CallbackException {
        System.out.println("here 1");
    }

    @Override
    public void onCollectionRecreate(Object collection, Serializable key) throws CallbackException {
        System.out.println("here 2");
    }

    @Override
    public void onCollectionRemove(Object collection, Serializable key) throws CallbackException {
        System.out.println("here 3");
    }
    
    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        
        System.out.println("Guardando");
        
        return true;
    }

    @Override
    public boolean onLoad(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        
        System.out.println("here 15");
        
        return super.onLoad(entity, id, state, propertyNames, types);
    }

    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
        
        System.out.println("here 16");
        
        return super.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);
    }

    @Override
    public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        
        System.out.println("Eliminando");
        
    }
    
    
    
}
