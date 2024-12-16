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
import escritoriofastpacket.modelo.pojo.Unidad;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author david
 */
public class FXMLAsignacionUnidadController implements Initializable {
    
    private Colaborador colaborador;
    private Unidad unidad;
    private ObservableList<Unidad> unidades;
    @FXML
    private Label lblNombreColaborador;
    @FXML
    private TableColumn col_vin;
    @FXML
    private TableColumn col_marca;
    @FXML
    private TableColumn col_modelo;
    @FXML
    private TableColumn col_tipo;
    @FXML
    private TableColumn col_anio;
    @FXML
    private TableColumn col_identificador;
    @FXML
    private TableView<Unidad> tv_unidades;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionTabla();     
    }
    
    private void obtenerUnidadAsignada(Colaborador colaborador){
        unidad = ColaboradorDAO.obtenerUnidad(colaborador.getIdColaborador());
        System.out.println("unidad"+colaborador.getIdColaborador());
        if(unidad !=  null){
            for (Unidad u : tv_unidades.getItems()) {
            if (u.getIdUnidad() == unidad.getIdUnidad()) { 
                tv_unidades.getSelectionModel().select(u);
                break;
            }
        }
        }
    }
    
    private void configurarTabla() {
        col_anio.setCellValueFactory(new PropertyValueFactory("anio"));
        col_identificador.setCellValueFactory(new PropertyValueFactory("noIdentificacion"));
        col_marca.setCellValueFactory(new PropertyValueFactory("marca"));
        col_modelo.setCellValueFactory(new PropertyValueFactory("modelo"));
        col_tipo.setCellValueFactory(new PropertyValueFactory("tipoUnidad"));
        col_vin.setCellValueFactory(new PropertyValueFactory("vin"));
    }

    private void cargarInformacionTabla() {
        unidades = FXCollections.observableArrayList();
        List<Unidad> listaWS = UnidadDAO.obtenerUnidades();
        if (listaWS != null) {
            unidades.addAll(listaWS);
            tv_unidades.setItems(unidades);
        } else {
            Utilidades.mostrarAlertaSimple("Datos no disponibles", "No se pudieron cargar los datos en la tabla", Alert.AlertType.ERROR);
        }
    }
    
    public void inicializarValores(Colaborador colaborador){
        this.colaborador = colaborador;
        obtenerUnidadAsignada(colaborador);
    }
    
    private void cerrarVentana() {
        ((Stage) lblNombreColaborador.getScene().getWindow()).close();
    }

    @FXML
    private void accionCancelar(ActionEvent event) {
        cerrarVentana();
    }

    @FXML
    private void accionAsignarUnidad(ActionEvent event) {
        Unidad unidad = tv_unidades.getSelectionModel().getSelectedItem();
        if (unidad != null) {
            Mensaje msj = ColaboradorDAO.comprobarColaboradorUnidad(this.colaborador.getIdColaborador());
            System.out.println("mensaje"+msj.getContenido());
            if(!msj.isError()){
                boolean asignada = asignarUnidad(unidad);
                if(asignada){
                    cerrarVentana();
                }
            }else{
                 Utilidades.mostrarAlertaSimple("Advertencia", msj.getContenido(), Alert.AlertType.INFORMATION);
            }
            
        } else {
            Utilidades.mostrarAlertaSimple("Advertencia", "Seleccione una Unidad para editarla.", Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    private void accionAsignarNuevaUnidad(ActionEvent event) {
        Unidad unidad = tv_unidades.getSelectionModel().getSelectedItem();
        if (unidad != null) {
            if(removerAsignacionUnidad()){
                if(asignarUnidad(unidad)){
                    cerrarVentana();
                }
            }
        } else {
            Utilidades.mostrarAlertaSimple("Advertencia", "Seleccione una Unidad para editarla.", Alert.AlertType.INFORMATION);
        }
    }
    
    private boolean asignarUnidad(Unidad unidad){
        Mensaje respuesta = ColaboradorDAO.asignarUnidad(colaborador.getIdColaborador(),unidad.getIdUnidad());
            if (!respuesta.isError()) {
                Utilidades.mostrarAlertaSimple("Colaborador asignado con exito", "El colaborador "
                        + colaborador.getNombre() + ", se asignó correctamente a la unidad con el vin " + unidad.getVin()
                        , Alert.AlertType.INFORMATION);
                return true;
            } else {
                Utilidades.mostrarAlertaSimple("Error al asignar una unidad al colaborador", respuesta.getContenido(), Alert.AlertType.ERROR);
                return false;
            }
    }
    
    private boolean removerAsignacionUnidad(){
        Mensaje respuesta = ColaboradorDAO.quitarAsignacionUnidad(colaborador.getIdColaborador());
            if (!respuesta.isError()) {
                Utilidades.mostrarAlertaSimple("Colaborador asignado con exito", 
                        "La relacion con la unidad asignada fue eliminada con exito "
                        , Alert.AlertType.INFORMATION);
                return true;
            } else {
                Utilidades.mostrarAlertaSimple("Error al eliminar relaciones del colaborador", respuesta.getContenido(), Alert.AlertType.ERROR);
                return false;
            }
    }

    @FXML
    private void accionRemoverAsignacionUnidad(ActionEvent event) {
        boolean aceptado = Utilidades.mostrarAlertaConfirmacion("Eliminar las asignaciones del colaborador", 
                    "Estas seguro de eliminar las realciones del colaborador con la unidad asignada");
        if (aceptado) {
            boolean removido = removerAsignacionUnidad();
            if(removido){
                cerrarVentana();
            }
        }
    }
    
}
