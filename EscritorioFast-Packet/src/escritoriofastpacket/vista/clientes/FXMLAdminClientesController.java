/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package escritoriofastpacket.vista.clientes;

import escritoriofastpacket.interfaz.INotificarOperacion;
import escritoriofastpacket.modelo.dao.ClienteDAO;
import escritoriofastpacket.modelo.pojo.Cliente;
import escritoriofastpacket.utils.Utilidades;
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
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author lizet
 */
public class FXMLAdminClientesController implements Initializable , INotificarOperacion{
    
    private ObservableList<Cliente> clientes;

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
    }

    @FXML
    private void btnEditarCliente(ActionEvent event) {
    }

    private void irPantallaFormulario(INotificarOperacion observador, Cliente cliente) {
        try{
            Stage escenario = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFormularioCliente.fxml"));
            Parent vista = loader.load();
            FXMLFormularioClienteController controller = loader.getController();
            
        }catch(Exception e){
            
        }
    }

    @Override
    public void notificarOperacionExitosa(String tipo, String nombre) {
        System.out.println("Operacion:" + tipo);
        System.out.print("Nombre:" + nombre);
        cargarInformacionTabla(); 
    
    }
    
}
