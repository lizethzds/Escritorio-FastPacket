
package escritoriofastpacket.vista.paquetes;

import escritoriofastpacket.interfaz.INotificarOperacion;
import escritoriofastpacket.modelo.dao.PaqueteDAO;
import escritoriofastpacket.modelo.pojo.Envio;
import escritoriofastpacket.modelo.pojo.Paquete;
import escritoriofastpacket.utils.Utilidades;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author lizet
 */
public class FXMLAdminPaquetesController implements Initializable , INotificarOperacion{
    
    
    private ObservableList<Paquete> paquetes;
    private INotificarOperacion observador;
    private Envio envioPaquete;
    

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
    @FXML
    private TableColumn tcDescripcion;

    /**
     * Initializes the controller class.
     */
    
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
       // cargarInfoTabla();
    }    

    @FXML
    private void btnAgregarPaquete(ActionEvent event) {
        irPantallaFormulario(this, null, envioPaquete.getIdEnvio());
       
    }

    @FXML
    private void btnEditarPaquete(ActionEvent event) {
        Paquete paquete = tvPaquetes.getSelectionModel().getSelectedItem();
        if(paquete !=null){
            irPantallaFormulario(this, paquete, envioPaquete.getIdEnvio());
        }else{
            Utilidades.mostrarAlertaSimple("Seleccione un paquete", "Para editar, primero seleccione un paquete en la tabla.",
                    Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void btnEliminarPaquete(ActionEvent event) {
        Paquete paquete = tvPaquetes.getSelectionModel().getSelectedItem();
        if(paquete != null){
        
        boolean seElimina = Utilidades.mostrarAlertaConfirmacion("Eliminar paquete",
                "¿Estás seguro de eliminar el paquete del sistema?");
        if(seElimina){
  
           PaqueteDAO.eliminarPaquete(paquete.getIdPaquete());
            notificarOperacionExitosa("Eliminado", "Paquete");
           }
        }else{
            Utilidades.mostrarAlertaSimple("Seleccionar paquete", "Primero selecciona un paquete para elimninar", Alert.AlertType.INFORMATION);
        }
        
    }
    
    
      public void inicializarValores(INotificarOperacion observador, Envio envioPaquete) {
        this.observador = observador;
        this.envioPaquete = envioPaquete;
        if(envioPaquete != null){
            cargarInfoTabla();
        }
         
    }
    

    @Override
    public void notificarOperacionExitosa(String tipo, String nombre) {
        System.out.println("Operacion:" + tipo);
         System.out.print("Nombre:" + nombre);
         cargarInfoTabla();
    }

    private void configurarTabla() {
         tcAltura.setCellValueFactory(new PropertyValueFactory("altura"));
         tcAnchura.setCellValueFactory(new PropertyValueFactory("ancho"));
         tcProfundidad.setCellValueFactory(new PropertyValueFactory("profundidad"));
         tcPeso.setCellValueFactory(new PropertyValueFactory("peso"));
         tcDescripcion.setCellValueFactory(new PropertyValueFactory("descripcion"));
    }
    
    private void cargarInfoTabla(){
        paquetes = FXCollections.observableArrayList();
   
        List<Paquete> listaWS = PaqueteDAO.obtenerPaquetesEnvio(envioPaquete.getIdEnvio());
        if(listaWS != null){
            paquetes.addAll(listaWS);
            tvPaquetes.setItems(paquetes);
        }else{
            Utilidades.mostrarAlertaSimple("Datos no disponibles", "No se pudieron cargar los datos en la tabla", Alert.AlertType.ERROR);
        }
        
    }
    
    
    
    private void irPantallaFormulario(INotificarOperacion observador, Paquete paquete, Integer idEnvio){
        try{
        Stage escenario = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFormularioPaquetes.fxml"));
 
            Parent vista = loader.load();
            FXMLFormularioPaquetesController controlador = loader.getController();
            controlador.inicializarValores(observador,paquete , envioPaquete.getIdEnvio() );
            Scene escena = new Scene(vista);
            escenario.setScene(escena);
            escenario.setTitle("Datos de paquete");
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.setResizable(false);
            escenario.showAndWait();
            
            
        }catch(IOException ex){
            Utilidades.mostrarAlertaSimple("Error al cargar", "No se pudo cargar la pantalla deseada.", Alert.AlertType.ERROR);
        }
    }


  
    
}
