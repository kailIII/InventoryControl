package inventory.crud.store;

import inventory.model.Store;
import inventory.util.ValidatorUtil;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import inventory.login.LoginController;
import static inventory.login.LoginController.crud;

/**
 *
 * @author VakSF
 */
public class StoreController implements Initializable {

    @FXML
    private TabPane accordion;
    
    @FXML
    private Tab createTab, editTab;
    
    @FXML
    private TableView<Store> storesTV;
    
    @FXML
    private TextField nameTF, locationTF;
    
    @FXML
    private TextField editNameTF, editLocationTF;
    
    private ObservableList<Store> storesList;
    
    private ValidatorUtil createValidator, updateValidator;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        if (crud == null) 
            LoginController.connectServer();
        
        this.initValidators();
        
        try {
            
            this.initTV();
            
        } catch (RemoteException ex) {
            
        }
        this.initAll();
        
    }
    
    @FXML
    private void makeFloat() throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/inventory/crud/store/StoreFXML.fxml"
        ));

        Stage stage = new Stage();
        stage.setScene(new Scene((Pane) loader.load()));
        
        loader.setController(this);
        
        stage.showAndWait();
        
    }
    
    private void initValidators() {
        
        this.createValidator = new ValidatorUtil(
                nameTF, locationTF
        );
        
        this.updateValidator = new ValidatorUtil(
                editNameTF, editLocationTF
        );
        
    }
    
    private void initTV() throws RemoteException {
        
        this.storesList = FXCollections.observableArrayList();
        this.storesTV.setItems(this.storesList);
        
        this.storesList.setAll(crud.getList(Store.class)
        );
        
        if (!this.storesList.isEmpty()) {
            this.storesTV.getSelectionModel().selectFirst();
        }
        
        this.storesTV.getSelectionModel().selectedItemProperty()
            .addListener(($obs, $old, $new) -> {
                
            Tab selectedItem = this.accordion.getSelectionModel().getSelectedItem();
                
                if (selectedItem != null) {
                
                    if (selectedItem.equals(this.editTab)) {                 
                        
                        if ($new != null) {
                        
                            this.updateEditFields($new);

                        }
                        
                    }
                    
                }
            });
        
    }
    
    private void initAll() {
        
        this.accordion.getSelectionModel().select(this.createTab);
        
        this.accordion.getSelectionModel().selectedItemProperty().addListener((
            ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) -> {
            
                if (newValue != null) {
                    
                    String value = newValue.getText();
                    
                    if (value.equalsIgnoreCase("Crear una tienda nueva")) {
                        
                        this.createValidator.clearFields();
                        this.updateValidator.clearFields();
                        
                    } else {
                        
                        if (value.equalsIgnoreCase("Editar una tienda existente")) {
                            
                            this.createValidator.clearFields();
                            
                            if (!this.storesList.isEmpty()) {
                                
                                Store store = this.getSelected(this.storesTV);
                                
                                this.updateEditFields(store);
                                        
                            }
                            
                        }
                        
                    }
                    
                }
                
            });
    }
    
    @FXML
    private void save() throws RemoteException {
        
        if (this.createValidator.validateFields()) {
            
            String name = this.nameTF.getText();
            String location = this.locationTF.getText();
            
            Store store = new Store(name, location);
            
            Integer savedId = crud.save(store);
            
            if (savedId != null) {
                
                store.setId(savedId);
                
                this.storesList.add(store);
                this.createValidator.clearFields();
                
                new Alert(
                        Alert.AlertType.INFORMATION,
                        "Tienda almacenada correctamente"
                ).show();
                
            }
            
        } else {
            this.createValidator.emptyFields().showAndWait();
        }
        
    }
    
    @FXML
    private void update() throws RemoteException {
        
        if (this.updateValidator.validateFields()) {
            
            String name = this.editNameTF.getText();
            String location = this.editLocationTF.getText();
            
            Store updateStore = new Store(
                    name, location
            );
                            
            Store store = this.getSelected(this.storesTV);
            
            store.setStore(updateStore);

            boolean updated = crud.update(store);

            if (updated) {

                this.storesTV.refresh();                        
                this.createValidator.clearFields();

                new Alert(
                        Alert.AlertType.INFORMATION,
                        "Tienda editada correctamente"
                ).show();

            }
                
        } else {
            
            this.updateValidator.emptyFields().showAndWait();
            
        }
        
    }
    
    @FXML
    private void context(ActionEvent event) throws RemoteException {
        
        Object source = event.getSource();
        
        String className = source.getClass().getSimpleName();
        
        String item = "";
        
        if (className.equals("MenuItem")) {
            
            MenuItem menuItem = (MenuItem) source;
            
            item = menuItem.getText();
            
        } else {
            
            if (className.equals("Button")) {
                
                Button buttonItem = (Button) source;
            
                item = buttonItem.getText();
                
            }
            
        }
            
        if (item.equalsIgnoreCase("Crear")) {

            this.accordion.getSelectionModel().select(this.createTab);

        } else {
            
            if (item.equalsIgnoreCase("Editar")) {
                
                this.accordion.getSelectionModel().select(this.editTab);
                
                Store store = this.getSelected(this.storesTV);
                
                this.updateEditFields(store);
                
            } else {

                if (item.equalsIgnoreCase("Eliminar")) {
                    
                    this.deleteUser();
                    
                } else {
                    
                    if (item.equalsIgnoreCase("Cancelar")) {
                        
                        this.createValidator.clearFields();
                        this.updateValidator.clearFields();
                        
                        this.storesTV.getSelectionModel().selectFirst();
                        this.accordion.getSelectionModel().select(this.createTab);
                        
                    } else {
                        
                        if (item.equalsIgnoreCase("Salir")) {
                            
                            
                            
                        }
                        
                    }
                    
                }

            }

        }
        
    }
    
    private void deleteUser() throws RemoteException {
        
        Store store = this.getSelected(this.storesTV);
        
        if (store != null) {
            
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            
            confirmation.setTitle("Confirmacion de eliminación");
            confirmation.setHeaderText("Está seguro?");
            confirmation.setContentText(
                    "Desea eliminar la tienda " + store + "?"
            );
            
            Optional<ButtonType> option = confirmation.showAndWait();
            
            if (option.get() == ButtonType.OK) {
                
                crud.delete(store);
                
                this.storesList.remove(store);
                
                if (this.storesList.isEmpty()) {
                    System.out.println("is empty :(");
                }
                
                new Alert(
                        Alert.AlertType.INFORMATION, 
                        "Tienda eliminada correctamente"
                ).show();
                
            }
            
        }
        
    }
    
    private void updateEditFields(Store store) {
        
        if (store != null) {
            
            this.editNameTF.setText(getStringValue(store.getName()));
            this.editLocationTF.setText(getStringValue(store.getLocation()));
            
        }
        
    }
    
    private String getStringValue(Object object) {
        
        try {
            
            String className = object.getClass().getSimpleName();
        
            if (className.equalsIgnoreCase("Integer")) {

                return ((Integer) object).toString();

            }
            
        } catch (NullPointerException ex) {
            
            return "Error: " + ex.toString();
            
        }
        
        return object.toString();
        
    }
    
    private <T> T getSelected(TableView<T> tableView) {
        return tableView.getSelectionModel().getSelectedItem();
    }
    
}
