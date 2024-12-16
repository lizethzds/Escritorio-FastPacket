
package escritoriofastpacket;

import escritoriofastpacket.modelo.dao.AutenticacionDAO;
import escritoriofastpacket.modelo.pojo.Colaborador;
import escritoriofastpacket.modelo.pojo.Login;
import escritoriofastpacket.utils.Utilidades;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author lizet
 */
public class FXMLInicioSesionController implements Initializable {
    
    Colaborador colaboradorSesion = new Colaborador();

    @FXML
    private Label lbErrorNoPersonal;
    @FXML
    private Label lbErrorPassword;
    @FXML
    private TextField tfNumPersonal;
    @FXML
    private PasswordField tfPassword;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnIngresar(ActionEvent event) {
        String noPersonal = tfNumPersonal.getText().trim();
        String password = tfPassword.getText().trim();
        
        if(validarCampos(noPersonal, password)){
            verificarCredenciales(noPersonal, password);
        }
    }

    private boolean validarCampos(String noPersonal, String password) {
        boolean camposValidos = true;
        lbErrorNoPersonal.setText("");
        lbErrorPassword.setText("");
        
        if(noPersonal.isEmpty()){
            camposValidos = false;
            lbErrorNoPersonal.setText("Numero de personal requerido.");
        }
        
        if(password.isEmpty()){
            camposValidos = false;
            lbErrorPassword.setText("Contraseña requerida.");
        }
       
        return camposValidos;
    }
    
    

    private void verificarCredenciales(String noPersonal, String password) {
        Login respuesta = AutenticacionDAO.inciarSesionColaborador(noPersonal, password);
        this.colaboradorSesion = respuesta.getColaboradorSesion();
        
        
        if(!respuesta.isError()){
            Utilidades.mostrarAlertaSimple("Acceso concedido", respuesta.getMensaje(), Alert.AlertType.INFORMATION);
            irPantallaPrincipal(colaboradorSesion);
        }else{
            Utilidades.mostrarAlertaSimple("Acceso denegado", respuesta.getMensaje(), Alert.AlertType.INFORMATION);
        
        }
        
        
    }
    
    private void irPantallaPrincipal(Colaborador colaborador){
    try {
            Stage escenarioBase = (Stage) tfNumPersonal.getScene().getWindow();
            FXMLLoader cargadorVista = new FXMLLoader(getClass().getResource("FXMLMenuPrincipal.fxml"));
            Parent principal = cargadorVista.load();

            // Obtener el controlador asociado
            FXMLMenuPrincipalController controladorMenu = cargadorVista.getController();
            // Llamar al método del controlador para cargar el colaborador
            controladorMenu.inicializarMenuGeneral(colaborador); // Asegúrate de tener el objeto `colaborador` definido

            Scene escenaPrincipal = new Scene(principal);

            // Configurar la ventana
            escenarioBase.setTitle("Menú Principal");
            escenarioBase.setScene(escenaPrincipal);
            escenarioBase.setResizable(false);
            escenarioBase.setMaxWidth(escenarioBase.getWidth());
            escenarioBase.show();

            
        } catch (IOException ex) {
            Utilidades.mostrarAlertaSimple("Error", "No se pudo cargar la pantalla de inicio", Alert.AlertType.ERROR);
            
        }
        
    
    }
    
}
