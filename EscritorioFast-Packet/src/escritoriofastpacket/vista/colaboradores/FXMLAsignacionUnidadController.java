/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package escritoriofastpacket.vista.colaboradores;

import escritoriofastpacket.modelo.dao.ColaboradorDAO;
import escritoriofastpacket.modelo.pojo.Colaborador;
import escritoriofastpacket.modelo.pojo.Mensaje;
import escritoriofastpacket.modelo.pojo.RegistroColaboradorUnidad;
import escritoriofastpacket.modelo.pojo.Unidad;
import escritoriofastpacket.observer.INotificacionOperacion;
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
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author david
 */
public class FXMLAsignacionUnidadController implements Initializable {

    private RegistroColaboradorUnidad registro;
    private ObservableList<Unidad> unidades;
    private ObservableList<Colaborador> conductores;
    private INotificacionOperacion observer;
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
    @FXML
    private TableView<Colaborador> tv_colaborador;
    @FXML
    private TableColumn col_nombre;
    @FXML
    private TableColumn col_noPersonal;
    @FXML
    private TableColumn col_noLicencia;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTablas();
    }

    private void configurarEstilosTabla() {
        tv_unidades.setRowFactory(tv -> new TableRow<Unidad>() {
            @Override
            protected void updateItem(Unidad item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setStyle("");
                } else if (item.isAsignada()) {
                    setStyle("-fx-background-color: #d6516a;");
                } else {
                    setStyle("-fx-background-color: #46d488;");
                }
            }
        });

        tv_colaborador.setRowFactory(tv -> new TableRow<Colaborador>() {
            @Override
            protected void updateItem(Colaborador item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setStyle("");
                } else if (item.isAsignado()) {
                    setStyle("-fx-background-color: #d6516a;");
                } else {
                    setStyle("-fx-background-color: #46d488;\n");
                }
            }
        });

        tv_unidades.getStylesheets().add(getClass().getResource("fxmlasignacionunidad.css").toExternalForm());
        tv_colaborador.getStylesheets().add(getClass().getResource("fxmlasignacionunidad.css").toExternalForm());

    }

    private void configurarTablas() {
        col_anio.setCellValueFactory(new PropertyValueFactory("anio"));
        col_identificador.setCellValueFactory(new PropertyValueFactory("noIdentificacion"));
        col_marca.setCellValueFactory(new PropertyValueFactory("marca"));
        col_modelo.setCellValueFactory(new PropertyValueFactory("modelo"));
        col_tipo.setCellValueFactory(new PropertyValueFactory("tipoUnidad"));
        col_vin.setCellValueFactory(new PropertyValueFactory("vin"));

        col_nombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        col_noPersonal.setCellValueFactory(new PropertyValueFactory("noPersonal"));
        col_noLicencia.setCellValueFactory(new PropertyValueFactory("noLicencia"));
    }

    private void cargarInformacionTablas() {
        unidades = FXCollections.observableArrayList();
        List<Unidad> listaWSUnidades = ColaboradorDAO.obtenerAsignacionesUnidades();
        if (listaWSUnidades != null) {
            unidades.addAll(listaWSUnidades);
            tv_unidades.setItems(unidades);
        } else {
            Utilidades.mostrarAlertaSimple("Datos no disponibles", "No se pudieron cargar los datos en la tabla de unidades", Alert.AlertType.ERROR);
        }

        conductores = FXCollections.observableArrayList();
        List<Colaborador> listaWSConductores = ColaboradorDAO.obtenerConductores();
        if (listaWSConductores != null) {
            conductores.addAll(listaWSConductores);
            tv_colaborador.setItems(conductores);
        } else {
            Utilidades.mostrarAlertaSimple("Datos no disponibles", "No se pudieron cargar los datos en la tabla de conductores", Alert.AlertType.ERROR);
        }

        if (registro != null) {
            tv_unidades.getSelectionModel().select(registro.getUnidad());
            tv_colaborador.getSelectionModel().select(registro.getColaborador());
        }

        configurarEstilosTabla();
    }

    public void inicializarValores(INotificacionOperacion observer ,RegistroColaboradorUnidad registro) {
        this.registro = registro;
        this.observer = observer;
        cargarInformacionTablas();
    }


    @FXML
    private void accionCancelar(ActionEvent event) {
        ((Stage) tv_colaborador.getScene().getWindow()).close();
    }

    @FXML
    private void accionAsignarUnidad(ActionEvent event) {
        Unidad unidad = tv_unidades.getSelectionModel().getSelectedItem();
        Colaborador colaborador = tv_colaborador.getSelectionModel().getSelectedItem();
        if (unidad != null && colaborador != null) {
            Mensaje msj = ColaboradorDAO.comprobarColaboradorUnidad(colaborador.getIdColaborador(), unidad.getIdUnidad());
            if (!msj.isError()) {
                asignarUnidad(unidad, colaborador);
            } else {
                comprobarCambioAsignacion(msj, colaborador, unidad);
            }

        } else {
            Utilidades.mostrarAlertaSimple("Advertencia", "Seleccione una Unidad/Colaboador para editarla.", Alert.AlertType.INFORMATION);
        }
    }

    private void comprobarCambioAsignacion(Mensaje msj, Colaborador colaborador, Unidad unidad) {
        boolean aceptado = Utilidades.mostrarAlertaConfirmacion("Advertencia", msj.getContenido() + "\n¿Desea cambiar las asignaciones?");
        if (aceptado) {
            Mensaje msjBorrado = ColaboradorDAO.quitarAsignacionUnidad(colaborador.getIdColaborador(), unidad.getIdUnidad());
            if (!msjBorrado.isError()) {
                asignarUnidad(unidad, colaborador);
            } else {
                Utilidades.mostrarAlertaSimple("", "Error al eliminar" + msjBorrado.getContenido(), Alert.AlertType.ERROR);
            }
        }
    }

    private void asignarUnidad(Unidad unidad, Colaborador colaborador) {
        Mensaje respuesta = ColaboradorDAO.asignarUnidad(colaborador.getIdColaborador(), unidad.getIdUnidad());
        if (!respuesta.isError()) {
            Utilidades.mostrarAlertaSimple("Colaborador asignado con exito", "El colaborador "
                    + colaborador.getNombre() + ", se asignó correctamente a la unidad con el vin " + unidad.getVin(),
                    Alert.AlertType.INFORMATION);
            cargarInformacionTablas();
            observer.notificarOperacionExitosa("cambio", respuesta.getContenido());
        } else {
            Utilidades.mostrarAlertaSimple("Error al asignar una unidad al colaborador", respuesta.getContenido(), Alert.AlertType.ERROR);
        }
    }
}
