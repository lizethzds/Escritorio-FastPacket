/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package escritoriofastpacket.vista.colaboradores;

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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import escritoriofastpacket.modelo.pojo.Colaborador;
import escritoriofastpacket.modelo.pojo.Mensaje;
import escritoriofastpacket.observer.INotificacionOperacion;
import escritoriofastpacket.utils.Utilidades;
import escritoriofastpacket.modelo.dao.ColaboradorDAO;

public class FXMLAdminColaboradoresController implements Initializable, INotificacionOperacion {
private ObservableList<Colaborador> colaboradores;
    @FXML
    private TableView<Colaborador> tvColaboradores;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colApellidoPaterno;
    @FXML
    private TableColumn colApellidoMaterno;
    @FXML
    private TableColumn colCURP;
    @FXML
    private TableColumn colCorreo;
    @FXML
    private TableColumn colNoPersonal;
    @FXML
    private TableColumn colRol;
    @FXML
    private TextField tfBuscarColaborador;
    
     private void configurarTabla(){
        colNoPersonal.setCellValueFactory(new PropertyValueFactory<>("noPersonal"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellidoPaterno.setCellValueFactory(new PropertyValueFactory<>("apellidoPaterno"));
        colApellidoMaterno.setCellValueFactory(new PropertyValueFactory<>("apellidoMaterno"));
        colCURP.setCellValueFactory(new PropertyValueFactory<>("curp"));
        colRol.setCellValueFactory(new PropertyValueFactory<>("rol"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
    }
    
     private void cargarInformacionTabla(){
        colaboradores = FXCollections.observableArrayList();
        List<Colaborador> listaWs = ColaboradorDAO.obtenerColaboradores();
        if(listaWs != null){
            if(!listaWs.isEmpty()){
                colaboradores.addAll(listaWs);
                tvColaboradores.setItems(colaboradores);
            }else{
                Utilidades.mostrarAlertaSimple("Datos no disponibles", 
                    "Por el momento no se encuentra ningún colaborador registrado",
                    Alert.AlertType.ERROR);
            }
        }else{
            Utilidades.mostrarAlertaSimple("Datos no disponibles", 
                    "Por el momento no se puede cargar la informacion de los colaboradores",
                    Alert.AlertType.ERROR);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionTabla();
    }    


    
    private void eliminarColaboradorConfirmacion(int idColaborador){
        Mensaje respuesta = ColaboradorDAO.eliminar(idColaborador);
        if(!respuesta.isError()){
            cargarInformacionTabla();
             Utilidades.mostrarAlertaSimple("Colaborador eliminado", 
                     "Colaborador eliminado con exíto", 
                     Alert.AlertType.INFORMATION);
        }else{
            Utilidades.mostrarAlertaSimple("Error al eliminar colaborador",
                    "Se ha producido un error al eliminar el colaborador por favor intentelo mas tarde", 
                    Alert.AlertType.WARNING);
        }
    
    }
    @FXML
    private void eliminarColaborador(ActionEvent event) {
        Colaborador colaborador = tvColaboradores.getSelectionModel().getSelectedItem();
        if(colaborador != null){
            boolean elimina = Utilidades.mostrarAlertaConfirmacion("Eliminar colaborador", 
                    "Estas seguro de eliminar el colaborador "+colaborador.getNombre()+"?");
            if(elimina == true){
                eliminarColaboradorConfirmacion(colaborador.getIdColaborador());
            }
        }else{
            Utilidades.mostrarAlertaSimple("seleccione un colaborador", 
                    "Tiene que seleccionar un colaborador para poder eliminarlo",
                    Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void formularioRegistrarColaborador(ActionEvent event) {
        irPantallaFormulario(this,null);
    }

    @FXML
    private void formularioEditarColaborador(ActionEvent event) {
        Colaborador colaborador = tvColaboradores.getSelectionModel().getSelectedItem();
        if(colaborador != null){
            irPantallaFormulario(this, colaborador);
        }else{
            Utilidades.mostrarAlertaSimple("seleccione un colaborador", 
                    "Tiene que seleccionar un colaborador para poder abrir el menu de editar",
                    Alert.AlertType.ERROR);
        }
    }
    private void irPantallaFormulario(INotificacionOperacion observador, Colaborador colaborador){
        Stage nuevoEcenario = new Stage();
        try {

            FXMLLoader cargador = new FXMLLoader(getClass().getResource("FXMLFormularioColaborador.fxml"));
            Parent nuevoParent = cargador.load();
            FXMLFormularioColaboradorController controlador = cargador.getController();
            controlador.inicializarValores(observador, colaborador);
            Scene ecenaAdmin = new Scene(nuevoParent);
            nuevoEcenario.setScene(ecenaAdmin);
            nuevoEcenario.setTitle("Agregar colaborador");
            nuevoEcenario.initModality(Modality.APPLICATION_MODAL);
            nuevoEcenario.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            Utilidades.mostrarAlertaSimple("Error", "Por el momento no se puede mostrar el formulario",
                   Alert.AlertType.ERROR);
        }  
    }
    @Override
    public void notificarOperacionExitosa(String tipo, String nombre) {
        cargarInformacionTabla();
    }
    
}
