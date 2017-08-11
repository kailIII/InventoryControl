package inventory.crud.product.barcode;

import inventory.model.Product;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import net.sourceforge.barbecue.output.OutputException;

/**
 *
 * @author VakSF
 */
public class ProductBarcodeController implements Initializable {
    
    @FXML
    private ImageView barcodeIV;
    
    @FXML
    private Label productLabel;
    
    private Product product;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public void setProduct(Product product) throws BarcodeException, OutputException {
        
        this.product = product;
        this.productLabel.setText(product.getName());
        
        Barcode barcode = BarcodeFactory.createCode128A(this.product.getCode());
        
        this.barcodeIV.setImage(
                SwingFXUtils.toFXImage(BarcodeImageHandler.getImage(barcode), null)
        );
        
    }
    
}
