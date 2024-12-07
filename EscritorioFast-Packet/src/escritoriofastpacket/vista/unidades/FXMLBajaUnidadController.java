/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package escritoriofastpacket.vista.unidades;

import escritoriofastpacket.interfaz.INotificarOperacion;
import escritoriofastpacket.modelo.dao.UnidadDAO;
import escritoriofastpacket.modelo.pojo.Mensaje;
import escritoriofastpacket.modelo.pojo.Unidad;
import escritoriofastpacket.utils.Utilidades;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author uriel
 */
public class FXMLBajaUnidadController implements Initializable {

    Unidad unidad = null;
    private INotificarOperacion observador;

    @FXML
    private TextArea tf_motivo;
    @FXML
    private Text lb_vin;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb ) {
        
    }
    

    public void cargarDatos(Unidad unidad, INotificarOperacion observador) {
        this.observador = observador;
        this.unidad = unidad;
        lb_vin.setText("Ingrese el motivo para dar de baja la unidad con VIN: " + unidad.getVin());
        System.out.println(unidad.getIdUnidad().toString());
    }

    @FXML
    private void btnBajaUnidad(ActionEvent event) {
        String motivo = tf_motivo.getText();
        
        if (validarMotivo(motivo)) {
            unidad.setMotivo(motivo);
            Mensaje respuesta = UnidadDAO.eliminarUnidad(unidad);
            if (!respuesta.isError()) {
                Utilidades.mostrarAlertaSimple("Eliminar", "Se ha eliminado la Unidad correctamente.", Alert.AlertType.INFORMATION);
                cerrarVenatana();
                observador.notificarOperacionExitosa("Eliminar", "eliminar");
            }

        } else {
            Utilidades.mostrarAlertaSimple("Error", "Ingrese un motico para eliminar al Unidad.", Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    private void btnCancelar(ActionEvent event) {
        cerrarVenatana();
    }

    private void cerrarVenatana() {
        Stage escenario = (Stage) lb_vin.getScene().getWindow();
        escenario.close();
    }

    private boolean validarMotivo(String motivo) {
        boolean valido = true;
        if (motivo.isEmpty()) {
            valido = false;
        }
        return valido;
    }

}
