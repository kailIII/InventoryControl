package inventory.crud.user.dialog;

import inventory.model.Store;
import inventory.model.User;
import inventory.model.UserStore;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ListView;

/**
 *
 * @author VakSF
 */
public class StoreViewDialogController implements Initializable {
    
    @FXML
    private ListView<Store> storesLV; 
    
    private final ObservableList<Store> storesList = FXCollections.observableArrayList();
    private final List<Store> stores = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public void setUser(User user) {
        
        List<UserStore> userStores = user.getUserStores();
        
        userStores.forEach((userStore) -> {
            
            this.storesList.add(
                    userStore.getStore()
            );
            
        });            
        this.storesLV.setItems(this.storesList);
        
    }
    
    @FXML
    private void close(ActionEvent event) {
        this.closeNode(event);
    }
    
    private void closeNode(ActionEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
    }
    
}
