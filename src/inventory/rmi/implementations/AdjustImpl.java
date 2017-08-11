package inventory.rmi.implementations;

import inventory.rmi.interfaces.Adjust;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author VakSF
 */
public class AdjustImpl extends UnicastRemoteObject implements Adjust {
    
    private CRUDImpl crud;
    
    public AdjustImpl() throws RemoteException {
        super();
    }
    
}
