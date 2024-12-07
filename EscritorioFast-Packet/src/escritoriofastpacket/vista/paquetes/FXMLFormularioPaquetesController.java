
package escritoriofastpacket.vista.paquetes;

import escritoriofastpacket.interfaz.INotificarOperacion;
import escritoriofastpacket.modelo.dao.PaqueteDAO;
import escritoriofastpacket.modelo.pojo.Mensaje;
import escritoriofastpacket.modelo.pojo.Paquete;
import escritoriofastpacket.utils.Utilidades;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author lizet
 */
public class FXMLFormularioPaquetesController implements Initializable {
    
    private INotificarOperacion observador;
    private Paquete paqueteEditado;
    private Integer idEnvio;
    private boolean modoEdicion = false;
    

    @FXML
    private TextArea taDescripcionPaquete;
    @FXML
    private TextField tfAlto;
    @FXML
    private TextField tfAncho;
    @FXML
    private TextField tfProfundidad;
    @FXML
    private TextField tfPeso;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnGuardarDatos(ActionEvent event) {
       
        String altura = tfAlto.getText();
        String ancho = tfAncho.getText();
        String profundidad = tfProfundidad.getText();
        String descripcion = taDescripcionPaquete.getText();
        String peso = tfPeso.getText();
        //Debo pasar el idEnvio para que el paquete lo reciba correctamente.
        Integer idEnvio = this.idEnvio;
        
        Paquete paquete = new Paquete();
        paquete.setAltura(Integer.parseInt(altura));
        paquete.setAncho(Integer.parseInt(ancho));
        paquete.setProfundidad(Integer.parseInt(profundidad));
        paquete.setPeso(Float.parseFloat(peso));
        paquete.setDescripcion(descripcion);
        paquete.setIdEnvio(idEnvio);
        
        //Agregar validacion de campos
        if(!modoEdicion){
            guardarDatos(paquete);
        }else{
            paquete.setIdPaquete(paqueteEditado.getIdPaquete());
            editarDatos(paquete);
        }
       
        
    }

    @FXML
    private void btnCancelar(ActionEvent event) {
    }
    
    
    
    public void inicializarValores(INotificarOperacion observador, Paquete paqueteEditado, Integer idEnvio){
        this.observador = observador;
        this.paqueteEditado = paqueteEditado;
        this.idEnvio = idEnvio;
        if(paqueteEditado != null){
            modoEdicion = true;
            cargarDatosPaquete();
        }
    }

    private void cargarDatosPaquete() {
        tfAlto.setText(this.paqueteEditado.getAltura().toString());
        tfAncho.setText(this.paqueteEditado.getAncho().toString());
        tfProfundidad.setText(this.paqueteEditado.getProfundidad().toString());
       tfPeso.setText(String.valueOf(this.paqueteEditado.getPeso()));
        taDescripcionPaquete.setText(this.paqueteEditado.getDescripcion());
    
    }
    
    
    private void cerrarVentana(){
        ((Stage) tfAlto.getScene().getWindow()).close();
    }
    
    
    private void guardarDatos(Paquete paquete){
       Mensaje msj  = PaqueteDAO.registrarPaquete(paquete);
       
       if(!msj.isError()){
           Utilidades.mostrarAlertaSimple("Paquete registrado ", msj.getContenido(), Alert.AlertType.INFORMATION);
            cerrarVentana();
            observador.notificarOperacionExitosa("Guardado", "Paquete");
       }else{
           Utilidades.mostrarAlertaSimple("Error al guardar", msj.getContenido(), Alert.AlertType.ERROR);
       }
        
    }
    
    private void editarDatos(Paquete paquete){
    Mensaje msj  = PaqueteDAO.editarPaquete(paquete);
       
       if(!msj.isError()){
           Utilidades.mostrarAlertaSimple("Paquete actualizado ", msj.getContenido(), Alert.AlertType.INFORMATION);
            cerrarVentana();
            observador.notificarOperacionExitosa("Actualizacion ", "Paquete");
       }else{
           Utilidades.mostrarAlertaSimple("Error al guardar", msj.getContenido(), Alert.AlertType.ERROR);
       }
    
    }
            
            
}
