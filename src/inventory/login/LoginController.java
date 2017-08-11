package inventory.login;

import inventory.rmi.interfaces.Adjust;
import java.util.ResourceBundle;
import java.io.IOException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.Scene;
import javafx.scene.Node;

import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import inventory.util.ValidatorUtil;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import inventory.rmi.interfaces.CRUD;

/**
 *
 * @author Arturo Cordero
 */
public class LoginController implements Initializable {
    
    @FXML
    private TextField userTF;
    
    @FXML
    private PasswordField passwordPF;
    
    private ValidatorUtil validator;
    
    public static CRUD crud;
    public static Adjust adjust;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        if (crud == null) 
            LoginController.connectServer();
        
        this.validator = new ValidatorUtil(userTF, passwordPF);
        
    }
    
    public static void connectServer() {
        
        try {
            
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            
            if (crud == null) 
                crud = (CRUD) registry.lookup("crud");
            
            if (adjust == null) 
                adjust = (Adjust) registry.lookup("adjust");
            
            System.out.println("Connected to server");
            
        } catch (RemoteException | NotBoundException ex) {
            
            System.out.println("Error: " + ex.toString());
            
            System.out.println("No hay servidor en ejecución...");
            
            System.exit(0);
            
        }
        
    }
    
    @FXML
    private void fastAccess(KeyEvent event) throws IOException{
        
        if (event.getCode().equals(KeyCode.ENTER)) {
            this.login(new ActionEvent());
        }
    }
    
    @FXML
    private void login(ActionEvent event) throws IOException {
        
        String user = this.userTF.getText().trim().toLowerCase();
        String password = this.passwordPF.getText().trim();
        
        if (this.validator.validateFields()) {
            
            if (crud.login(user, password)) {
                
                this.openFXML(
                        "/inventory/dashboard/DashboardFXML.fxml",
                        "Dashboard - Control de inventario", true
                );
                    
                this.close(event);
                
            } else {
                notFound();
            }
            
        } else {
            this.validator.emptyFields().showAndWait();
        }
        
    }
    
    private void notFound() {
        
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error!");
        alert.setHeaderText("No se ha podido iniciar sesión");
        alert.setContentText("Usuario y/o contraseña incorrectos...");
        alert.showAndWait();
        
    }
    
    private void close(ActionEvent event) {
        ((Node) this.userTF).getScene().getWindow().hide();
    }
    
    private void openFXML(String fxml, String title, boolean flag) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml)); 
       
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(new Scene((Pane) loader.load()));
        
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/inventory/images/small-logo.png")));
        
        if (flag) {
            
            stage.setOnCloseRequest((WindowEvent event) -> {
                
                try {
                    
                    openFXML("/inventory/login/LoginFXML.fxml", "Login", false);
                    
                } catch (IOException ex) {
                    
                }
                
            });
            
        }
        
        stage.setTitle(title);
        stage.show();
    }
    
}
