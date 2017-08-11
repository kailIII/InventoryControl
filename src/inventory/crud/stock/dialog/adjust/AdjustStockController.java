package inventory.crud.stock.dialog.adjust;

import inventory.crud.stock.dialog.adjust.selection.AdjustSelectionController;
import inventory.model.Store;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import static inventory.login.LoginController.crud;

import inventory.model.Adjustment;
import inventory.model.AdjustmentItem;

/**
 *
 * @author VakSF
 */
public class AdjustStockController implements Initializable {
    
    @FXML
    private TableView<AdjustmentItem> adjusmenttTV;
    
    @FXML
    private TextArea observationTA;
    
    @FXML
    private TextField adjustmentTF, dateTF, statusTF;
    
    private ObservableList<AdjustmentItem> adjustmentList = FXCollections.observableArrayList();
    
    private Map<String, Integer> storeProductsMap = new HashMap<>();
    
    private Store store;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        this.adjusmenttTV.setItems(adjustmentList);
        
        this.dateTF.setText(
                new SimpleDateFormat("dd/M/yyyy").format(new Date())
        );
        
    }
    
    public void setStore(Store store) {
        
        this.store = store;
        
        this.store.getStoreProducts().forEach((storeProduct) -> {
            
            Integer units = storeProduct.getUnits();
            String productName = storeProduct.getProduct().getName();
            
            this.storeProductsMap.put(productName, units);
            
        });
        
    }
    
    @FXML
    private void openAdjustSelection() throws IOException {
        
        FXMLLoader stockLoader = new FXMLLoader(getClass().getResource(
                "/inventory/crud/stock/dialog/adjust/selection/AdjustSelectionFXML.fxml"
        ));
        
        Stage selectionStage = new Stage();
        selectionStage.setScene(new Scene((Pane) stockLoader.load()));
        
        selectionStage.setTitle("Crear un ajuste");
        
        AdjustSelectionController selectionController =
                stockLoader.<AdjustSelectionController>getController();
        
        selectionController.setMap(this.storeProductsMap);
        selectionController.setStore(this.store);
        
        selectionStage.setOnHidden((event) -> {
            
            AdjustmentItem adjustItem = selectionController.getAdjustItem();
            
            if (adjustItem != null) {
                
                String productName = adjustItem.getProduct().getName();
                Integer unitsInteger = this.storeProductsMap.get(productName);
                
                int units = unitsInteger != null ? unitsInteger : 0;
                int newQuantity = (int) adjustItem.getNewQuantity();
                
                adjustItem.setTotalQuantity(units + newQuantity);
                
                this.storeProductsMap.put(
                        productName, 
                        adjustItem.getTotalQuantity()
                );
                
                this.adjustmentList.add(adjustItem);
                
            }
            
        });
        
        selectionStage.showAndWait();
        
    }
    
    @FXML
    private void saveAdjust(ActionEvent event) throws RemoteException {
        
        String code = this.adjustmentTF.getText();
        
        if (code.isEmpty()) {
            
            int id = crud.getLastId("adjustment");
            
            code = "SA".concat("-").concat(String.format("%06d", ++id));
        }
        
        Adjustment adjustment = new Adjustment(
                code, new Date(), "Completado", 
                this.observationTA.getText()
        );
        
        this.adjustmentTF.setText(code);
        this.statusTF.setText("Completado");
        
        adjustment.getItems().addAll(this.adjustmentList);
        
        crud.save(adjustment);
        
    }
    
}
