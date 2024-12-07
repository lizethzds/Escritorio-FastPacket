
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
    
    private ObservableList<Estado> estados;
    private ObservableList<Municipio> municipios;

    @FXML
    private TextField tfCalleDestino;
    @FXML
    private TextField tfColoniaDestino;
    @FXML
    private TextField tfNumeroDestino;
    @FXML
    private TextField tfCPDestino;
    @FXML
    private ComboBox<Estado> cbEstado;
    @FXML
    private ComboBox<Municipio> cbMunicipio;
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
    private TextField tfCostoEnvio;
    @FXML
    private Label lbLugarOrigen;
    @FXML
    private Label lbEstatus;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarEstados();
        configuracionSeleccionEstado();
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
        tfCostoEnvio.setText(Float.toString(datosEnvio.getEnvio().getCostoEnvio()));
        lbNombreCliente.setText(datosEnvio.getCliente());
        lbConductor.setText(datosEnvio.getConductor());
        tfNumeroDestino.setText(datosEnvio.getDireccion().getNumero());
    }
    
     private void cargarEstados() {
      estados = FXCollections.observableArrayList();
      List<Estado> listaWS = CatalogoDAO.obtenerEstados();
      if(listaWS != null){
          estados.addAll(listaWS);
          cbEstado.setItems(estados);
      }
    }
     
      private void cargarMunicipios(Integer idEstado) {
        municipios = FXCollections.observableArrayList();
        List<Municipio> inf = CatalogoDAO.obtenerMunicipios(idEstado);
        municipios.addAll(inf);
        cbMunicipio.setItems(municipios);
    }
    
      
        private void configuracionSeleccionEstado() {
        cbEstado.valueProperty().addListener(new ChangeListener<Estado>() {
            @Override
            public void changed(ObservableValue<? extends Estado> observable, Estado oldValue, Estado newValue) {
                cargarMunicipios(newValue.getIdEstado());
            }
        });
    }

   private int buscarIdEstado(Integer idEstado){
        for(int i = 0; i<estados.size(); i++){
            if(estados.get(i).getIdEstado() == idEstado){
                return i;
            }
        }
        return 0;
    }
    
    private int buscarIdMunicipio(Integer idMunicipio){
        for(int i = 0; i<municipios.size(); i++){
            if(municipios.get(i).getIdMunicipio() == idMunicipio){
                return i;
            }
        }
        return 0;
    }
}
