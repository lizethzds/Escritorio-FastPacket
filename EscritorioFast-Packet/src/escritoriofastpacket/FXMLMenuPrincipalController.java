
package escritoriofastpacket;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author lizet
 */
public class FXMLMenuPrincipalController implements Initializable {

    @FXML
    private Label lbNombreColaborador;
    @FXML
    private Label lbNumPColaborador;
    @FXML
    private StackPane stackPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnIrColaboradores(ActionEvent event) throws IOException {
       
    }

    @FXML
    private void btnIrEnvios(ActionEvent event) {
    }

   @FXML
    private void btnIrClientes(ActionEvent event) throws IOException {
    URL resource = getClass().getResource("/escritoriofastpacket/vista/clientes/FXMLAdminClientes.fxml");
    System.out.println("Resource URL: " + resource); // Depuración

    if (resource == null) {
        throw new IOException("FXML file not found at /escritoriofastpacket/vista/clientes/FXMLAdminClientes.fxml");
    }

    Parent loadMain = FXMLLoader.load(resource);
    stackPane.getChildren().clear();
    stackPane.getChildren().add(loadMain);
}



    @FXML
    private void btnIrUnidades(ActionEvent event) {
    }

    @FXML
    private void btnCerrarSesion(ActionEvent event) {
    }
    
}
