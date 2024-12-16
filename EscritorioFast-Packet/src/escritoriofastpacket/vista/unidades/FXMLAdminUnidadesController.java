/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package escritoriofastpacket.vista.unidades;

import escritoriofastpacket.interfaz.INotificarOperacion;
import escritoriofastpacket.modelo.dao.UnidadDAO;
import escritoriofastpacket.modelo.pojo.Cliente;
import escritoriofastpacket.modelo.pojo.Unidad;
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
public class FXMLAdminUnidadesController implements Initializable, INotificarOperacion {

    private ObservableList<Unidad> unidades;
    private FilteredList<Unidad> busquedaUnidades;

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
    private TextField tf_buscar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionTabla();
        configurarFiltroBusqueda();
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

    @FXML
    private void btnAgregarUnidad(ActionEvent event) {
        irFormularioAgregarUnidad(this, null);
    }

    @FXML
    private void btnEliminarUnidad(ActionEvent event) {
        Unidad unidad = tv_unidades.getSelectionModel().getSelectedItem();
        if (unidad != null) {
            irFormularioEliminarUnidad(unidad, this);
        } else {
            Utilidades.mostrarAlertaSimple("Advertencia", "Seleccione una Unidad para eliminarla.", Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    private void btnEditarUnidad(ActionEvent event) {
        Unidad unidad = tv_unidades.getSelectionModel().getSelectedItem();
        if (unidad != null) {
            irFormularioAgregarUnidad(this, unidad);
        } else {
            Utilidades.mostrarAlertaSimple("Advertencia", "Seleccione una Unidad para editarla.", Alert.AlertType.INFORMATION);
        }
    }

    private void irFormularioAgregarUnidad(INotificarOperacion observador, Unidad unidad) {
        try {
            Stage escenario = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLAgregarUnidad.fxml"));
            Parent vista = loader.load();
            FXMLAgregarUnidadController controller = loader.getController();
            controller.inicializarValores(observador, unidad);

            Scene escena = new Scene(vista);
            escenario.setScene(escena);
            escenario.setTitle("Formulario de Unidad");
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.setResizable(false);
            escenario.showAndWait();
            
        } catch (Exception e) {
            e.printStackTrace();
            Utilidades.mostrarAlertaSimple("Error inesperado", "Ocurrio un error al mostrar el formulario, intentelo de nuevo.", Alert.AlertType.ERROR);
        }
    }

    private void irFormularioEliminarUnidad(Unidad unidad, INotificarOperacion obsevador) {
        try {
            Stage escenario = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLBajaUnidad.fxml"));
            Parent vista = loader.load();
            FXMLBajaUnidadController controller = loader.getController();
            controller.cargarDatos(unidad, obsevador);

            Scene escena = new Scene(vista);
            escenario.setScene(escena);
            escenario.setTitle("Baja de unidad");
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.setResizable(false);
            escenario.showAndWait();
            
        } catch (Exception e) {
            e.printStackTrace();
            Utilidades.mostrarAlertaSimple("Error inesperado", "Ocurrio un error al mostrar el formulario, intentelo de nuevo.", Alert.AlertType.ERROR);
        }
    }

    @Override
    public void notificarOperacionExitosa(String tipo, String nombre) {
        cargarInformacionTabla();
        configurarFiltroBusqueda();
    }

    @FXML
    private void btnHistorial(ActionEvent event) {
        irHistorialBajas();
    }

    private void irHistorialBajas() {
        try {
            Stage escenario = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLHistorialUnidades.fxml"));
            Parent vista = loader.load();

            Scene escena = new Scene(vista);
            escenario.setScene(escena);
            escenario.setTitle("Historial de Unidades");
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.setResizable(false);
            escenario.showAndWait();
            
        } catch (Exception e) {
            e.printStackTrace();
            Utilidades.mostrarAlertaSimple("Error inesperado", "Ocurrio un error al mostrar el historial, intentelo de nuevo.", Alert.AlertType.ERROR);
        }
    }

    private void configurarFiltroBusqueda() {
        busquedaUnidades = new FilteredList<>(unidades, b -> true);

        tf_buscar.textProperty().addListener((observable, oldValue, newValue) -> {
            busquedaUnidades.setPredicate(unidad -> {
                if (newValue == null || newValue.trim().isEmpty()) {
                    return true; // Mostrar todos los clientes si no hay filtro
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (unidad.getVin() != null && unidad.getVin().toLowerCase().contains(lowerCaseFilter)) {
                    return true; 
                }

                if (unidad.getNoIdentificacion()!= null && unidad.getNoIdentificacion().toLowerCase().contains(lowerCaseFilter)) {
                    return true; 
                }

                if (unidad.getMarca() != null && unidad.getMarca().toLowerCase().contains(lowerCaseFilter)) {
                    return true; 
                }

                return false; 
            });
        });

        SortedList<Unidad> sortedData = new SortedList<>(busquedaUnidades);
        sortedData.comparatorProperty().bind(tv_unidades.comparatorProperty());
        tv_unidades.setItems(sortedData);
    }

}
