package inventory.crud.stock.dialog.adjust.selection;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import inventory.model.Product;
import inventory.model.Store;
import inventory.model.AdjustmentItem;

import java.util.Map;

import static inventory.login.LoginController.crud;
import inventory.util.ValidatorUtil;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author VakSF
 */
public class AdjustSelectionController implements Initializable {
    
    @FXML
    private TableView<Product> producstTV;
    
    @FXML
    private JFXTextField productNameTF, newQuantityTF, 
                oldQuantityTF, differenceQuantityTF;
    
    @FXML
    private JFXComboBox<Store> storesCB;
    
    private Store store;
    private ObservableList<Product> productsList = FXCollections.observableArrayList();
    private Map<String, Integer> storeProductsMap;
    private AdjustmentItem adjustItem;
    
    private ValidatorUtil saveValidator;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    @FXML
    private void saveAdjust(ActionEvent event) {
        
        if (this.saveValidator.validateFields()) {
            
            Product product = this.getSelected(this.producstTV);
            
            AdjustmentItem adjustIt = new AdjustmentItem(
                    this.store, product, 
                    Integer.parseInt(this.oldQuantityTF.getText()),
                    Integer.parseInt(this.newQuantityTF.getText())
            );
            
            if (adjustIt.getNewQuantity() > 0) {
                
                this.adjustItem = adjustIt;
                
                this.close(event);
                
            } else {
                
                new Alert(
                        AlertType.ERROR, 
                        "Por favor introduzca un valor mayor a 0 (cero)"
                ).show();
                
            }
            
        } else {
            this.saveValidator.emptyFields().showAndWait();
        }
        
    }
    
    public AdjustmentItem getAdjustItem() {
        return this.adjustItem;
    }
    
    private void close(ActionEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
    }
    
    public void setMap(Map<String, Integer> storeProductsMap) {
        this.storeProductsMap = storeProductsMap;
    }
    
    public Map<String, Integer> getMap() {
        return this.storeProductsMap;
    }
    
    public void setStore(Store store) {
        
        this.store = store;
        
        try {
            
            List<Product> products = crud.getList(Product.class);
            
            this.productsList.addAll(products);
            this.producstTV.setItems(productsList);
            
        } catch (RemoteException ex) {
            
            System.out.println("Exception: " + ex.toString());
            
        }
        
        this.initCB();
        this.initTV();
        this.initValidator();
        this.initTFListener();
        
    }
    
    private void initValidator() {
        
        this.saveValidator = new ValidatorUtil(
                this.newQuantityTF,
                this.oldQuantityTF,
                this.differenceQuantityTF,
                this.productNameTF,
                this.storesCB
        );
        
    }
    
    private void initTFListener() {
        
        this.newQuantityTF.textProperty().addListener(
                (observable, oldValue, newValue) -> {
                    
            if (!newValue.isEmpty()) {

                if (newValue.matches("\\d*")) {

                    try {

                        Integer newQuantity = Integer.parseInt(newValue);
                        Integer oldQuantity = Integer.parseInt(oldQuantityTF.getText());
                        Integer difference = Math.abs(oldQuantity - newQuantity);

                        differenceQuantityTF.setText(difference.toString());

                    } catch (Exception ex) {

                        newQuantityTF.setText(oldValue);
                        differenceQuantityTF.clear();

                    }

                } else {

                    newQuantityTF.setText(oldValue);

                }

            } else {

                differenceQuantityTF.clear();

            }

        });
        
    }
    
    private void initTV() {
        
        this.producstTV.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, selectedProduct) -> {
        
            if (selectedProduct != null) {
                
                String productName = selectedProduct.getName();
                
                newQuantityTF.clear();
                
                productNameTF.setText(productName);
                
                Integer units = storeProductsMap.get(productName);
                
                if (units != null) {
                    
                    oldQuantityTF.setText(units.toString());
                    
                } else {
                    
                    oldQuantityTF.setText(Integer.toString(0));
                    
                }
                
            }
        });
        
        if (!productsList.isEmpty()) {
            producstTV.getSelectionModel().selectFirst();
        }
        
    }
    
    private void initCB() {
        
        try {
            
            this.storesCB.getItems().addAll(crud.getList(Store.class));
            this.storesCB.setValue(this.store);
            
        } catch (RemoteException ex) {}
        
    }
    
    private <T> T getSelected(TableView<T> tableView) {
        return tableView.getSelectionModel().getSelectedItem();
    }
    
}
