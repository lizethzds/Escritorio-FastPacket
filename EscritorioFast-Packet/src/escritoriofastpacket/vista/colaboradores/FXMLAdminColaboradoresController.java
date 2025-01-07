/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package escritoriofastpacket.vista.colaboradores;

import escritoriofastpacket.FXMLMenuPrincipalController;
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
import escritoriofastpacket.observer.INotificacionCambio;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

public class FXMLAdminColaboradoresController implements Initializable, INotificacionOperacion {
    private ObservableList<Colaborador> colaboradores;
    private Colaborador colaboradorSesion;
    private INotificacionCambio instaciaMenu;
    FilteredList<Colaborador> listaColaboradores;
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
    
     public void inicializarDatos(Colaborador colaboradorSesion, INotificacionCambio instacianMenu) {
        this.colaboradorSesion = colaboradorSesion;
        this.instaciaMenu = instacianMenu;
        cargarInformacionTabla();
    }
     private void configurarTabla(){
        colNoPersonal.setCellValueFactory(new PropertyValueFactory<>("noPersonal"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellidoPaterno.setCellValueFactory(new PropertyValueFactory<>("apellidoPaterno"));
        colApellidoMaterno.setCellValueFactory(new PropertyValueFactory<>("apellidoMaterno"));
        colCURP.setCellValueFactory(new PropertyValueFactory<>("curp"));
        colRol.setCellValueFactory(new PropertyValueFactory<>("rol"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
    }
    
   private void cargarInformacionTabla() {
    colaboradores.clear();
    List<Colaborador> listaWs = ColaboradorDAO.obtenerColaboradores();
    if (listaWs != null && !listaWs.isEmpty()) {
        listaWs.removeIf(colaborador -> colaborador.getIdColaborador() == 1);

        colaboradores.addAll(listaWs);
        tvColaboradores.setItems(colaboradores);
    } else {
        Utilidades.mostrarAlertaSimple("Datos no disponibles", 
                "Por el momento no se puede cargar la informacion de los colaboradores",
                Alert.AlertType.ERROR);
    }
    tfBuscarColaborador.setText("");
    configurarFiltroBusqueda();
}


    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colaboradores = FXCollections.observableArrayList();
        configurarTabla();
         
    }    
    
    private boolean comprobarEliminacion(Colaborador colaborador){
        Mensaje respuestaEnvios = ColaboradorDAO.comprobarEnvios(colaborador.getIdColaborador());
        if(!respuestaEnvios.isError()){
            boolean aceptado;
            if(!respuestaEnvios.getContenido().contains("no cuenta")){
                aceptado = false;
                Utilidades.mostrarAlertaSimple("Eliminar colaborador", 
                    "Estas seguro de eliminar el colaborador "+colaborador.getNombre()+"?\n"+
                     respuestaEnvios.getContenido() + "\n" + 
                     "Por favor elimine los envios antes de eliminar el colaborador",Alert.AlertType.ERROR);               
            }else{
                aceptado = Utilidades.mostrarAlertaConfirmacion("Eliminar colaborador", 
                    "Estas seguro de eliminar el colaborador "+colaborador.getNombre()+"?");
            }
            if(aceptado){
                    return true;
             }
        }else{
            Utilidades.mostrarAlertaSimple("Error al eliminar colaborador",
                      "Se ha producido un error al eliminar el colaborador por favor intentelo mas tarde", 
                       Alert.AlertType.WARNING);
        }
        return false;
    }

    private void eliminarColaboradorConfirmacion(int idColaborador){
        
            Mensaje respuesta = ColaboradorDAO.eliminar(idColaborador);
            if(!respuesta.isError()){
                cargarInformacionTabla();
                 Utilidades.mostrarAlertaSimple("Colaborador eliminado", 
                         "Colaborador eliminado con exíto", 
                         Alert.AlertType.INFORMATION);
            }else{
                System.err.println("error: " + respuesta.getContenido());
                Utilidades.mostrarAlertaSimple("Error al eliminar colaborador",
                        "Se ha producido un error al eliminar el colaborador por favor intentelo mas tarde", 
                        Alert.AlertType.WARNING);
            } 
    }
    
    @FXML
    private void eliminarColaborador(ActionEvent event) {
        Colaborador colaborador = tvColaboradores.getSelectionModel().getSelectedItem();
        Mensaje validacion = comprobarColaboradorEliminacion(colaborador);
        if(!validacion.isError()){
            if(comprobarEliminacion(colaborador)){
                eliminarColaboradorConfirmacion(colaborador.getIdColaborador());
            }  
        }else{
            Utilidades.mostrarAlertaSimple("seleccione un colaborador", 
                    validacion.getContenido(),
                    Alert.AlertType.ERROR);
        }
    }
    
    private Mensaje comprobarColaboradorEliminacion(Colaborador colaborador){
        if(colaborador == null)
            return new Mensaje(true,"Tiene que seleccionar un colaborador para poder eliminarlo");

        if(colaborador.equals(colaboradorSesion))
            return new Mensaje(true,"No puede eliminar al colaborador con la sesión activa, "
                    + "por favor inicie sesión en otra cuenta para eliminar al colaborador");
        
        return new Mensaje(false,"sin problemas");
    }
    
    @FXML
    private void formularioRegistrarColaborador(ActionEvent event) {
        irPantallaFormulario(this,null,"Agregar colaborador");
    }

    @FXML
    private void formularioEditarColaborador(ActionEvent event) {
        Colaborador colaborador = tvColaboradores.getSelectionModel().getSelectedItem();
        if(colaborador != null){
            irPantallaFormulario(this, colaborador,"Editar colaborador");
        }else{
            Utilidades.mostrarAlertaSimple("seleccione un colaborador", 
                    "Tiene que seleccionar un colaborador para poder abrir el menu de editar",
                    Alert.AlertType.ERROR);
        }
    }
    private void irPantallaFormulario(INotificacionOperacion observador, Colaborador colaborador, String tituloPantalla){
        Stage nuevoEcenario = new Stage();
        try {

            FXMLLoader cargador = new FXMLLoader(getClass().getResource("FXMLFormularioColaborador.fxml"));
            Parent nuevoParent = cargador.load();
            FXMLFormularioColaboradorController controlador = cargador.getController();
            controlador.inicializarValores(observador, colaborador,this.instaciaMenu);
            Scene ecenaAdmin = new Scene(nuevoParent);
            nuevoEcenario.setScene(ecenaAdmin);
            nuevoEcenario.setTitle(tituloPantalla);
            nuevoEcenario.initModality(Modality.APPLICATION_MODAL);
            nuevoEcenario.setResizable(false);
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
    private void configurarFiltroBusqueda() {
        listaColaboradores = new FilteredList<>(colaboradores, b -> true);

        tfBuscarColaborador.textProperty().addListener((observable, oldValue, newValue) -> {
            listaColaboradores.setPredicate(colaborador -> {
                if (newValue == null || newValue.trim().isEmpty()) {
                    return true; 
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (colaborador.getNombre() != null && colaborador.getNombre().toLowerCase().contains(lowerCaseFilter)) {
                    return true; 
                }
                if (colaborador.getNoPersonal() != null && colaborador.getNoPersonal().toLowerCase().contains(lowerCaseFilter)) {
                    return true; 
                }
                if (colaborador.getRol() != null && colaborador.getRol().toLowerCase().contains(lowerCaseFilter)) {
                    return true; 
                }

                return false; 
            });
        });

        SortedList<Colaborador> sortedData = new SortedList<>(listaColaboradores);
        sortedData.comparatorProperty().bind(tvColaboradores.comparatorProperty());
        tvColaboradores.setItems(sortedData);
    }    
    
    private void irPantallaASignarUnidad(String tituloPantalla){
        Stage nuevoEcenario = new Stage();
        try {

            FXMLLoader cargador = new FXMLLoader(getClass().getResource("FXMLListaAsignaciones.fxml"));
            Parent nuevoParent = cargador.load();
            Scene ecenaAdmin = new Scene(nuevoParent);
            nuevoEcenario.setScene(ecenaAdmin);
            nuevoEcenario.setTitle(tituloPantalla);
            nuevoEcenario.initModality(Modality.APPLICATION_MODAL);
            nuevoEcenario.setResizable(false);
            nuevoEcenario.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            Utilidades.mostrarAlertaSimple("Error", "Por el momento no se puede mostrar la vetana de asignación",
                   Alert.AlertType.ERROR);
        }  
    }

    @FXML
    private void asignarColaboradorAction(ActionEvent event) {
      irPantallaASignarUnidad("Lista de asignaciones");
    }
}
