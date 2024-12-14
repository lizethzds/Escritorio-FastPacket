
package escritoriofastpacket;

import escritoriofastpacket.modelo.pojo.Colaborador;
import escritoriofastpacket.vista.colaboradores.FXMLAdminColaboradoresController;
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
    
    Colaborador colaboradorSesion;

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
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/escritoriofastpacket/vista/colaboradores/FXMLAdminColaboradores.fxml"));
    Parent loadMain = loader.load();
    FXMLAdminColaboradoresController controller = loader.getController();
    controller.inicializarDatos(colaboradorSesion);
    stackPane.getChildren().clear();
    stackPane.getChildren().add(loadMain);
}


    @FXML
    private void btnIrEnvios(ActionEvent event) throws IOException {
    URL fxml = getClass().getResource("/escritoriofastpacket/vista/envios/FXMLAdminEnvios.fxml");

    Parent loadMain = FXMLLoader.load(fxml);
    stackPane.getChildren().clear();
    stackPane.getChildren().add(loadMain);
        
    }

   @FXML
    private void btnIrClientes(ActionEvent event) throws IOException {
    URL fxml = getClass().getResource("/escritoriofastpacket/vista/clientes/FXMLAdminClientes.fxml");

    Parent loadMain = FXMLLoader.load(fxml);
    stackPane.getChildren().clear();
    stackPane.getChildren().add(loadMain);
}



    @FXML
    private void btnIrUnidades(ActionEvent event) throws IOException {
    URL fxml = getClass().getResource("/escritoriofastpacket/vista/unidades/FXMLAdminUnidades.fxml");

    Parent loadMain = FXMLLoader.load(fxml);
    stackPane.getChildren().clear();
    stackPane.getChildren().add(loadMain);
    }

    @FXML
    private void btnCerrarSesion(ActionEvent event) {
    }

    void inicializarMenuGeneral(Colaborador colaboradorSesion) {
        this.colaboradorSesion = colaboradorSesion;
        lbNombreColaborador.setText(colaboradorSesion.getNombre() +" "+ colaboradorSesion.getApellidoPaterno() + " "+colaboradorSesion.getApellidoMaterno());
        lbNumPColaborador.setText(colaboradorSesion.getNoPersonal());
    }
    
}
