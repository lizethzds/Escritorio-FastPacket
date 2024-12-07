/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package escritoriofastpacket.vista.envios;

import escritoriofastpacket.interfaz.INotificarOperacion;
import escritoriofastpacket.modelo.dao.EnvioDAO;
import escritoriofastpacket.modelo.pojo.Cliente;
import escritoriofastpacket.modelo.pojo.Envio;
import escritoriofastpacket.utils.Utilidades;
import escritoriofastpacket.vista.clientes.FXMLFormularioClienteController;
import escritoriofastpacket.vista.paquetes.FXMLAdminPaquetesController;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
public class FXMLAdminEnviosController implements Initializable , INotificarOperacion {
    
    private ObservableList<Envio> envios;
    private FilteredList<Envio> listaEnvios;

    @FXML
    private TableView<Envio> tvEnvios;
    @FXML
    private TextField tfBusquedaNumGuia;
    @FXML
    private TableColumn tcNoGuia;
    @FXML
    private TableColumn tcConductor;
    @FXML
    private TableColumn tcCliente;
    @FXML
    private TableColumn tcEstatus;
    @FXML
    private TableColumn tcCosto;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionTabla();
       // configurarFiltroBusqueda();
    }    

    @FXML
    private void btnAgregarEnvio(ActionEvent event) {
         irPantallaFormulario(this,null);
    }

    @FXML
    private void btnEditarEnvio(ActionEvent event) {
        Envio envio = tvEnvios.getSelectionModel().getSelectedItem();
                if(envio != null){
                    irPantallaFormulario(this, envio);
                }else{
                    Utilidades.mostrarAlertaSimple("Seleccione un envio", "Para ver "
                            + "sus detalles, seleccione un envio", Alert.AlertType.WARNING);
                }
    }

    @FXML
    private void btnEliminar(ActionEvent event) {
    }

    @FXML
    private void btnVerPaquetes(ActionEvent event) {
        Envio envio = tvEnvios.getSelectionModel().getSelectedItem();
        if(envio != null){
                    irAdminisitradorPaquetes(this, envio);
                    System.out.println("GUIA  " + envio.getNoGuia());
                }else{
                    Utilidades.mostrarAlertaSimple("Seleccione un envio", "Para ver "
                            + "los paquetes, seleccione un envio", Alert.AlertType.WARNING);
                }
    }

    @FXML
    private void btnVerDetalles(ActionEvent event) {
        Envio envio = tvEnvios.getSelectionModel().getSelectedItem();
                if(envio != null){
                    irPantallaDetalles(this, envio);
                }else{
                    Utilidades.mostrarAlertaSimple("Seleccione un envio", "Para ver "
                            + "sus detalles, seleccione un envio", Alert.AlertType.WARNING);
                }
    }

    
    private void configurarTabla() {
        tcNoGuia.setCellValueFactory(new PropertyValueFactory("noGuia"));
        tcCosto.setCellValueFactory(new PropertyValueFactory("costoEnvio"));
        tcCliente.setCellValueFactory(new PropertyValueFactory("cliente"));
        tcConductor.setCellValueFactory(new PropertyValueFactory("conductor"));
        tcEstatus.setCellValueFactory(new PropertyValueFactory("estatus"));
    }

    private void cargarInformacionTabla() {
        envios = FXCollections.observableArrayList();
        List<Envio> listaWS = EnvioDAO.obtenerEnvios();
        if(listaWS != null){
            envios.addAll(listaWS);
            tvEnvios.setItems(envios);
        }else{
        Utilidades.mostrarAlertaSimple("Datos no disponibles", "No se pudieron cargar los datos en la tabla", Alert.AlertType.ERROR);

        }
        
    }

    private void configurarFiltroBusqueda() {
        //TODO
    }
    
    
    private void irPantallaFormulario(INotificarOperacion observador, Envio envio) {
        try{
            Stage escenario = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFormularioEnvio.fxml"));
            Parent vista = loader.load();
            FXMLFormularioEnvioController controller = loader.getController();
           controller.inicializarValores(observador, envio);
            
            Scene escena = new Scene(vista);
            escenario.setScene(escena);
            escenario.setTitle("Formulario de Envios");
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();
            escenario.setResizable(false);
            
        }catch(Exception e){
            Utilidades.mostrarAlertaSimple("Error al cargar", "No se pudo cargar el formulario", Alert.AlertType.INFORMATION);
            e.printStackTrace();
        }
    }
    
    
   private void irAdminisitradorPaquetes(INotificarOperacion observador, Envio envio){
        try{
            Stage escenario = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/escritoriofastpacket/vista/paquetes/FXMLAdminPaquetes.fxml"));
            Parent vista = loader.load();
            FXMLAdminPaquetesController controller = loader.getController();
            controller.inicializarValores(observador, envio);
            
            Scene escena = new Scene(vista);
            escenario.setScene(escena);
            escenario.setTitle("Formulario de Paquetes");
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();
            escenario.setResizable(false);
            
        }catch(Exception e){
            Utilidades.mostrarAlertaSimple("Error al cargar", "No se pudo cargar el formulario", Alert.AlertType.INFORMATION);
            e.printStackTrace();
        }
   }
    
    private void irPantallaDetalles(INotificarOperacion observador, Envio envio){
        try{
            Stage escenario = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDetallesEnvio.fxml"));
            Parent vista = loader.load();
            FXMLDetallesEnvioController controller = loader.getController();
           controller.inicializarValores(observador, envio);
            
            Scene escena = new Scene(vista);
            escenario.setScene(escena);
            escenario.setTitle("Detalle de envío");
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();
            escenario.setResizable(false);
            
        }catch(Exception e){
            Utilidades.mostrarAlertaSimple("Error al cargar", "No se pudo cargar el formulario", Alert.AlertType.INFORMATION);
            e.printStackTrace();
        }
    }

    @Override
    public void notificarOperacionExitosa(String tipo, String nombre) {
        System.out.println("Operacion:" + tipo);
        System.out.print("Nombre:" + nombre);
        cargarInformacionTabla(); 
    }

    
}
