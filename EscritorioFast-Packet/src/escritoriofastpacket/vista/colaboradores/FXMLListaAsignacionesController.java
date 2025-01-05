/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package escritoriofastpacket.vista.colaboradores;

import escritoriofastpacket.modelo.dao.ColaboradorDAO;
import escritoriofastpacket.modelo.dao.UnidadDAO;
import escritoriofastpacket.modelo.pojo.Colaborador;
import escritoriofastpacket.modelo.pojo.Mensaje;
import escritoriofastpacket.modelo.pojo.RegistroColaboradorUnidad;
import escritoriofastpacket.modelo.pojo.Unidad;
import escritoriofastpacket.observer.INotificacionOperacion;
import escritoriofastpacket.utils.Utilidades;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author david
 */
public class FXMLListaAsignacionesController implements Initializable, INotificacionOperacion {
    
    private ObservableList<RegistroColaboradorUnidad> registroColaboradorUnidad;
    @FXML
    private TableView<RegistroColaboradorUnidad> tb_RegistroColaboradorUnidad;
    @FXML
    private TableColumn<RegistroColaboradorUnidad, String> col_vin;
    @FXML
    private TableColumn<RegistroColaboradorUnidad, String> col_Marca;
    @FXML
    private TableColumn<RegistroColaboradorUnidad, String> col_Modelo;
    @FXML
    private TableColumn<RegistroColaboradorUnidad, String> col_Tipo;
    @FXML
    private TableColumn<RegistroColaboradorUnidad, String> col_Nombre;
    @FXML
    private TableColumn<RegistroColaboradorUnidad, String> col_NoPersonal;
    @FXML
    private TableColumn<RegistroColaboradorUnidad, String> col_NoLicencia;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionTabla();
    }    
    
    private void configurarTabla() { 
        col_Marca.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getUnidad().getMarca()));

        col_Modelo.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getUnidad().getModelo()));

        col_Tipo.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getUnidad().getTipoUnidad()));

        col_vin.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getUnidad().getVin()));
        
        col_Nombre.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getColaborador().getNombre()+" "+
                    cellData.getValue().getColaborador().getApellidoPaterno()+" "+
                    cellData.getValue().getColaborador().getApellidoMaterno()));

        col_NoPersonal.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getColaborador().getNoPersonal()));

        col_NoLicencia.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getColaborador().getNoLicencia()));
    }

    private void cargarInformacionTabla() {
        registroColaboradorUnidad = FXCollections.observableArrayList();
        List<RegistroColaboradorUnidad> listaWS = ColaboradorDAO.obtenerRegistrosColaboradorUnidad();
        if (listaWS != null) {
            registroColaboradorUnidad.addAll(listaWS);
            tb_RegistroColaboradorUnidad.setItems(registroColaboradorUnidad);
        } else {
            Utilidades.mostrarAlertaSimple("Datos no disponibles", "No se pudieron cargar los datos en la tabla", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void accionRegresar(ActionEvent event) {
        ((Stage) tb_RegistroColaboradorUnidad.getScene().getWindow()).close();
    }

    @FXML
    private void accionEditarAsignacion(ActionEvent event) {
        RegistroColaboradorUnidad registro = tb_RegistroColaboradorUnidad.getSelectionModel().getSelectedItem();
        irPantallaAsignarColaboradorUnidad(registro);
    }
    
    private void irPantallaAsignarColaboradorUnidad(RegistroColaboradorUnidad registro){
        Stage nuevoEcenario = new Stage();
        try {

            FXMLLoader cargador = new FXMLLoader(getClass().getResource("FXMLAsignacionUnidad.fxml"));
            Parent nuevoParent = cargador.load();
            FXMLAsignacionUnidadController controller = cargador.getController();
            controller.inicializarValores(this,registro);
            Scene ecenaAdmin = new Scene(nuevoParent);
            nuevoEcenario.setScene(ecenaAdmin);
            nuevoEcenario.setTitle("Asignar colaborador a unidad");
            nuevoEcenario.initModality(Modality.APPLICATION_MODAL); 
            nuevoEcenario.setResizable(false);
            nuevoEcenario.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            Utilidades.mostrarAlertaSimple("Error", "Por el momento no se puede mostrar la vetana de vista asignaciónes",
                   Alert.AlertType.ERROR);
        }  
    }
    private void removerAsignacionUnidad(){
        RegistroColaboradorUnidad registro = tb_RegistroColaboradorUnidad.getSelectionModel().getSelectedItem();
        Mensaje respuesta = ColaboradorDAO.quitarAsignacionUnidad(registro.getColaborador().getIdColaborador(), 
                                                     registro.getUnidad().getIdUnidad());
            if (!respuesta.isError()) {
                cargarInformacionTabla();
                Utilidades.mostrarAlertaSimple("Colaborador asignado con exito", 
                        "La relacion con la unidad asignada fue eliminada con exito "
                        , Alert.AlertType.INFORMATION);
            } else {
                Utilidades.mostrarAlertaSimple("Error al eliminar relaciones del colaborador", respuesta.getContenido(), Alert.AlertType.ERROR);
            }
    }

    @FXML
    private void accionRemoverAsignacionUnidad(ActionEvent event) {
        boolean aceptado = Utilidades.mostrarAlertaConfirmacion("Eliminar las asignaciones del colaborador", 
                    "Estas seguro de eliminar las realciones del colaborador con la unidad asignada");
        if (aceptado) {
           removerAsignacionUnidad();
        }
    }
    
    @Override
    public void notificarOperacionExitosa(String tipo, String nombre) {
        cargarInformacionTabla();
    }
}
