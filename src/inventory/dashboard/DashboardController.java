package inventory.dashboard;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import inventory.dialog.store.SelectStoreController;
import inventory.crud.stock.StockController;

import inventory.model.Store;
import javafx.stage.Modality;

/**
 *
 * @author Arturo Cordero
 */
public class DashboardController implements Initializable {
    
    @FXML
    private AnchorPane viewerAP;
    
    private AnchorPane homeAP, productAP, inventoryAP, userAP;
    
    private AnchorPane currentAP;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.openHome();
        this.currentAP = this.homeAP;
    }
    
    @FXML
    private void openHome() {
        
        this.fade(this.currentAP, "out");
        
        this.currentAP = this.homeAP;
        
        if (this.homeAP == null) {
            
            try {
                
                this.homeAP = this.loadFXML(
                        "/inventory/dashboard/home/HomeFXML.fxml",
                        this.homeAP
                );
                
            } catch (IOException ex) {
                
            }
            
        } else {
            
            this.fade(this.homeAP, "in");
            
            this.viewerAP.getChildren().setAll(this.homeAP);
            
        }
        
    }
    
    @FXML
    private void openProducts() throws IOException {
        
        this.fade(this.currentAP, "out");
        
        this.currentAP = this.productAP;
        
        if (this.productAP == null) {
            
            this.productAP = this.loadFXML(
                    "/inventory/crud/product/ProductFXML.fxml", 
                    this.productAP
            );
            
        } else {
            
            this.fade(this.productAP, "in");
            
            this.viewerAP.getChildren().setAll(this.productAP);
            
        }
        
    }
    
    @FXML
    private void openInventory() throws IOException {
        
        FXMLLoader storeLoader = new FXMLLoader(getClass().getResource(
                "/inventory/dialog/store/SelectStoreFXML.fxml"
        ));

        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(new Scene((Pane) storeLoader.load()));
        stage.setTitle("Tiendas");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        
        SelectStoreController selectStoreController = 
                storeLoader.<SelectStoreController>getController();
       
        stage.getIcons().add(new Image(getClass().getResourceAsStream(
                "/inventory/images/small-logo.png")
        ));
        
        stage.setOnHidden((event) -> {
            
            try {
                
                Store selectedStore = selectStoreController.getSelectedStore();
                
                if (selectedStore != null) {
                    
                    this.fade(this.currentAP, "out");
                    this.currentAP = this.inventoryAP;
                    
                    if (this.inventoryAP == null) {
                        
                        System.out.println("here");

                        FXMLLoader stockLoader = new FXMLLoader(getClass().getResource(
                                "/inventory/crud/stock/StockFXML.fxml"
                        ));
                        
                        Stage stockStage = new Stage();
                        stockStage.setScene(new Scene((Pane) stockLoader.load()));
                        
                        stockStage.setTitle("Elegir acción");

                        StockController inventoryController = 
                                stockLoader.<StockController>getController();

                        inventoryController.setStore(selectedStore);
                        
                        stockStage.setOnHidden((stockEvent) -> {
                            
                            System.out.println("And now I'm here");
                            
                        });
                        
                        stockStage.showAndWait();
                        
                        /*
                        this.setAnchor(this.inventoryAP);

                        this.fade(this.inventoryAP, "in");

                        this.viewerAP.getChildren().setAll(this.inventoryAP);
                        */

                    } else {

                        this.fade(this.inventoryAP, "in");
                        this.viewerAP.getChildren().setAll(this.inventoryAP);

                    }
                    
                }
                
            } catch (IOException ex) {
                
                System.out.println("Error: " + ex.toString());
            }
            
        });
        
        stage.showAndWait();
        
    }
    
    @FXML
    private void openUsers() throws IOException {
        
        this.fade(this.currentAP, "out");
        
        this.currentAP = this.userAP;
        
        if (this.userAP == null) {
            
            this.userAP = this.loadFXML(
                    "/inventory/crud/user/UserFXML.fxml", 
                    this.userAP
            );
            
        } else {
            
            this.fade(this.userAP, "in");
            
            this.viewerAP.getChildren().setAll(this.userAP);
            
        }
        
    }
    
    private AnchorPane loadFXML(String fxml, AnchorPane anchorPane) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        
        anchorPane = (AnchorPane) loader.load();
        
        this.setAnchor(anchorPane);
        
        this.fade(anchorPane, "in");

        this.viewerAP.getChildren().setAll(anchorPane);
        
        return anchorPane;
        
    }
    
    private void setAnchor(AnchorPane anchorPane) {
        AnchorPane.setTopAnchor(anchorPane, 0.0);
        AnchorPane.setBottomAnchor(anchorPane, 0.0);
        AnchorPane.setLeftAnchor(anchorPane, 0.0);
        AnchorPane.setRightAnchor(anchorPane, 0.0);
    }
    
    private void fade(AnchorPane anchorPane, String type) {
        
        FadeTransition fadeTransition = new FadeTransition(
                Duration.millis(750), anchorPane
        );
        
        if (type.equalsIgnoreCase("in")) {
            
            fadeTransition.setFromValue(0.0);
            fadeTransition.setToValue(1.0);
            
        } else {
            
            if (type.equalsIgnoreCase("out")) {
                
                fadeTransition.setDuration(Duration.millis(400));
                
                fadeTransition.setFromValue(1.0);
                fadeTransition.setToValue(0.0);
                
            }
            
        }
        
        fadeTransition.play();
        
    }
    
    private void fadeOut(AnchorPane anchorPane) {
        
        FadeTransition fadeTransition = new FadeTransition(
                Duration.millis(750), anchorPane
        );
        
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
        
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