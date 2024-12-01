/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package escritoriofastpacket.vista.unidades;

import escritoriofastpacket.interfaz.INotificarOperacion;
import escritoriofastpacket.modelo.dao.UnidadDAO;
import escritoriofastpacket.modelo.pojo.Mensaje;
import escritoriofastpacket.modelo.pojo.TipoUnidad;
import escritoriofastpacket.modelo.pojo.Unidad;
import escritoriofastpacket.utils.Utilidades;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author uriel
 */
public class FXMLAgregarUnidadController implements Initializable {
    
    private INotificarOperacion observador;
    private Unidad unidadEdiatada;
    private boolean modoEdicion = false;
    private ObservableList<TipoUnidad> tipoUnidad;
    

    @FXML
    private TextField tf_vin;
    @FXML
    private TextField tf_marca;
    @FXML
    private TextField tf_modelo;
    @FXML
    private TextField tf_anio;
    @FXML
    private ComboBox<TipoUnidad> cb_tipo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarTipoUnidad();
    }

    public void inicializarValores(INotificarOperacion observador, Unidad unidadEditada){
        this.observador = observador;
        this.unidadEdiatada = unidadEditada;
        if (unidadEditada != null) {
            modoEdicion = true;
            cargarDatosEdicion();
        }
    }
    
    private void cargarDatosEdicion(){
        
    }

    @FXML
    private void btnGuardarUnidad(ActionEvent event) {
        Unidad unidad = new Unidad();
        String anio = tf_anio.getText();
        String marca = tf_marca.getText();
        String modelo = tf_modelo.getText();
        String vin = tf_vin.getText();
                
        String noIdentificacion = vin.substring(0,4) + anio;
        int idTipoUnidad = (cb_tipo.getSelectionModel().getSelectedItem() != null) 
                ? cb_tipo.getSelectionModel().getSelectedItem().getIdTipoUnidad()
                : null;
        
        unidad.setAnio(anio);
        unidad.setMarca(marca);
        unidad.setModelo(modelo);
        unidad.setVin(vin);
        unidad.setNoIdentificacion(noIdentificacion);
        unidad.setIdTipoUnidad(idTipoUnidad);
        Mensaje respuesta = UnidadDAO.registrarUnidad(unidad);
        if (respuesta.isError()) {
            Utilidades.mostrarAlertaSimple("Error", respuesta.getContenido(), Alert.AlertType.ERROR);
        }else{
            Utilidades.mostrarAlertaSimple("Registro", "Registro realizado correctamente", Alert.AlertType.INFORMATION);
            cerrarVenatana();
            observador.notificarOperacionExitosa("Registro", unidad.getVin());
        }
    }

    @FXML
    private void btnCancelar(ActionEvent event) {
        cerrarVenatana();
    }
    
    private void cargarTipoUnidad(){
        tipoUnidad = FXCollections.observableArrayList();
        List<TipoUnidad> listaWS = UnidadDAO.obtenerTipoUnidad();
        System.err.println(listaWS.get(0));
        if (listaWS != null) {
            tipoUnidad.addAll(listaWS);
            cb_tipo.setItems(tipoUnidad);
        }
    }
    
    private void cerrarVenatana(){
         Stage escenario = (Stage) cb_tipo.getScene().getWindow();
        escenario.close();
    }
    
}
