package inventory.rmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author VakSF
 */
public interface CRUD extends Remote {
    
    public void test() throws RemoteException;
    
    public boolean login(String username, String password) throws RemoteException;
    
    public <T> List<T> getList(Class<T> type) throws RemoteException;
    
    public Integer save(Object object) throws RemoteException;
    
    public boolean update(Object object) throws RemoteException;
            
    public boolean delete(Object object) throws RemoteException;
    
    public int getLastId(String entity) throws RemoteException;
    
}
