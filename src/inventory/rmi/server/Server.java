package inventory.rmi.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import inventory.rmi.implementations.AdjustImpl;
import inventory.rmi.implementations.CRUDImpl;

/**
 *
 * @author VakSF
 */
public class Server {

    public Server() throws RemoteException {
        
        Registry registry = LocateRegistry.createRegistry(1099);
        
        registry.rebind("crud", new CRUDImpl());
        registry.rebind("adjust", new AdjustImpl());
        
        System.out.println("Server started");
        
    }
    
    public static void main(String[] args) throws RemoteException {
        
        Server server = new Server();
        
    }
    
}
