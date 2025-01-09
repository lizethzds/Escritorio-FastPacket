
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
import javafx.scene.control.Label;
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
    @FXML
    private Label lbErrorDimensiones;
    @FXML
    private Label lbErrorPeso;
    @FXML
    private Label lbErrorDescripcion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarDatosEntrada();
    }    

    @FXML
    private void btnGuardarDatos(ActionEvent event) {
        
        if (!validarCampos()) {
        return;
    }
       
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
        cerrarPantalla();
    }
    
    private void cerrarPantalla() {
        ((Stage) tfProfundidad.getScene().getWindow()).close();
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
    
    
  private boolean validarCampos() {
    boolean camposValidos = true;

    if (tfAlto.getText() == null || tfAlto.getText().trim().isEmpty() ||
        tfAncho.getText() == null || tfAncho.getText().trim().isEmpty() ||
        tfProfundidad.getText() == null || tfProfundidad.getText().trim().isEmpty()) {
        lbErrorDimensiones.setText("Por favor, complete Altura, Ancho y Profundidad.");
        camposValidos = false;
    } else {
        lbErrorDimensiones.setText(""); 
    }

    
    if (tfPeso.getText() == null || tfPeso.getText().trim().isEmpty()) {
        lbErrorPeso.setText("Por favor, ingrese el peso.");
        camposValidos = false;
    } else {
        lbErrorPeso.setText("");
    }

    if (taDescripcionPaquete.getText() == null || taDescripcionPaquete.getText().trim().isEmpty()) {
        lbErrorDescripcion.setText("Por favor, ingrese una descripción.");
        camposValidos = false;
    } else {
        lbErrorDescripcion.setText(""); 
    }

    return camposValidos;
}

   private void configurarDatosEntrada() {
    // Validar tfAlto: Solo números, máximo 4 caracteres
    tfAlto.textProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue.matches("\\d*") || newValue.length() > 4) {
            tfAlto.setText(oldValue);
        }
    });
    

    // Validar tfAncho: Solo números, máximo 4 caracteres
    tfAncho.textProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue.matches("\\d*") || newValue.length() > 4) {
            tfAncho.setText(oldValue);
        }
    });
    

    // Validar tfProfundidad: Solo números, máximo 4 caracteres
    tfProfundidad.textProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue.matches("\\d*") || newValue.length() > 4) {
            tfProfundidad.setText(oldValue);
        }
    });
    

    // Validar tfPeso: Solo números, máximo 4 caracteres
      tfPeso.textProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue.matches("\\d*(\\.\\d*)?") || newValue.length() > 7) {
            tfPeso.setText(oldValue);
        }
    });
    

    // Validar taDescripcionPaquete: Máximo 250 caracteres
    taDescripcionPaquete.textProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue.length() > 250) {
            taDescripcionPaquete.setText(oldValue);
        }
    });
    taDescripcionPaquete.setPromptText("Máximo 250 caracteres");
}

     
            
}
