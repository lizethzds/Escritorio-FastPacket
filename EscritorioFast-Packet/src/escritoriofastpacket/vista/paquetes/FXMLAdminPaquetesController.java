
package escritoriofastpacket.vista.paquetes;

import escritoriofastpacket.interfaz.INotificarOperacion;
import escritoriofastpacket.modelo.dao.PaqueteDAO;
import escritoriofastpacket.modelo.pojo.Paquete;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author lizet
 */
public class FXMLAdminPaquetesController implements Initializable , INotificarOperacion{
    
    
    private ObservableList<Paquete> paquetes;
    

    @FXML
    private TableView<Paquete> tvPaquetes;
    @FXML
    private TableColumn tcAltura;
    @FXML
    private TableColumn tcAnchura;
    @FXML
    private TableColumn tcProfundidad;
    @FXML
    private TableColumn tcPeso;

    /**
     * Initializes the controller class.
     */
    
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInfoTabla();
    }    

    @FXML
    private void btnAgregarPaquete(ActionEvent event) {
        
    }

    @FXML
    private void btnEditarPaquete(ActionEvent event) {
    }

    @FXML
    private void btnEliminarPaquete(ActionEvent event) {
    }

    @Override
    public void notificarOperacionExitosa(String tipo, String nombre) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void configurarTabla() {
         tcAltura.setCellValueFactory(new PropertyValueFactory("altura"));
         tcAnchura.setCellValueFactory(new PropertyValueFactory("ancho"));
         tcProfundidad.setCellValueFactory(new PropertyValueFactory("profundidad"));
         tcPeso.setCellValueFactory(new PropertyValueFactory("peso"));
    }
    
    private void cargarInfoTabla(){
        paquetes = FXCollections.observableArrayList();
        List<Paquete> listaWS = PaqueteDAO.obtenerPaquetesEnvio(17);
        if(listaWS != null){
            paquetes.addAll(listaWS);
            tvPaquetes.setItems(paquetes);
        }else{
            Utilidades.mostrarAlertaSimple("Datos no disponibles", "No se pudieron cargar los datos en la tabla", Alert.AlertType.ERROR);
        }
        
    }
    
}
