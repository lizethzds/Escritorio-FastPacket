
package escritoriofastpacket;

import escritoriofastpacket.modelo.pojo.Colaborador;
import escritoriofastpacket.observer.INotificacionCambio;
import escritoriofastpacket.utils.Utilidades;
import escritoriofastpacket.vista.colaboradores.FXMLAdminColaboradoresController;
import escritoriofastpacket.vista.envios.FXMLAdminEnviosController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author lizet
 */
public class FXMLMenuPrincipalController implements Initializable, INotificacionCambio {
    
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
    controller.inicializarDatos(colaboradorSesion,this);
    stackPane.getChildren().clear();
    stackPane.getChildren().add(loadMain);
}


    @FXML
private void btnIrEnvios(ActionEvent event) throws IOException {
    URL fxml = getClass().getResource("/escritoriofastpacket/vista/envios/FXMLAdminEnvios.fxml");

    FXMLLoader loader = new FXMLLoader(fxml);
    Parent loadMain = loader.load();
    FXMLAdminEnviosController controller = loader.getController();

    if (colaboradorSesion != null) {
        controller.cargarColaboradorSesion(colaboradorSesion.getIdColaborador());
    }
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
        
        boolean cierraSesion = Utilidades.mostrarAlertaConfirmacion("Cerrar sesión", "¿Está seguro que desea cerrar sesión?");
        if(cierraSesion){
        try{
            Stage escenario = (Stage)lbNombreColaborador.getScene().getWindow();
            FXMLLoader loadVista = new FXMLLoader(getClass().getResource("FXMLInicioSesion.fxml"));
            Parent vista = loadVista.load();
            FXMLInicioSesionController controlador = loadVista.getController();
            
            Scene escena = new Scene(vista);
            escenario.setScene(escena);
            escenario.show();
            
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    }

    void inicializarMenuGeneral(Colaborador colaboradorSesion) {
        this.colaboradorSesion = colaboradorSesion;
        lbNombreColaborador.setText(colaboradorSesion.getNombre() +" "+ colaboradorSesion.getApellidoPaterno() + " "+colaboradorSesion.getApellidoMaterno());
        lbNumPColaborador.setText(colaboradorSesion.getNoPersonal());
    }

    
    @Override
    public void notificarCambioColaboradorSesion(Colaborador cambio){
        System.err.println("Notifico");
        if(cambio.getIdColaborador().equals(this.colaboradorSesion.getIdColaborador())){  
            System.out.println("entro al if");
            this.colaboradorSesion = cambio;
            lbNombreColaborador.setText(colaboradorSesion.getNombre() +" "+ colaboradorSesion.getApellidoPaterno() + " "+colaboradorSesion.getApellidoMaterno());
            lbNumPColaborador.setText(colaboradorSesion.getNoPersonal());
        }
    }
}
