package inventory.crud.product;

import inventory.crud.product.barcode.ProductBarcodeController;
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
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParsePosition;
import java.util.Locale;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import inventory.model.Product;
import inventory.util.ValidatorUtil;

import inventory.model.Category;
import inventory.model.Supplier;
import java.rmi.RemoteException;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.output.OutputException;
import static inventory.login.LoginController.crud;

public class ProductController implements Initializable {

    @FXML
    private TabPane accordion;
    
    @FXML
    private Tab createTab, editTab;
    
    @FXML
    private TableView<Product> productsTV;
    
    @FXML
    private TableColumn<Product, String> codeTC;
    
    @FXML
    private TextField nameTF, descriptionTF,
            priceTF, brandTF;
    
    @FXML
    private TextField editNameTF, editDescriptionTF,
            editPriceTF, editBrandTF;
    
    @FXML
    private ComboBox<Category> categoryCB, editCategoryCB;
    
    @FXML
    private ComboBox<Supplier> supplierCB, editSupplierCB;
    
    private ObservableList<Product> productsList;
    
    private ValidatorUtil createValidator, updateValidator;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        if (crud == null) 
            LoginController.connectServer();
        
        this.initValidators();
        this.initTV();
        this.initAll();
        this.initCBs();
        
    }
    
    @FXML
    private void makeFloat() throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/inventory/crud/product/ProductFXML.fxml"
        ));

        Stage stage = new Stage();
        stage.setScene(new Scene((Pane) loader.load()));
        
        loader.setController(this);
        
        stage.setTitle("Establecer compañia");
        
        stage.showAndWait();
        
    }
    
    private void initValidators() {
        
        this.createValidator = new ValidatorUtil(
                nameTF, descriptionTF, priceTF, 
                brandTF, categoryCB, supplierCB
        );
        
        this.updateValidator = new ValidatorUtil(
                editNameTF, editDescriptionTF, editPriceTF, 
                editBrandTF, editCategoryCB, editSupplierCB
        );
        
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("es_ES"));
        symbols.setDecimalSeparator('.');
        symbols.setGroupingSeparator(','); 
        
        DecimalFormat format = new DecimalFormat("#.0", symbols);

        this.priceTF.setTextFormatter(new TextFormatter<>(value -> {
            
            if (value.getControlNewText().isEmpty()){
                return value;
            }
            
            ParsePosition parsePosition = new ParsePosition(0);
            
            Object object = format.parse(value.getControlNewText(), parsePosition);
            
            if (object == null || parsePosition.getIndex() < value.getControlNewText().length()){
                return null;
            } else {
                return value;
            }
            
        }));
        
    }
    
    private void initTV() {
        
        try {
            
            this.productsList = FXCollections.observableArrayList();
            this.productsTV.setItems(this.productsList);
            
            this.productsList.setAll(crud.getList(Product.class));
            
            this.codeTC.setCellFactory(new PropertyValueFactory("code"));
            
            Callback<TableColumn<Product, String>, TableCell<Product, String>> cellFactory =
                    (TableColumn<Product, String> value) -> {
                        
                        TableCell<Product, String> cell = new TableCell<Product, String>() {
                            
                            @Override
                            protected void updateItem(String item, boolean empty) {
                                
                                super.updateItem(item, empty);
                                
                                Button button = new Button("Ver código");
                                
                                if (empty) {
                                    
                                    super.setGraphic(null);
                                    
                                    super.setText(null);
                                    
                                } else {
                                    
                                    button.setOnAction((ActionEvent event) -> {
                                        
                                        Product product = getTableView().getItems().get(
                                                super.getIndex()
                                        );
                                        
                                        FXMLLoader stockLoader = new FXMLLoader(getClass().getResource(
                                                "/inventory/crud/product/barcode/ProductBarcodeFXML.fxml"
                                        ));
                                        
                                        Stage barcodeStage = new Stage();
                                        
                                        try {
                                            
                                            barcodeStage.setScene(new Scene((Pane) stockLoader.load()));
                                            
                                            barcodeStage.setTitle("Ajuste de inventario");
                                            
                                            ProductBarcodeController barcodeController =
                                                    stockLoader.<ProductBarcodeController>getController();
                                            
                                            barcodeController.setProduct(product);
                                            
                                            barcodeStage.setResizable(false);
                                            
                                            barcodeStage.setOnHidden((barcodeEvent) -> {
                                                
                                                System.out.println("And now I'm here");
                                                
                                            });
                                            
                                            barcodeStage.showAndWait();
                                            
                                        } catch (IOException | BarcodeException | OutputException ex) {
                                            
                                            System.out.println("Error: " + ex.toString());
                                            
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
            
            this.codeTC.setCellFactory(cellFactory);
            
            /*
            
            Random random = new Random();
            
            for (int i = 0; i < 100; i++) {
            this.productsList.add(new Product(
            i, Integer.toString(random.nextInt(100 - 1) + 1),
            "Descripcion " + i, "Nombre " + i, this.truncate(random.nextDouble() * 1000, 2).doubleValue(),
            "Marca " + i, random.nextInt(10 - 1) + 1, random.nextInt(200 - 180) + 180
            ));
            }
            
            */
            
            if (!this.productsList.isEmpty()) {
                this.productsTV.getSelectionModel().selectFirst();
            }
            
            this.productsTV.getSelectionModel().selectedItemProperty()
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
                            
                            if (!this.productsList.isEmpty()) {
                                
                                Product product = this.getSelected(this.productsTV);
                                
                                this.updateEditFields(product);
                                        
                            }
                            
                        }
                        
                    }
                    
                }
                
            });
    }
    
    private void initCBs() {
        
        try {
            
            List<Category> categories = crud.getList(Category.class);
            List<Supplier> suppliers = crud.getList(Supplier.class);
            
            this.categoryCB.getItems().addAll(categories);
            this.editCategoryCB.getItems().addAll(categories);
            
            this.supplierCB.getItems().addAll(suppliers);
            this.editSupplierCB.getItems().addAll(suppliers);
            
            if (!categories.isEmpty()) {
                this.categoryCB.getSelectionModel().selectFirst();
                this.editCategoryCB.getSelectionModel().selectFirst();
            }
            
            if (!suppliers.isEmpty()) {
                this.supplierCB.getSelectionModel().selectFirst();
                this.editSupplierCB.getSelectionModel().selectFirst();
            }
        } catch (RemoteException ex) {
            
            System.out.println("Exception: " + ex.toString());
            
        }
        
    }
    
    @FXML
    private void save() throws BarcodeException, OutputException, RemoteException {
        
        if (this.createValidator.validateFields()) {
            
            String name = this.nameTF.getText();
            String description = this.descriptionTF.getText();
            double price = Double.parseDouble(this.priceTF.getText());
            String brand = this.brandTF.getText();
            Category category = this.categoryCB.getValue();
            Supplier supplier = this.supplierCB.getValue();
            
            String code = name.concat(" - ").concat(description);
            
            Product product = new Product(
                    code, description, name, price, brand, category, supplier
            );
            
            Integer savedId = crud.save(product);

            if (savedId != null) {
                
                product.setId(savedId);
                
                this.productsList.add(product);
                this.createValidator.clearFields();

                new Alert(
                        Alert.AlertType.INFORMATION,
                        "Producto almacenado correctamente"
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
            String description = this.editDescriptionTF.getText();
            double price = Double.parseDouble(this.editPriceTF.getText());
            String brand = this.editBrandTF.getText();
            Category category = this.editCategoryCB.getValue();
            Supplier supplier = this.editSupplierCB.getValue();
            
            Product updateProduct = new Product(
                    description, name, price, brand, category, supplier
            );
                            
            Product product = this.getSelected(this.productsTV);
            
            updateProduct.setCode(product.getCode());
            
            product.setProduct(updateProduct);
            
            boolean updated = crud.update(product);

            if (updated) {

                this.productsTV.refresh();                        
                this.createValidator.clearFields();

                new Alert(
                        Alert.AlertType.INFORMATION,
                        "Producto editado correctamente"
                ).show();

            } else {
                
                System.out.println("Here");
                
            }
                
        } else {
            
            this.updateValidator.emptyFields().showAndWait();
            
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
                
                Product product = this.getSelected(this.productsTV);
                
                this.updateEditFields(product);
                
            } else {

                if (item.equalsIgnoreCase("Eliminar")) {
                    
                    this.deleteUser();
                    
                } else {
                    
                    if (item.equalsIgnoreCase("Cancelar")) {
                        
                        this.createValidator.clearFields();
                        this.updateValidator.clearFields();
                        
                        this.productsTV.getSelectionModel().selectFirst();
                       
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
        
        Product product = this.getSelected(this.productsTV);
        
        if (product != null) {
            
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            
            confirmation.setTitle("Confirmacion de eliminación");
            confirmation.setHeaderText("Está seguro?");
            confirmation.setContentText(
                    "Desea eliminar el producto " + product + "?"
            );
            
            Optional<ButtonType> option = confirmation.showAndWait();
            
            if (option.get() == ButtonType.OK) {
                
                try {
                    
                    crud.delete(product);
                    
                    this.productsList.remove(product);
                    
                    if (this.productsList.isEmpty()) {
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
    
    private void updateEditFields(Product product) {
        
        if (product != null) {
            
            this.editNameTF.setText(getStringValue(product.getName()));
            this.editDescriptionTF.setText(getStringValue(product.getDescription()));
            this.editPriceTF.setText(getStringValue(Double.toString(product.getUnitPrice())));
            this.editBrandTF.setText(getStringValue(product.getBrand()));
            this.editCategoryCB.setValue(product.getCategory());
            this.editSupplierCB.setValue(product.getSupplier());
            
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
