/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package escritoriofastpacket.vista.envios;

import escritoriofastpacket.interfaz.INotificarOperacion;
import escritoriofastpacket.modelo.pojo.Cliente;
import escritoriofastpacket.modelo.pojo.Colaborador;
import escritoriofastpacket.modelo.pojo.DatosRegistroEnvio;
import escritoriofastpacket.modelo.pojo.Envio;
import escritoriofastpacket.modelo.pojo.Estado;
import escritoriofastpacket.modelo.pojo.EstadoEnvio;
import escritoriofastpacket.modelo.pojo.Municipio;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author lizet
 */
public class FXMLFormularioEnvioController implements Initializable {
    
    private INotificarOperacion observador;
    private Envio envioEdicion;
    private DatosRegistroEnvio datosEnvio;
    private boolean modoEdicion = false;
    
    private ObservableList<Estado> estados;
    private ObservableList<Municipio> municipios;
    private ObservableList<Cliente> clientes;
    private ObservableList<Colaborador> conductor;
    
    

    @FXML
    private ComboBox<Cliente> cbClientes;
    @FXML
    private ComboBox<Estado> cbEstado;
    @FXML
    private ComboBox<Municipio> cbMunicipio;
    @FXML
    private TextField tfCalleDestino;
    @FXML
    private TextField tfColoniaDestino;
    @FXML
    private TextField tfNumeroDestino;
    @FXML
    private TextField tfCodigoPostal;
    @FXML
    private ComboBox<Colaborador> cbConductores;
    @FXML
    private TextField tfCostoEnvio;
    @FXML
    private ComboBox<EstadoEnvio> cbEstatus;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnGuardarEnvio(ActionEvent event) {
    }

    void inicializarValores(INotificarOperacion observador, Envio envio) {
        this.observador = observador;
        this.envioEdicion = envioEdicion;
        if(envioEdicion != null){
            modoEdicion = true;
            cargarDatosEnvio();
        }
    }

    private void cargarDatosEnvio() {
        System.out.println("Hola");
    }
    
}
