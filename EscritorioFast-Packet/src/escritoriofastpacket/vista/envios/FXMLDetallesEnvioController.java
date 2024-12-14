
package escritoriofastpacket.vista.envios;

import escritoriofastpacket.interfaz.INotificarOperacion;
import escritoriofastpacket.modelo.dao.CatalogoDAO;
import escritoriofastpacket.modelo.dao.EnvioDAO;
import escritoriofastpacket.modelo.pojo.DatosRegistroEnvio;
import escritoriofastpacket.modelo.pojo.Envio;
import escritoriofastpacket.modelo.pojo.Estado;
import escritoriofastpacket.modelo.pojo.Municipio;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author lizet
 */
public class FXMLDetallesEnvioController implements Initializable {
   
    private INotificarOperacion observador;
    private DatosRegistroEnvio datosEnvio;
    private Envio envioEdicion;
    
  
   
    @FXML
    private Label lbNombreCliente;
    @FXML
    private Label lbCalle;
    @FXML
    private Label lbColonia;
    @FXML
    private Label lbNumero;
    @FXML
    private Label lbConductor;
    @FXML
    private Label lbLugarOrigen;
    @FXML
    private Label lbCalleDestino;
    @FXML
    private Label lbColoniaDestino;
    @FXML
    private Label lbNumDestino;
    @FXML
    private Label lbCPDestino;
    @FXML
    private Label lbLugarDestino;
    @FXML
    private Label lbEstatus;
    @FXML
    private Label lbCP;
    @FXML
    private Label lbCostoEnvio;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
    }    

    void inicializarValores(INotificarOperacion observador, Envio envioEdicion) {
        this.observador = observador;
        this.envioEdicion = envioEdicion;
        if(envioEdicion != null){
            cargarDetallesEnvio(envioEdicion.getIdEnvio());
            
        }
        
        
    }

    private void cargarDetallesEnvio(Integer idEnvio) {
        datosEnvio = EnvioDAO.obtenerDetallesEnvio(idEnvio);
        lbCalle.setText(datosEnvio.getDireccionCliente().getCalle());
        lbColonia.setText(datosEnvio.getDireccionCliente().getColonia());
        lbLugarOrigen.setText(datosEnvio.getLugarOrigen());
        lbLugarDestino.setText(datosEnvio.getLugarDestino());
        lbConductor.setText(datosEnvio.getConductor());
        lbCP.setText(datosEnvio.getDireccionCliente().getCodigoPostal());
        lbCalleDestino.setText(datosEnvio.getDireccion().getCalle());
        lbColoniaDestino.setText(datosEnvio.getDireccion().getColonia());
        lbCPDestino.setText(datosEnvio.getDireccion().getCodigoPostal());
        lbNombreCliente.setText(datosEnvio.getCliente());
        lbNumero.setText(datosEnvio.getDireccionCliente().getNumero());
        lbNumDestino.setText(datosEnvio.getDireccion().getNumero());
        lbCostoEnvio.setText(String.valueOf(datosEnvio.getEnvio().getCostoEnvio()));
        lbEstatus.setText(datosEnvio.getEstadoEnvio());
        

 
        
    }
    
    
     
    
    
      
        



    @FXML
    private void btnCerrarVentana(ActionEvent event) {
    }
}
