
package escritoriofastpacket.vista.clientes;

import escritoriofastpacket.interfaz.INotificarOperacion;
import escritoriofastpacket.modelo.dao.ClienteDAO;
import escritoriofastpacket.modelo.pojo.Cliente;
import escritoriofastpacket.modelo.pojo.Mensaje;
import escritoriofastpacket.utils.Utilidades;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author lizet
 */
public class FXMLAdminClientesController implements Initializable , INotificarOperacion{
    
    private ObservableList<Cliente> clientes;
    private FilteredList<Cliente> listaClientes;

    @FXML
    private TableView<Cliente> tvClientes;
    @FXML
    private TableColumn tcNombre;
    @FXML
    private TableColumn tcApellidoPat;
    @FXML
    private TableColumn tcApellidoMat;
    @FXML
    private TableColumn tcCorreo;
    @FXML
    private TableColumn tcTelefono;
    @FXML
    private TextField tfBusqueda;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionTabla();
        configurarFiltroBusqueda();
    }    
    
    
    private void configurarTabla(){
        tcNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        tcApellidoPat.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        tcApellidoMat.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));
        tcCorreo.setCellValueFactory(new PropertyValueFactory("correo"));
        tcTelefono.setCellValueFactory(new PropertyValueFactory("telefono"));
        
    }
    
    private void cargarInformacionTabla(){
        clientes = FXCollections.observableArrayList();
        List<Cliente> listaWS = ClienteDAO.obtenerClientes();
        if(listaWS != null){
            clientes.addAll(listaWS);
            tvClientes.setItems(clientes);
        }else{
            Utilidades.mostrarAlertaSimple("Datos no disponibles", "No se pudieron cargar los datos en la tabla", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void btnAgregarCliente(ActionEvent event) {
        irPantallaFormulario(this,null);
    }

    @FXML
    private void btnEliminarCliente(ActionEvent event) {
        Cliente cliente = tvClientes.getSelectionModel().getSelectedItem();
        if(cliente != null){
            boolean seElimina = Utilidades.mostrarAlertaConfirmacion("Eliminar cliente", "¿Está seguro de elimninar la información "
                    + "del cliente" + cliente.getNombre()+" del sistema? Esta acción es irreversible.");
            
            if(seElimina){
                Mensaje msj = ClienteDAO.eliminarCliente(cliente.getIdCliente());
                if(msj.isError()){
                    Utilidades.mostrarAlertaSimple("Error en eliminación", msj.getContenido(), Alert.AlertType.ERROR);
                }else{
                    Utilidades.mostrarAlertaSimple("Eliminación exitosa", msj.getContenido(), Alert.AlertType.INFORMATION);
                    notificarOperacionExitosa("Eliminado", cliente.getNombre());
                }
                
            }
        }else{
            Utilidades.mostrarAlertaSimple("Seleccione un cliente", "Para borrar un cliente, debe seleccionarlo primero", Alert.AlertType.INFORMATION);
        }
        
    }

    @FXML
    private void btnEditarCliente(ActionEvent event) {
        Cliente cliente = tvClientes.getSelectionModel().getSelectedItem();
        if(cliente!=null){
            irPantallaFormulario(this, cliente);
            
        }else{
            Utilidades.mostrarAlertaSimple("Seleccione un cliente", "Para poder editar, "
                    + "debe seleccionar a un cliente", Alert.AlertType.WARNING);
        }
    }

    private void irPantallaFormulario(INotificarOperacion observador, Cliente cliente) {
        try{
            Stage escenario = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFormularioCliente.fxml"));
            Parent vista = loader.load();
            FXMLFormularioClienteController controller = loader.getController();
            controller.inicializarValores(observador, cliente);
            
            Scene escena = new Scene(vista);
            escenario.setScene(escena);
            escenario.setTitle("Formulario de Clientes");
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();
            escenario.setResizable(false);
            
        }catch(Exception e){
            Utilidades.mostrarAlertaSimple("Error al cargar", "No se pudo cargar el formulario", Alert.AlertType.INFORMATION);
        }
    }

    @Override
    public void notificarOperacionExitosa(String tipo, String nombre) {
        System.out.println("Operacion:" + tipo);
        System.out.print("Nombre:" + nombre);
        cargarInformacionTabla(); 
        configurarFiltroBusqueda();
    
    }

   private void configurarFiltroBusqueda() {
    listaClientes = new FilteredList<>(clientes, b -> true);
    
    tfBusqueda.textProperty().addListener((observable, oldValue, newValue) -> {
        listaClientes.setPredicate(cliente -> {
            if (newValue == null || newValue.trim().isEmpty()) {
                return true; 
            }
            
            String lowerCaseFilter = newValue.toLowerCase();

            if (cliente.getCorreo() != null && cliente.getCorreo().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            }
            
            if (cliente.getNombre() != null && cliente.getNombre().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            }
            
            if (cliente.getTelefono() != null && cliente.getTelefono().toLowerCase().contains(lowerCaseFilter)) {
                return true; 
            }

            return false; 
        });
    });

    SortedList<Cliente> sortedData = new SortedList<>(listaClientes);
    sortedData.comparatorProperty().bind(tvClientes.comparatorProperty());
    tvClientes.setItems(sortedData);
}

    
}
