/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package escritoriofastpacket.vista.unidades;

import escritoriofastpacket.interfaz.INotificarOperacion;
import escritoriofastpacket.modelo.dao.UnidadDAO;
import escritoriofastpacket.modelo.pojo.Mensaje;
import escritoriofastpacket.modelo.pojo.TipoUnidad;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author uriel
 */
public class FXMLAgregarUnidadController implements Initializable {

    private INotificarOperacion observador;
    private Unidad unidadEditada;
    private boolean modoEdicion = false;
    private ObservableList<TipoUnidad> tipoUnidad;

    @FXML
    private TextField tf_vin;
    @FXML
    private TextField tf_marca;
    @FXML
    private TextField tf_modelo;
    @FXML
    private TextField tf_anio;
    @FXML
    private ComboBox<TipoUnidad> cb_tipo;
    @FXML
    private Label lb_vin;
    @FXML
    private Label lb_marca;
    @FXML
    private Label lb_modelo;
    @FXML
    private Label lb_tipoUnidad;
    @FXML
    private Label lb_anio;
    @FXML
    private Text lb_titulo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarTipoUnidad();
    }

    public void inicializarValores(INotificarOperacion observador, Unidad unidadEditada) {
        this.observador = observador;
        this.unidadEditada = unidadEditada;
        if (unidadEditada != null) {
            modoEdicion = true;
            cargarDatosEdicion();
            lb_titulo.setText("Editar unidad");
        }
    }

    private void cargarDatosEdicion() {
        tf_vin.setText(this.unidadEditada.getVin());
        tf_marca.setText(unidadEditada.getMarca());
        tf_modelo.setText(unidadEditada.getModelo());
        tf_anio.setText(unidadEditada.getAnio());
        int posicionTipoUnidad = obtenerPosicionTipoUnidad(unidadEditada.getIdTipoUnidad());
        cb_tipo.getSelectionModel().select(posicionTipoUnidad);
        tf_vin.setEditable(false);
    }

    @FXML
    private void btnGuardarUnidad(ActionEvent event) {

        String anio = tf_anio.getText();
        String marca = tf_marca.getText();
        String modelo = tf_modelo.getText();
        String vin = tf_vin.getText();
        int idTipoUnidad = (cb_tipo.getSelectionModel().getSelectedItem() != null)
                ? cb_tipo.getSelectionModel().getSelectedItem().getIdTipoUnidad()
                : -1;

        Unidad unidad = new Unidad();
        unidad.setAnio(anio);
        unidad.setMarca(marca);
        unidad.setModelo(modelo);
        unidad.setVin(vin);
        unidad.setIdTipoUnidad(idTipoUnidad);

        if (camposValidos(marca, modelo, anio, vin, idTipoUnidad)) {
            if (!modoEdicion) {
                String noIdentificacion = vin.substring(0, 4) + anio;
                unidad.setNoIdentificacion(noIdentificacion);
                registrarUnidad(unidad);
            } else {
                String noIdentificacion = vin.substring(0, 4) + anio;
                unidad.setNoIdentificacion(noIdentificacion);
                unidad.setIdUnidad(this.unidadEditada.getIdUnidad());
                editarUnidad(unidad);
            }

        }

    }

    @FXML
    private void btnCancelar(ActionEvent event) {
        cerrarVenatana();
    }

    private void cargarTipoUnidad() {
        tipoUnidad = FXCollections.observableArrayList();
        List<TipoUnidad> listaWS = UnidadDAO.obtenerTipoUnidad();
        if (listaWS != null) {
            tipoUnidad.addAll(listaWS);
            cb_tipo.setItems(tipoUnidad);
        }
    }

    private void cerrarVenatana() {
        Stage escenario = (Stage) cb_tipo.getScene().getWindow();
        escenario.close();
    }

    private void registrarUnidad(Unidad unidad) {
        Mensaje respuesta = UnidadDAO.registrarUnidad(unidad);
        if (respuesta.isError()) {
            Utilidades.mostrarAlertaSimple("Error", respuesta.getContenido(), Alert.AlertType.ERROR);
        } else {
            Utilidades.mostrarAlertaSimple("Registro", "Registro realizado correctamente", Alert.AlertType.INFORMATION);
            cerrarVenatana();
            observador.notificarOperacionExitosa("Registro", unidad.getVin());
        }
    }

    private void editarUnidad(Unidad unidad) {
        Mensaje respuesta = UnidadDAO.editarUnidad(unidad);
        if (respuesta.isError()) {
            Utilidades.mostrarAlertaSimple("Error", respuesta.getContenido(), Alert.AlertType.ERROR);
        } else {
            Utilidades.mostrarAlertaSimple("Editar", "Edición realizado correctamente", Alert.AlertType.INFORMATION);
            cerrarVenatana();
            observador.notificarOperacionExitosa("Editar", "editar");
        }
    }

    private boolean camposValidos(String marca, String modelo, String anio, String vin, int idTipoUnidad) {
        boolean valido = true;

        lb_marca.setText("");
        lb_anio.setText("");
        lb_modelo.setText("");
        lb_tipoUnidad.setText("");
        lb_vin.setText("");
        lb_tipoUnidad.setText("");

        if (marca.isEmpty()) {
            lb_marca.setText("Este campo es requerido.");
            valido = false;
        }

        if (modelo.isEmpty()) {
            lb_modelo.setText("Este campo es requerido.");
            valido = false;
        }

        if (anio.isEmpty()) {
            lb_anio.setText("Este campo es requerido.");
            valido = false;
        } else if (!anio.matches("\\d{4}")) {
            lb_anio.setText("Debe ser un número de 4 dígitos.");
            valido = false;
        }

        String erroresVin = "";
        if (vin.isEmpty()) {
            erroresVin = "Este campo es requerido.";
        } else if (vin.length() != 17) {
            erroresVin = "El VIN debe tener exactamente 17 caracteres.";
        } else if (!vin.matches("[a-zA-Z0-9]*")) {
            erroresVin = "Solo se permiten letras y números.";
        }
        if (!erroresVin.isEmpty()) {
            lb_vin.setText(erroresVin);
            valido = false;
        }

        if (idTipoUnidad == -1) {
            lb_tipoUnidad.setText("Selecione un tipo de unidad");
            valido = false;
        }

        return valido;
    }

    private int obtenerPosicionTipoUnidad(int idTipoUnidad) {
        for (int i = 0; i < tipoUnidad.size(); i++) {
            if (idTipoUnidad == tipoUnidad.get(i).getIdTipoUnidad()) {
                return i;
            }
        }
        return 0;
    }

}
