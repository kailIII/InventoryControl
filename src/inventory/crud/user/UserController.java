package inventory.crud.user;

import inventory.crud.user.dialog.StoreDialogController;
import inventory.crud.user.dialog.StoreViewDialogController;
import inventory.login.LoginController;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.math.BigDecimal;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import inventory.util.ValidatorUtil;

import java.rmi.RemoteException;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import inventory.model.User;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.stage.StageStyle;

import static inventory.login.LoginController.crud;
import inventory.model.Store;
import inventory.model.UserStore;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.control.Alert.AlertType;

import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.output.OutputException;

public class UserController implements Initializable {

    @FXML
    private TabPane accordion;
    
    @FXML
    private Tab createTab, editTab;
    
    @FXML
    private TableView<User> usersTV;
    
    @FXML
    private TableColumn<User, String> storeTC;
    
    @FXML
    private TextField usernameTF, editUsernameTF;
    
    @FXML
    private PasswordField passwordPF, rePasswordPF, 
            editPasswordPF, editRePasswordPF;
    
    private ObservableList<User> usersList;
    
    private ValidatorUtil createValidator, updateValidator;
    
    private List<Store> selectedStores = new ArrayList<>();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        if (crud == null) 
            LoginController.connectServer();
        
        this.initValidators();
        this.initTV();
        this.initAll();
        
    }
    
    @FXML
    private void makeFloat() throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/inventory/crud/user/UserFXML.fxml"
        ));

        Stage stage = new Stage();
        stage.setScene(new Scene((Pane) loader.load()));
        
        loader.setController(this);
        
        stage.setTitle("Establecer compañia");
        stage.showAndWait();
        
    }
    
    @FXML
    private void openSelectStores() throws IOException {
        
        String type = null;
        
        Tab selectedItem = this.accordion.getSelectionModel().getSelectedItem();
        
        if (selectedItem != null) {
            
            if (selectedItem.equals(this.editTab)) {
                
                type = "edit";
                
            } else {
                
                if (selectedItem.equals(this.createTab)) {
                    
                    type = "save";
                    
                }
                
            }
            
        }
        
        FXMLLoader storeLoader = new FXMLLoader(getClass().getResource(
                "/inventory/crud/user/dialog/StoreDialogFXML.fxml"
        ));
        
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(new Scene((Pane) storeLoader.load()));
        stage.setTitle("Seleccionar tiendas");
        stage.setResizable(false);
        
        StoreDialogController storeDialogController = 
                storeLoader.<StoreDialogController>getController();
        
        storeDialogController.setUser(this.getSelected(usersTV), type);
        
        stage.getIcons().add(new Image(getClass().getResourceAsStream(
                "/inventory/images/small-logo.png")
        ));
        
        stage.setOnHidden((event) -> {
            
            List<Store> stores = storeDialogController.getStores();
            
            this.selectedStores.clear();
            
            if (!stores.isEmpty()) {
                
                this.selectedStores.addAll(stores);
                
                new Alert(
                        AlertType.INFORMATION, 
                        "La selección de tiendas ha sido establecida. " + 
                        "La cantidad de tiendas seleccionadas es: " + this.selectedStores.size() + ". " + 
                        "Ahora puede terminar el guardado"
                ).showAndWait();
                
            } else {
                
                new Alert(
                        AlertType.WARNING, 
                        "La selección de tiendas se encuentra vacía"
                ).showAndWait();
                
            }
            
        });
        
        stage.showAndWait();
        
    }
    
    private void initValidators() {
        
        this.createValidator = new ValidatorUtil(
                usernameTF, passwordPF, rePasswordPF
        );
        
        this.updateValidator = new ValidatorUtil(
                editUsernameTF, editPasswordPF, editRePasswordPF
        );
        
    }
    
    private void initTV() {
        
        try {
            
            this.usersList = FXCollections.observableArrayList();
            this.usersTV.setItems(this.usersList);
            
            List<User> users = crud.getList(User.class);
            
            this.usersList.setAll(users);
            
            this.storeTC.setCellFactory(new PropertyValueFactory("code"));
            
            Callback<TableColumn<User, String>, TableCell<User, String>> cellFactory =
                    (TableColumn<User, String> value) -> {
                        
                        TableCell<User, String> cell = new TableCell<User, String>() {
                            
                            @Override
                            protected void updateItem(String item, boolean empty) {
                                
                                super.updateItem(item, empty);
                                
                                Button button = new Button("Ver tiendas");
                                
                                if (empty) {
                                    
                                    super.setGraphic(null);
                                    
                                    super.setText(null);
                                    
                                } else {
                                    
                                    button.setOnAction((ActionEvent event) -> {
                                        
                                        try {
                                            
                                            User user = getTableView().getItems().get(
                                                    super.getIndex()
                                            );
                                            
                                            FXMLLoader storeLoader = new FXMLLoader(getClass().getResource(
                                                    "/inventory/crud/user/dialog/StoreViewDialogFXML.fxml"
                                            ));
                                            
                                            Stage stage = new Stage(StageStyle.DECORATED);
                                            stage.setScene(new Scene((Pane) storeLoader.load()));
                                            stage.setTitle("Tiendas");
                                            stage.setResizable(false);
                                            
                                            StoreViewDialogController storeViewDialogController =
                                                    storeLoader.<StoreViewDialogController>getController();
                                            
                                            storeViewDialogController.setUser(user);
                                            
                                            stage.getIcons().add(new Image(getClass().getResourceAsStream(
                                                    "/inventory/images/small-logo.png")
                                            ));
                                            
                                            stage.showAndWait();
                                            
                                        } catch (IOException ex) {
                                            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        
                                        
                                    });
                                    
                                    super.setGraphic(button);
                                    super.setAlignment(Pos.CENTER);
                                    super.setText(null);
                                    
                                }
                                
                            }
                            
                        };
                        
                        return cell;
                        
                    };
            
            this.storeTC.setCellFactory(cellFactory);
            
            
            if (!this.usersList.isEmpty()) {
                this.usersTV.getSelectionModel().selectFirst();
            }
            
            this.usersTV.getSelectionModel().selectedItemProperty()
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
        } catch (RemoteException ex) {
            System.out.println("Exception: " + ex.toString());
        }
        
    }
    
    private void initAll() {
        
        this.accordion.getSelectionModel().select(this.createTab);
        
        this.accordion.getSelectionModel().selectedItemProperty().addListener((
            ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) -> {
            
                if (newValue != null) {
                    
                    String value = newValue.getText();
                    
                    if (value.equalsIgnoreCase("Crear un producto nuevo")) {
                        
                        this.createValidator.clearFields();
                        this.updateValidator.clearFields();
                        
                    } else {
                        
                        if (value.equalsIgnoreCase("Editar un producto existente")) {
                            
                            this.createValidator.clearFields();
                            
                            if (!this.usersList.isEmpty()) {
                                
                                User user = this.getSelected(this.usersTV);
                                
                                this.updateEditFields(user);
                                        
                            }
                            
                        }
                        
                    }
                    
                }
                
            });
    }
    
    @FXML
    private void save() throws BarcodeException, OutputException, IOException {
        
        if (this.createValidator.validateFields()) {
            
            if (this.selectedStores.isEmpty()) {
                
                Optional<ButtonType> option = new Alert(
                        AlertType.CONFIRMATION,
                        "No existe una selección de tiendas, ¿Desea continuar?",
                        ButtonType.CANCEL, ButtonType.OK
                ).showAndWait();
                
                if (option.get() == ButtonType.OK) {
                    
                    this.saveUser();
                    
                } else {
                    
                    option = new Alert(
                            AlertType.CONFIRMATION,
                            "Desea hacer una selección ahora?",
                            ButtonType.CANCEL, ButtonType.OK
                    ).showAndWait();
                    
                    if (option.get() == ButtonType.OK) {
                        
                        this.openSelectStores();
                        
                    }
                    
                }
                
            } else {
                
                this.saveUser();
                
            }
            
        } else {
            this.createValidator.emptyFields().showAndWait();
        }
        
    }
    
    private void saveUser() throws RemoteException {
        
        String username = this.usernameTF.getText();
        String password = this.passwordPF.getText();
        String rePassword = this.rePasswordPF.getText();
        
        if (password.equals(rePassword)) {
            
            User user = new User(username, password);
            Integer saved = crud.save(user);
            
            if (saved != null) {
                
                user.setId(saved);
                
                this.usersList.add(user);
                this.createValidator.clearFields();
                
                this.selectedStores.forEach((store) -> {
                    
                    try {
                        
                        UserStore userStore = new UserStore(
                                user, store
                        );
                        
                        crud.save(userStore);
                        
                        user.getUserStores().add(userStore);
                        
                    } catch (RemoteException ex) {
                        
                        System.out.println("Remote exception: " + ex.toString());
                        
                    }
                });
                
                this.selectedStores.clear();
                
                new Alert(
                        Alert.AlertType.INFORMATION,
                        "Usuario almacenado correctamente"
                ).show();
                
            } else {
                
                new Alert(
                        AlertType.ERROR,
                        "El usuario no ha sido almacenado, " +
                                "al parecer ya se encuentra registrado, intente con otro"
                ).showAndWait();
                
            }
            
        } else {
            
            new Alert(
                    AlertType.ERROR,
                    "Las contraseñas no coinciden, " +
                            "por favor inténtelo de nuevo"
            ).showAndWait();
            
        }
        
    }
    
    @FXML
    private void update() throws RemoteException, IOException {
        
        if (this.updateValidator.validateFields()) {
            
            if (this.selectedStores.isEmpty()) {
                
                ButtonType selectButton = new ButtonType("Crear una selección");
                ButtonType clearButton = new ButtonType("Limpiar la selección");
                ButtonType ignoreButton = new ButtonType("Dejar todo como estaba");
                
                Optional<ButtonType> option = new Alert(
                        AlertType.CONFIRMATION,
                        "No existe una selección de tiendas, ¿Que desea hacer?",
                        selectButton, clearButton, ignoreButton
                ).showAndWait();
                
                ButtonType selectedOption = option.get();
                
                if (selectedOption == ignoreButton) {
                    
                    this.updateUser("none");
                    
                } else {
                    
                    if (selectedOption == clearButton) {
                        
                        this.updateUser("clear");
                        
                    } else {
                        
                        if (selectedOption == selectButton) {
                            
                            this.openSelectStores();
                            
                        }
                        
                    }
                    
                }
                
            } else {
                
                this.updateUser("selection");
                
            }
            
        } else {
            
            this.updateValidator.emptyFields().showAndWait();
            
        }
        
    }
    
    private void updateUser(String action) throws RemoteException {
        
        String username = this.editUsernameTF.getText();
        String password = this.editPasswordPF.getText();
        String rePassword = this.editRePasswordPF.getText();
        
        if (password.equals(rePassword)) {
            
            User updatedUser = new User(username, password);
            
            User user = this.getSelected(this.usersTV);
            
            user.setUser(updatedUser);
            
            boolean updated = crud.update(user);
            
            if (updated) {
                
                this.usersTV.refresh();
                this.createValidator.clearFields();
                
                List<UserStore> userStores = user.getUserStores();
                
                if (action.equalsIgnoreCase("clear")) {
                    
                    for (UserStore userStore : userStores) {
                        crud.delete(userStore);
                    }
                    
                    user.getUserStores().clear();
                    
                } else if (action.equalsIgnoreCase("none")) {
                    
                    // nothing to do
                    
                } else if (action.equalsIgnoreCase("selection")) {
                    
                    for (UserStore userStore : userStores) {
                        crud.delete(userStore);
                    }
                    
                    user.getUserStores().clear();
                    
                    this.selectedStores.forEach((store) -> {
                        
                        try {
                            
                            UserStore userStore = new UserStore(
                                    user, store
                            );
                            
                            crud.save(userStore);
                            
                            user.getUserStores().add(userStore);
                            
                            System.out.println("Tienda guardada: \n" + userStore.toString());
                            
                        } catch (RemoteException ex) {
                            
                            System.out.println("Remote exception: " + ex.toString());
                            
                        }
                    });
                    
                    this.selectedStores.clear();
                    
                }
                
                new Alert(
                        Alert.AlertType.INFORMATION,
                        "Usuario editado correctamente"
                ).show();
                
            } else {
                
                new Alert(
                        AlertType.ERROR,
                        "El usuario no ha sido actualizado, " +
                        "al parecer ya se encuentra registrado, intente con otro"
                ).showAndWait();
                
            }
            
        } else {
            
            new Alert(
                    AlertType.ERROR,
                    "Las contraseñas no coinciden, " +
                    "por favor inténtelo de nuevo"
            ).showAndWait();
            
        }
        
    }
    
    @FXML
    private void context(ActionEvent event) {
        
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
                
                User user = this.getSelected(this.usersTV);
                
                this.updateEditFields(user);
                
            } else {

                if (item.equalsIgnoreCase("Eliminar")) {
                    
                    this.deleteUser();
                    
                } else {
                    
                    if (item.equalsIgnoreCase("Cancelar")) {
                        
                        this.createValidator.clearFields();
                        this.updateValidator.clearFields();
                        
                        this.usersTV.getSelectionModel().selectFirst();
                       
                        this.accordion.getSelectionModel().select(this.createTab);
                        
                    } else {
                        
                        if (item.equalsIgnoreCase("Salir")) {
                            
                            
                            
                        }
                        
                    }
                    
                }

            }

        }
        
    }
    
    private void deleteUser() {
        
        User user = this.getSelected(this.usersTV);
        
        if (user != null) {
            
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            
            confirmation.setTitle("Confirmacion de eliminación");
            confirmation.setHeaderText("Está seguro?");
            confirmation.setContentText(
                    "Desea eliminar el usuario " + user + "?"
            );
            
            Optional<ButtonType> option = confirmation.showAndWait();
            
            if (option.get() == ButtonType.OK) {
                
                try {
                    
                    crud.delete(user);
                    
                    this.usersList.remove(user);
                    
                    if (this.usersList.isEmpty()) {
                        System.out.println("is empty :(");
                    }
                    
                    new Alert(
                            Alert.AlertType.INFORMATION,
                            "Producto eliminado correctamente"
                    ).show();
                    
                } catch (RemoteException ex) {
                    System.out.println("Exception: " + ex.toString());
                }
                
            }
            
        }
        
    }
    
    private void updateEditFields(User user) {
        
        if (user != null) {
            
            this.editUsernameTF.setText(getStringValue(user.getUsername()));
            this.editPasswordPF.setText(getStringValue(user.getPassword()));
            this.editRePasswordPF.setText(getStringValue(user.getPassword()));
            
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
    
    private static BigDecimal truncate(double number, int numberOfDecimals){
        if (number > 0) {
            return new BigDecimal(String.valueOf(number)).setScale(numberOfDecimals, BigDecimal.ROUND_FLOOR);
        } else {
            return new BigDecimal(String.valueOf(number)).setScale(numberOfDecimals, BigDecimal.ROUND_CEILING);
        }
    }
    
}
