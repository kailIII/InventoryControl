package inventory.dialog.store;

import java.net.URL;
import javafx.fxml.FXML;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import inventory.model.Store;
import inventory.crud.store.StoreController;
import inventory.login.LoginController;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;

import java.rmi.RemoteException;
import static inventory.login.LoginController.crud;

/**
 *
 * @author VakSF
 */
public class SelectStoreController implements Initializable {
    
    @FXML
    private ListView<Store> storeLV;
    
    private ObservableList<Store> storeList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        this.storeList = FXCollections.observableArrayList();
        
        if (crud == null) 
            LoginController.connectServer();
        
        this.initList();
        
    }
    
    private void initList() {
        
        try {
            this.storeList.clear();
            
            this.storeList.addAll(crud.getList(Store.class));
            
            this.storeLV.setItems(this.storeList);
            
            if (!this.storeList.isEmpty()) {
                
                this.storeLV.getSelectionModel().selectFirst();
                
            }
        } catch (RemoteException ex) {
            System.out.println("Exception: " + ex.toString());
        }
        
    }
    
    public Store getSelectedStore() {
        return this.storeLV.getSelectionModel().getSelectedItem();
    }
    
    @FXML
    private void openStore() throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/inventory/crud/store/StoreFXML.fxml"
        ));

        Stage stage = new Stage();
        stage.setScene(new Scene((Pane) loader.load()));
        
        StoreController storeController = 
                loader.<StoreController>getController();
       
        stage.getIcons().add(new Image(getClass().getResourceAsStream(
                "/inventory/images/small-logo.png")
        ));
        
        stage.setOnCloseRequest((event) -> {
            this.initList();
        });
        
        stage.setTitle("Manejador de tiendas");
        stage.showAndWait();
        
    }
    
    @FXML
    private void finish(ActionEvent event) {
        
        String text = ((Button) event.getSource()).getText();
        
        if (text.equalsIgnoreCase("cancelar")) {
            this.storeLV.getSelectionModel().clearSelection();
        }
        
        this.close(event);
    }
    
    private void close(ActionEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
    }
    
}
