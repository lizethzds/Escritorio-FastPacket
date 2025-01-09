/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package escritoriofastpacket.vista.colaboradores;

import escritoriofastpacket.FXMLMenuPrincipalController;
import escritoriofastpacket.modelo.dao.ColaboradorDAO;
import escritoriofastpacket.modelo.pojo.Colaborador;
import escritoriofastpacket.modelo.pojo.Mensaje;
import escritoriofastpacket.modelo.pojo.Rol;
import escritoriofastpacket.observer.INotificacionCambio;
import escritoriofastpacket.observer.INotificacionOperacion;
import escritoriofastpacket.utils.Utilidades;
import escritoriofastpacket.validators.ColaboradorValidator;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

public class FXMLFormularioColaboradorController implements Initializable {

    private boolean modoEdicion = false;
    private boolean vboxExtendido = false;
    private Image imagenSeleccionada = null;
    private ObservableList<Rol> roles;
    private Colaborador colaboradorSesion;
    INotificacionOperacion observador;
    INotificacionCambio observadorCambio;
    Colaborador colaboradorEdicion = null;
    HashMap<String, Label> hashlbl = new LinkedHashMap<>();
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
    @FXML
    private ImageView imgPerfil;
    @FXML
    private VBox VboxPrincipal;

    public void inicializarHashErrores() {
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
    
    public void inicializarValores(INotificacionOperacion observador, Colaborador colaboradorEdicion, INotificacionCambio instaciaMenu, Colaborador colaboradorSesion) {
        this.observador = observador;
        this.colaboradorEdicion = colaboradorEdicion;
        this.colaboradorSesion = colaboradorSesion;
        cargarRoles();
        if (colaboradorEdicion != null) {
            modoEdicion = true;
            this.observadorCambio = instaciaMenu;
            cargarDatosEdicion();
        }
    }

    public void cargarDatosEdicion() {
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
        if (cbTipoColaborador.getSelectionModel().getSelectedItem().getRol().equals("Conductor")) {
            tfLicencia.setVisible(true);
            lbLicencia.setVisible(true);
            tfLicencia.setText(colaboradorEdicion.getNoLicencia());
        }
    }

    private void cargarRoles() {
        roles = FXCollections.observableArrayList();
        List<Rol> listaWS;
        if(colaboradorSesion != null && "Ejecutivo de tienda".equals(colaboradorSesion.getRol())){
            listaWS = new LinkedList();
            listaWS.add(new Rol(3,"Conductor"));
            roles.addAll(listaWS);            
            cbTipoColaborador.setItems(roles);
        }else{
            listaWS = ColaboradorDAO.obtentenerRolesColaborador();
            if (listaWS != null) {
                roles.addAll(listaWS);            
                cbTipoColaborador.setItems(roles);
            }
        }
        
    }

    private int obtenerPosicionRol(int idRol) {
        for (int i = 0; i < roles.size(); i++) {
            if (idRol == roles.get(i).getIdRol()) {
                return i;
            }
        }
        return 0;
    }
    private void configurarDatosEntrada() {
        tfLicencia.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tfLicencia.setText(oldValue);
            }
        });
        tfNombre.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*")) {
                tfNombre.setText(oldValue);
            }
        });
        
        tfApellidoMaterno.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*")) {
                tfApellidoMaterno.setText(oldValue);
            }
        });
        
        tfApellidoPaterno.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*")) {
                tfApellidoPaterno.setText(oldValue);
            }
        });
        
        
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializarHashErrores();
        
        Platform.runLater(() -> {
            if (modoEdicion) {
                obtenerImagenColaboradorEdicion(colaboradorEdicion.getIdColaborador());
            }
        });
        configurarDatosEntrada();
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
       
        String rol = (cbTipoColaborador.getSelectionModel().getSelectedItem() != null)
                ? cbTipoColaborador.getSelectionModel().getSelectedItem().getRol() : "";

        Colaborador colaborador = new Colaborador();
        colaborador.setNoPersonal(noPersonal);
        colaborador.setApellidoPaterno(apellidoPaterno);
        colaborador.setApellidoMaterno(apellidoMaterno);
        colaborador.setNombre(nombre);
        colaborador.setCorreo(correo);
        colaborador.setPassword(contraseña);
        colaborador.setCurp(CURP);
        colaborador.setIdRol(idRol);
        colaborador.setRol(rol);
        if (idRol == 3) {
            colaborador.setNoLicencia(tfLicencia.getText());
        }
        Mensaje mensaje = sonCamposValidos(colaborador);
        if (!mensaje.isError()) {
            if (!modoEdicion) { 
                colaborador.setFotografia(imagenSeleccionada != null ? imageToBase64(imagenSeleccionada) : null);
                enviarDatosColaborador(colaborador);
            } else {
                colaborador.setFotografia(imagenSeleccionada != null ? imageToBase64(imagenSeleccionada) : colaboradorEdicion.getFotografia());
                colaborador.setIdColaborador(colaboradorEdicion.getIdColaborador());
                editarDatosColaborador(colaborador);
            }
        } else {
            Utilidades.mostrarAlertaSimple("Error", mensaje.getContenido(), Alert.AlertType.ERROR);
        }
    }


    private void enviarDatosColaborador(Colaborador colaborador) {
        Mensaje respuesta = ColaboradorDAO.registrarColaborador(colaborador);
        if (!respuesta.isError()) {
            Utilidades.mostrarAlertaSimple("Colaborador registrado", "La información del colaborador "
                    + colaborador.getNombre() + ", se registro correctamente", Alert.AlertType.INFORMATION);
            cerrarVentana();
            observador.notificarOperacionExitosa("Guardar", colaborador.getNombre());
        } else {
            Utilidades.mostrarAlertaSimple("Error al registrar colaborador", respuesta.getContenido(), Alert.AlertType.ERROR);
        }
    }

    private void editarDatosColaborador(Colaborador colaborador) {
        Mensaje respuesta = ColaboradorDAO.modificar(colaborador);
        if (!respuesta.isError()) {
            Utilidades.mostrarAlertaSimple("Colaborador modificado", "La información del colaborador "
                    + colaborador.getNombre() + ", se modificó correctamente", Alert.AlertType.INFORMATION);
            cerrarVentana();
            observador.notificarOperacionExitosa("Actualización", colaborador.getNombre());
            colaborador.setFotografia(null);
            observadorCambio.notificarCambioColaboradorSesion(colaborador);
        } else {
            Utilidades.mostrarAlertaSimple("Error al modificar colaborador", respuesta.getContenido(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void agregarFoto(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Imagen");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
        if (selectedFile != null) {
            long fileSizeInBytes = selectedFile.length();
            long fileSizeInMB = fileSizeInBytes / (1024 * 1024);

            if (fileSizeInMB < 1) {
                Image image = new Image(selectedFile.toURI().toString());
                imagenSeleccionada = image;
                imgPerfil.setImage(image);
                ajustarTamañoVboxImagen();
            } else {
                Utilidades.mostrarAlertaSimple("Archivo demasiado grande",
                        "Archivo demasiado grande. Seleccione una imagen menor a 1MB.",
                        Alert.AlertType.WARNING);
            }

        }
    }

    private void ajustarTamañoVboxImagen() {
        btnImagen.setText("Cambiar imagen");
        imgPerfil.setFitWidth(200);
        imgPerfil.setFitHeight(200);
        imgPerfil.setPreserveRatio(true);
        imgPerfil.setSmooth(true);
        imgPerfil.setCache(true);
        if (!vboxExtendido) {
            VboxPrincipal.setMinHeight(VboxPrincipal.getHeight() + 200);
            vboxExtendido = true;
        }
    }

    private void obtenerImagenColaboradorEdicion(Integer idColaborador) {
        Mensaje respuesta = ColaboradorDAO.obtenerImagen(idColaborador);
        if (!respuesta.isError()) {
            colaboradorEdicion.setFotografia(respuesta.getContenido());
            imgPerfil.setImage(base64ToImage(respuesta.getContenido()));
            ajustarTamañoVboxImagen();
        }
    }

    public String imageToBase64(Image image) {
        try {
            java.awt.image.BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", outputStream);
            byte[] imageBytes = outputStream.toByteArray();
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Image base64ToImage(String base64String) {
        try {
            byte[] imageBytes = Base64.getDecoder().decode(base64String);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
            return new Image(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void cerrarVentana() {
        ((Stage) tfApellidoMaterno.getScene().getWindow()).close();
    }

    private Mensaje sonCamposValidos(Colaborador colaborador) {
        return ColaboradorValidator.validarColaborador(colaborador, colaboradorEdicion , hashlbl);
    }

    @FXML
    private void accionBomprobarConductor(ActionEvent event) {
        int idRol = (cbTipoColaborador.getSelectionModel().getSelectedItem() != null)
                ? cbTipoColaborador.getSelectionModel().getSelectedItem().getIdRol() : 0;
        if (idRol == 3) {
            lbLicencia.setVisible(true);
            tfLicencia.setVisible(true);
        } else {
            if (lbLicencia.isVisible() && tfLicencia.isVisible()) {
                lbLicencia.setVisible(false);
                tfLicencia.setVisible(false);
            }
        }

    }
}
