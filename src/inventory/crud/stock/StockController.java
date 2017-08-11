package inventory.crud.stock;

import inventory.crud.stock.dialog.adjust.AdjustStockController;
import inventory.crud.stock.dialog.current.CurrentStockController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import inventory.model.Store;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author VakSF
 */
public class StockController implements Initializable {
    
    @FXML
    private Label storeLabel;
    
    private Store store;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public void setStore(Store store) {
        
        this.store = store;
        
        String storeText = String.join(
                " - ", store.getName(), store.getLocation()
        );
        
        this.storeLabel.setText(storeText);
        
    }
    
    @FXML
    private void openCurrentInventory() throws IOException {
        
        FXMLLoader stockLoader = new FXMLLoader(getClass().getResource(
                "/inventory/crud/stock/dialog/current/CurrentStockFXML.fxml"
        ));
        
        Stage currentStockStage = new Stage();
        currentStockStage.setScene(new Scene((Pane) stockLoader.load()));
        
        currentStockStage.setTitle("Inventario actual");
        
        CurrentStockController currentStockController =
                stockLoader.<CurrentStockController>getController();
        
        currentStockController.setStore(this.store);
        
        currentStockStage.setOnHidden((stockEvent) -> {
            
            System.out.println("And now I'm here");
            
        });
        
        currentStockStage.showAndWait();
        
    }
    
    @FXML
    private void openAdjustInventory() throws IOException {
        
        FXMLLoader stockLoader = new FXMLLoader(getClass().getResource(
                "/inventory/crud/stock/dialog/adjust/AdjustStockFXML.fxml"
        ));
        
        Stage ajustStockStage = new Stage();
        ajustStockStage.setScene(new Scene((Pane) stockLoader.load()));
        
        ajustStockStage.setTitle("Ajuste de inventario");
        
        AdjustStockController adjustStockController =
                stockLoader.<AdjustStockController>getController();
        
        adjustStockController.setStore(this.store);
        
        ajustStockStage.setOnHidden((stockEvent) -> {
            
            System.out.println("And now I'm here");
            
        });
        
        ajustStockStage.showAndWait();
        
    }

    public Store getStore() {
        return store;
    }
    
    private Stage openFXML(String fxml, String title) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml)); 
       
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(new Scene((Pane) loader.load()));
        
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/inventory/images/small-logo.png")));
        
        stage.setTitle(title);
        
        return stage;
    }
    
}
