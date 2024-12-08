/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package escritoriofastpacket.vista.unidades;

import escritoriofastpacket.modelo.dao.UnidadDAO;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author uriel
 */
public class FXMLHistorialUnidadesController implements Initializable {
    
    private ObservableList<Unidad> unidades;

    @FXML
    private TableView<Unidad> tv_historial;
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
    private TableColumn col_interno;
    @FXML
    private TableColumn col_motivo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionTabla();
    }

    private void configurarTabla() {
        col_anio.setCellValueFactory(new PropertyValueFactory("anio"));
        col_interno.setCellValueFactory(new PropertyValueFactory("noIdentificacion"));
        col_marca.setCellValueFactory(new PropertyValueFactory("marca"));
        col_modelo.setCellValueFactory(new PropertyValueFactory("modelo"));
        col_tipo.setCellValueFactory(new PropertyValueFactory("tipoUnidad"));
        col_vin.setCellValueFactory(new PropertyValueFactory("vin"));
        col_motivo.setCellValueFactory(new PropertyValueFactory("motivo"));
    }
    
    private void cargarInformacionTabla() {
        unidades = FXCollections.observableArrayList();
        List<Unidad> listaWS = UnidadDAO.historialUnidades();
        if (listaWS != null) {
            unidades.addAll(listaWS);
            tv_historial.setItems(unidades);
        } else {
            Utilidades.mostrarAlertaSimple("Datos no disponibles", "No se pudieron cargar los datos en la tabla", Alert.AlertType.ERROR);
        }
    }
    
    private void cerrarVenatana() {
        Stage escenario = (Stage) tv_historial.getScene().getWindow();
        escenario.close();
    }

    @FXML
    private void btnCerrar(ActionEvent event) {
        cerrarVenatana();
    }
    
}
