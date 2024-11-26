/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package escritoriofastpacket.vista.colaboradores;

import escritoriofastpacket.modelo.dao.ColaboradorDAO;
import escritoriofastpacket.modelo.pojo.Colaborador;
import escritoriofastpacket.modelo.pojo.Mensaje;
import escritoriofastpacket.modelo.pojo.Rol;
import escritoriofastpacket.observer.INotificacionOperacion;
import escritoriofastpacket.utils.Utilidades;
import escritoriofastpacket.validators.ColaboradorValidator;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author lizet
 */
public class FXMLFormularioColaboradorController implements Initializable {
    
    private boolean modoEdicion = false;
    INotificacionOperacion observador;
    Colaborador colaboradorEdicion;
    private ObservableList<Rol> roles;
    HashMap<String,Label> hashlbl = new LinkedHashMap<>();
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfApellidoPaterno;
    @FXML
    private TextField tfApellidoMaterno;
    @FXML
    private TextField tfCorreo;
    @FXML
    private TextField tfCURP;
    @FXML
    private TextField tfPassword;
    @FXML
    private TextField tfNoPersonal;
    @FXML
    private ComboBox<Rol> cbTipoColaborador;
    @FXML
    private TextField tfLicencia;
    @FXML
    private Label lbLicencia;
    @FXML
    private Button btnImagen;
    @FXML
    private Label lblErrorNombre;
    @FXML
    private Label lblErrorApellidoPaterno;
    @FXML
    private Label lblErrorApellidoMaterno;
    @FXML
    private Label lblErrorCorreoElectronico;
    @FXML
    private Label lblErrorCURP;
    @FXML
    private Label lblErrorPassword;
    @FXML
    private Label lblErrorNoPersonal;
    @FXML
    private Label lblErrorRol;
    @FXML
    private Label lblErrorlicencia;
    
    public void inicializarHashErrores(){
        hashlbl.put("nombre", lblErrorNombre);
        hashlbl.put("apellidoPaterno", lblErrorApellidoPaterno);
        hashlbl.put("apellidoMaterno", lblErrorApellidoMaterno);
        hashlbl.put("correo", lblErrorCorreoElectronico);
        hashlbl.put("curp", lblErrorCURP);
        hashlbl.put("password", lblErrorPassword);
        hashlbl.put("noPersonal", lblErrorNoPersonal);
        hashlbl.put("rol", lblErrorRol);
        hashlbl.put("licencia", lblErrorlicencia);
    }
    public void inicializarValores(INotificacionOperacion observador, Colaborador colaboradorEdicion){
        this.observador = observador;
        this.colaboradorEdicion = colaboradorEdicion;
        if(colaboradorEdicion != null){
            modoEdicion = true;
            cargarDatosEdicion();
        }
    }
    public void cargarDatosEdicion(){
        tfNombre.setText(colaboradorEdicion.getNombre());
        tfApellidoMaterno.setText(colaboradorEdicion.getApellidoMaterno());
        tfApellidoPaterno.setText(colaboradorEdicion.getApellidoPaterno());
        tfCURP.setText(colaboradorEdicion.getCurp());
        tfCorreo.setText(colaboradorEdicion.getCorreo());
        tfPassword.setText(colaboradorEdicion.getPassword());
        tfNoPersonal.setText(colaboradorEdicion.getNoPersonal());
        tfNoPersonal.setDisable(true);
        int pocisionRol = obtenerPosicionRol(colaboradorEdicion.getIdRol());
        cbTipoColaborador.getSelectionModel().select(pocisionRol);
        cbTipoColaborador.setDisable(true);
        if(pocisionRol != 3){
            tfLicencia.setText(colaboradorEdicion.getNoLicencia());
        }
    }
    private void cargarRoles(){
        roles = FXCollections.observableArrayList();
        List<Rol> listaWS = ColaboradorDAO.obtentenerRolesColaborador();
        if(listaWS != null){
            roles.addAll(listaWS);
            cbTipoColaborador.setItems(roles);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializarHashErrores();
        cargarRoles();
    }    

    @FXML
    private void accionCancelar(ActionEvent event) {
        cerrarVentana();
    }

    @FXML
    private void accionAceptar(ActionEvent event) {
        String noPersonal = tfNoPersonal.getText();
        String nombre = tfNombre.getText();
        String apellidoPaterno = tfApellidoPaterno.getText();
        String apellidoMaterno = tfApellidoMaterno.getText();
        String correo = tfCorreo.getText();
        String CURP = tfCURP.getText();
        String contraseña = tfPassword.getText();
        
        int idRol = (cbTipoColaborador.getSelectionModel().getSelectedItem() != null) 
                ? cbTipoColaborador.getSelectionModel().getSelectedItem().getIdRol() : 0;
        
        Colaborador colaborador = new Colaborador();
        colaborador.setNoPersonal(noPersonal);
        colaborador.setApellidoPaterno(apellidoPaterno);
        colaborador.setApellidoMaterno(apellidoMaterno);
        colaborador.setNombre(nombre);
        colaborador.setCorreo(correo);
        colaborador.setPassword(contraseña);
        colaborador.setCurp(CURP);
        colaborador.setIdRol(idRol);
        if(idRol==3){
            colaborador.setNoLicencia(tfLicencia.getText());
        }
        if(sonCamposValidos(colaborador)){ 
            if(!modoEdicion){            
                enviarDatosColaborador(colaborador);
            }else{
                colaborador.setIdColaborador(colaboradorEdicion.getIdColaborador());
                editarDatosColaborador(colaborador);
            }
        }else{
            Utilidades.mostrarAlertaSimple("Error uno o varios de los campos no son valídos", correo, Alert.AlertType.ERROR);
        }
    }
    
    private void enviarDatosColaborador(Colaborador colaborador){
        Mensaje respuesta = ColaboradorDAO.registrarColaborador(colaborador);
        if(!respuesta.isError()){
            Utilidades.mostrarAlertaSimple("Colaborador registrado", "La información del colaborador "+
                    colaborador.getNombre()+", se registro correctamente", Alert.AlertType.INFORMATION);
            cerrarVentana();
            observador.notificarOperacionExitosa("Guardar", colaborador.getNombre());
        }else{
            Utilidades.mostrarAlertaSimple("Error al registrar colaborador", respuesta.getContenido(), Alert.AlertType.ERROR);
        }
    }
    
    private void editarDatosColaborador(Colaborador colaborador){
        Mensaje respuesta = ColaboradorDAO.modificar(colaborador);
        if(!respuesta.isError()){
            Utilidades.mostrarAlertaSimple("Colaborador modificado", "La información del colaborador "+
                    colaborador.getNombre()+", se modificó correctamente", Alert.AlertType.INFORMATION);
            cerrarVentana();
            observador.notificarOperacionExitosa("Actualización", colaborador.getNombre());
        }else{
            Utilidades.mostrarAlertaSimple("Error al modificar colaborador", respuesta.getContenido(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void agregarFoto(ActionEvent event) {
    }
    
    private int obtenerPosicionRol(int idRol){
        for (int i = 0; i < roles.size(); i++) {
            if(idRol == roles.get(i).getIdRol()){
                return i;
            }
        }
        return 0;
    }
    
    private void cerrarVentana(){
        ((Stage) tfApellidoMaterno.getScene().getWindow()).close();
    }
    private boolean sonCamposValidos(Colaborador colaborador){
        return !ColaboradorValidator.validarColaborador(colaborador, hashlbl);
    }

    @FXML
    private void accionBomprobarConductor(ActionEvent event) {
        int idRol = (cbTipoColaborador.getSelectionModel().getSelectedItem() != null) 
                ? cbTipoColaborador.getSelectionModel().getSelectedItem().getIdRol() : 0;
        if(idRol == 3 ){
            lbLicencia.setVisible(true);
            tfLicencia.setVisible(true);
        } else {
            if(lbLicencia.isVisible() && tfLicencia.isVisible()){
                lbLicencia.setVisible(false);
                tfLicencia.setVisible(false);
            }
        }
        
    }
}
