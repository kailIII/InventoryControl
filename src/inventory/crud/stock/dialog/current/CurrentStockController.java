package inventory.crud.stock.dialog.current;

import java.net.URL;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ResourceBundle;
import java.util.List;

import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

import javafx.beans.property.SimpleStringProperty;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableCell;

import javafx.util.Callback;

import inventory.model.StoreProduct;
import inventory.model.Store;

import inventory.login.LoginController;
import static inventory.login.LoginController.crud;

/**
 *
 * @author VakSF
 */
public class CurrentStockController implements Initializable {
    
    @FXML
    private TableView<StoreProduct> storeProductsTV;
    
    @FXML
    private TableColumn<StoreProduct, String> nameTC, categoryTC,
            quantityTC, descriptionTC, supplierTC, detailsTC;
    
    private ObservableList<StoreProduct> storeProductsList;
    
    private Store store;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public void setStore(Store store) {
        
        if (crud == null)
            LoginController.connectServer();
        
        this.store = store;
        
        this.storeProductsList = FXCollections.observableArrayList();
        
        List<StoreProduct> storeProducts = this.store.getStoreProducts();
        storeProductsList.addAll(storeProducts);
        
        this.initTV();
        this.initTableColumns();
        
    }
    
    private void initTV() {
        
        this.storeProductsTV.setItems(this.storeProductsList);
        
        if (!this.storeProductsList.isEmpty()) {
            this.storeProductsTV.getSelectionModel().selectFirst();
        }
        
        this.detailsTC.setCellFactory(new PropertyValueFactory("details"));
        
        Callback<TableColumn<StoreProduct, String>, TableCell<StoreProduct, String>> cellFactory =
                (TableColumn<StoreProduct, String> value) -> {
                    
                    TableCell<StoreProduct, String> cell = new TableCell<StoreProduct, String>() {
                        
                        @Override
                        protected void updateItem(String item, boolean empty) {
                            
                            Button button = new Button("Ver detalles");
                            
                            super.updateItem(item, empty);
                            
                            if (empty) {
                                
                                super.setGraphic(null);
                                
                                super.setText(null);
                                
                            } else {
                                
                                button.setOnAction((ActionEvent event) -> {
                                    
                                    StoreProduct stockProduct = getTableView().getItems().get(
                                            super.getIndex()
                                    );
                                    
                                    System.out.println(stockProduct.toString());
                                    
                                });
                                
                                super.setGraphic(button);
                                
                                super.setAlignment(Pos.CENTER);
                                
                                super.setText(null);
                                
                            }
                            
                        }
                        
                    };
                    
                    return cell;
                    
                };
        
        this.detailsTC.setCellFactory(cellFactory);
        
    }
    
    private void initTableColumns() {
        
        this.nameTC.setCellValueFactory((value) -> {
            
            StoreProduct stockProduct = value.getValue();
            
            return new SimpleStringProperty(stockProduct.getProduct().getName());
        });
        
        this.descriptionTC.setCellValueFactory((value) -> {
            
            StoreProduct stockProduct = value.getValue();
            
            return new SimpleStringProperty(stockProduct.getProduct().getDescription());
        });
        
        this.categoryTC.setCellValueFactory((value) -> {
            
            StoreProduct stockProduct = value.getValue();
            
            return new SimpleStringProperty(stockProduct.getProduct().getCategory().getName());
            
        });
        
        this.supplierTC.setCellValueFactory((value) -> {
            
            StoreProduct stockProduct = value.getValue();
            
            return new SimpleStringProperty(stockProduct.getProduct().getSupplier().getCompanyName());
            
        });
        
        this.quantityTC.setCellValueFactory((value) -> {
            
            StoreProduct stockProduct = value.getValue();
            
            return new SimpleStringProperty(stockProduct.getUnits().toString());
            
        });
        
    }
    
}
