package inventory.crud.user.dialog;

import inventory.javafx.table.cells.model.StoreFX;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import java.rmi.RemoteException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import inventory.model.Store;
import inventory.model.User;

import static inventory.login.LoginController.crud;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.cell.CheckBoxListCell;


/**
 *
 * @author VakSF
 */
public class StoreDialogController implements Initializable {
    
    @FXML
    private ListView<StoreFX> storesLV; 
    
    private ObservableList<StoreFX> storesFX = FXCollections.observableArrayList();
    
    private List<Store> stores = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public void setUser(User user, String type) {
        
        List<Store> userStores = user.getStores();
        
        try {
            
            for (Store store : crud.getList(Store.class)) {
                
                StoreFX storeFX = new StoreFX(store, false);
                
                storeFX.selectedProperty().addListener((observable, wasSelected, isSelected) -> {
                    
                    if (isSelected) {
                        
                        this.stores.add(storeFX.getStore());
                        
                    } else {
                        
                        this.stores.remove(storeFX.getStore());
                        
                    }
                    
                });
                
                this.storesFX.add(storeFX);
            }
            
            this.storesLV.setItems(this.storesFX);
            
            if (type.equalsIgnoreCase("edit")) {
                
                if (!userStores.isEmpty()) {

                    userStores.forEach((store) -> {

                        this.storesLV.getItems().forEach((StoreFX other) -> {

                            if (store.getName().equals(other.getStore().getName())) {
                                
                                other.setSelected(true);
                                
                            }

                        });

                    });

                }
                
            }
            
            this.storesLV.setCellFactory(CheckBoxListCell.forListView((StoreFX storeFX) -> {
                return storeFX.selectedProperty();
            }));
            
        } catch (RemoteException ex) {
            
            System.out.println("Remote exception: " + ex.toString());
            
        }
            
    }
    
    public List<Store> getStores() {
        return this.stores;
    }
    
    @FXML
    private void clear() {
        
        storesFX.forEach((storeFX) -> {
            storeFX.setSelected(false);
        });
        
        this.stores.clear();
        
        new Alert(
                AlertType.INFORMATION, 
                "La selección ha sido limpiada. " + 
                "No hay tiendas seleccionadas"
        ).showAndWait();
    }
    
    @FXML
    private void finish(ActionEvent event) throws RemoteException {
        this.close(event);
    }
    
    @FXML
    private void cancel(ActionEvent event) {
        this.close(event);
    }
    
    private void close(ActionEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
    }
    
}