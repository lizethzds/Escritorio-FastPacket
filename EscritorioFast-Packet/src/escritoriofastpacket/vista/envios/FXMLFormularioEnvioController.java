
package escritoriofastpacket.vista.envios;

import escritoriofastpacket.interfaz.INotificarOperacion;
import escritoriofastpacket.modelo.dao.CatalogoDAO;
import escritoriofastpacket.modelo.dao.ClienteDAO;
import escritoriofastpacket.modelo.dao.ColaboradorDAO;
import escritoriofastpacket.modelo.dao.EnvioDAO;
import escritoriofastpacket.modelo.pojo.Cliente;
import escritoriofastpacket.modelo.pojo.Colaborador;
import escritoriofastpacket.modelo.pojo.DatosRegistroEnvio;
import escritoriofastpacket.modelo.pojo.Direccion;
import escritoriofastpacket.modelo.pojo.Envio;
import escritoriofastpacket.modelo.pojo.Estado;
import escritoriofastpacket.modelo.pojo.EstadoEnvio;
import escritoriofastpacket.modelo.pojo.Mensaje;
import escritoriofastpacket.modelo.pojo.Municipio;
import escritoriofastpacket.utils.Utilidades;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author lizet
 */
public class FXMLFormularioEnvioController implements Initializable {
    
    private INotificarOperacion observador;
    private Envio envioEdicion;
    private DatosRegistroEnvio datosEnvio;
    private boolean modoEdicion = false;
    
    private ObservableList<Estado> estados;
    private ObservableList<Municipio> municipios;
    private ObservableList<Cliente> clientes;
    private ObservableList<Colaborador> conductores;
     private ObservableList<EstadoEnvio> estadosEnvio;
    
    

    @FXML
    private ComboBox<Cliente> cbClientes;
    @FXML
    private ComboBox<Estado> cbEstado;
    @FXML
    private ComboBox<Municipio> cbMunicipio;
    @FXML
    private TextField tfCalleDestino;
    @FXML
    private TextField tfColoniaDestino;
    @FXML
    private TextField tfNumeroDestino;
    @FXML
    private TextField tfCodigoPostal;
    @FXML
    private ComboBox<Colaborador> cbConductores;
    @FXML
    private TextField tfCostoEnvio;
    @FXML
    private ComboBox<EstadoEnvio> cbEstatus;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarEstados();
        cargarClientes();
        cargarConductores();
        cargarEstatusEnvio();
        configuracionSeleccionEstado();
        configurarDatosEntrada();
    }    

    @FXML
    private void btnGuardarEnvio(ActionEvent event) {
       
        if( validarCamposLlenos()){
            DatosRegistroEnvio datosRegistroEnvio = new DatosRegistroEnvio();
            
            Envio envio = new Envio();
            float costoEnvio = Float.parseFloat(tfCostoEnvio.getText());
            envio.setCostoEnvio(costoEnvio);
            Integer idColaborador = (cbConductores.getSelectionModel().getSelectedItem() != null) 
                    ? cbConductores.getSelectionModel().getSelectedItem().getIdColaborador()
                    : 0;
            envio.setIdColaborador(idColaborador);
            
            Integer idCliente = (cbClientes.getSelectionModel().getSelectedItem() != null) 
                    ? cbClientes.getSelectionModel().getSelectedItem().getIdCliente()
                    :0;
            envio.setIdCliente(idCliente);
  
            envio.setIdEstadoEnvio(1);
            
            Direccion direccion = new Direccion();
            direccion.setCalle(tfCalleDestino.getText());
            direccion.setCodigoPostal(tfCodigoPostal.getText());
            direccion.setColonia(tfColoniaDestino.getText());
            direccion.setNumero(tfNumeroDestino.getText());
            Integer idMunicipio = (cbMunicipio.getSelectionModel().getSelectedItem() != null) 
            ? cbMunicipio.getSelectionModel().getSelectedItem().getIdMunicipio() 
            : 0;
            direccion.setIdMunicipio(idMunicipio);
            
            
            datosRegistroEnvio.setEnvio(envio);
            datosRegistroEnvio.setDireccion(direccion);
            
            
            if(modoEdicion == false){
                guardarDatosEnvio(datosRegistroEnvio);
        }else{
          datosRegistroEnvio.getEnvio().setIdEnvio(envioEdicion.getIdEnvio());
          Integer idEstadoEnvio = (cbEstatus.getSelectionModel().getSelectedItem() != null)
                    ? cbEstatus.getSelectionModel().getSelectedItem().getIdEstadoEnvio()
                    :0;
         datosRegistroEnvio.getEnvio().setIdEstadoEnvio(idEstadoEnvio);
          editarRegistroEnvio(datosRegistroEnvio);
            
         
        }
        }
    }
    
 
    
    @FXML
    private void btnCancelar(ActionEvent event) {
        cerrarPantalla();
    }

    void inicializarValores(INotificarOperacion observador, Envio envioEdicion) {
        this.observador = observador;
        this.envioEdicion = envioEdicion;
        if(envioEdicion != null){
            modoEdicion = true;
            cargarDatosEnvio(envioEdicion.getIdEnvio());
        }
    }

     
    private void cargarDatosEnvio(Integer idEnvio) {
      datosEnvio = EnvioDAO.obtenerDetallesEnvio(idEnvio);
      
      tfCalleDestino.setText(datosEnvio.getDireccion().getCalle());
      tfCodigoPostal.setText(datosEnvio.getDireccion().getCodigoPostal());
      tfCostoEnvio.setText(String.valueOf(datosEnvio.getEnvio().getCostoEnvio()));
      tfColoniaDestino.setText(datosEnvio.getDireccion().getColonia());
      tfNumeroDestino.setText(datosEnvio.getDireccion().getNumero());
      Integer idCliente = datosEnvio.getEnvio().getIdCliente();
      Integer idColaborador = datosEnvio.getEnvio().getIdColaborador();
      Integer idEstadoEnvio = datosEnvio.getEnvio().getIdEstadoEnvio();
      Integer idMunicipio = datosEnvio.getDireccion().getIdMunicipio();
      Estado estadoDireccion = CatalogoDAO.obtenerEstadoMunicipio(idMunicipio);
      Integer idEstado = estadoDireccion.getIdEstado();
    

      int posicionEstado = buscarIdEstado(idEstado);
      cbEstado.getSelectionModel().select(posicionEstado);

      cargarMunicipios(idEstado);
      int posicionMunicipio = buscarIdMunicipio(idMunicipio);
      cbMunicipio.getSelectionModel().select(posicionMunicipio);
        
      int posicionConductor = buscarIdColaborador(idColaborador);
      cbConductores.getSelectionModel().select(posicionConductor);
        
      int posicionCliente = buscarIdCliente(idCliente);
      cbClientes.getSelectionModel().select(posicionCliente);
        
      int posicionEstatus = buscarIdEstadoEnvio(idEstadoEnvio);
      cbEstatus.getSelectionModel().select(posicionEstatus);
   
      
    }


   //Carga de comboBox
    
     private void cargarEstados() {
      estados = FXCollections.observableArrayList();
      List<Estado> listaWS = CatalogoDAO.obtenerEstados();
      if(listaWS != null){
          estados.addAll(listaWS);
          cbEstado.setItems(estados);
      }
    }
    
    
     private void cargarMunicipios(Integer idEstado) {
        municipios = FXCollections.observableArrayList();
        List<Municipio> inf = CatalogoDAO.obtenerMunicipios(idEstado);
        municipios.addAll(inf);
        cbMunicipio.setItems(municipios);
    }

     private void configuracionSeleccionEstado() {
        cbEstado.valueProperty().addListener(new ChangeListener<Estado>() {
            @Override
            public void changed(ObservableValue<? extends Estado> observable, Estado oldValue, Estado newValue) {
                cargarMunicipios(newValue.getIdEstado());
            }
        });
    }

    private void cargarClientes() {
        clientes = FXCollections.observableArrayList();
        List<Cliente> listaWS = ClienteDAO.obtenerClientes();
        clientes.addAll(listaWS);
        cbClientes.setItems(clientes);
    }

   private void cargarConductores() {
    conductores = FXCollections.observableArrayList(); 
    List<Colaborador> listaWS = ColaboradorDAO.obtenerColaboradores();
    List<Colaborador> conductoresFiltrados = listaWS.stream()
            .filter(colaborador -> colaborador.getIdRol() == 3) 
            .collect(Collectors.toList()); 

    conductores.addAll(conductoresFiltrados);
    cbConductores.setItems(conductores);
}



    private void cargarEstatusEnvio() {
        estadosEnvio = FXCollections.observableArrayList();
        List<EstadoEnvio> listaWS = CatalogoDAO.obtenerEstatusEnvio();
        estadosEnvio.addAll(listaWS);
        cbEstatus.setItems(estadosEnvio);
        
    }
    
    private int buscarIdEstado(Integer idEstado){
        for(int i = 0; i<estados.size(); i++){
            if(estados.get(i).getIdEstado() == idEstado){
                return i;
            }
        }
        return 0;
    }
    
    private int buscarIdMunicipio(Integer idMunicipio){
        for(int i = 0; i<municipios.size(); i++){
            if(municipios.get(i).getIdMunicipio() == idMunicipio){
                return i;
            }
        }
        return 0;
    }
    
    
    private int buscarIdColaborador(Integer idColaborador){
        for(int i = 0; i<conductores.size(); i++){
            if(conductores.get(i).getIdColaborador()== idColaborador){
                return i;
            }
        }
        return 0;
    }
    
    private int buscarIdCliente(Integer idCliente){
        for(int i = 0; i<clientes.size(); i++){
            if(clientes.get(i).getIdCliente()== idCliente){
                return i;
            }
        }
        return 0;
    }
    
    private int buscarIdEstadoEnvio(Integer idEstadoEnvio) {
        for(int i=0; i<estadosEnvio.size(); i++ ){
            if(estadosEnvio.get(i).getIdEstadoEnvio() == idEstadoEnvio){
                return i;
            }
        }
        return 0;
    }

    private void cerrarPantalla() {
        ((Stage) tfCostoEnvio.getScene().getWindow()).close();
    }
    
    
    private boolean validarCamposLlenos() {
    
    if (tfCalleDestino.getText() == null || tfCalleDestino.getText().trim().isEmpty()) {
        Utilidades.mostrarAlertaSimple("Campo vacío", "Por favor, ingresa la calle de destino.", Alert.AlertType.WARNING);
        return false;
    }
    if (tfCodigoPostal.getText() == null || tfCodigoPostal.getText().trim().isEmpty()) {
        Utilidades.mostrarAlertaSimple("Campo vacío", "Por favor, ingresa el código postal.", Alert.AlertType.WARNING);
        return false;
    }
    if (tfCostoEnvio.getText() == null || tfCostoEnvio.getText().trim().isEmpty()) {
        Utilidades.mostrarAlertaSimple("Campo vacío", "Por favor, ingresa el costo del envío.", Alert.AlertType.WARNING);
        return false;
    }
    if (tfColoniaDestino.getText() == null || tfColoniaDestino.getText().trim().isEmpty()) {
        Utilidades.mostrarAlertaSimple("Campo vacío", "Por favor, ingresa la colonia de destino.", Alert.AlertType.WARNING);
        return false;
    }
    if (tfNumeroDestino.getText() == null || tfNumeroDestino.getText().trim().isEmpty()) {
        Utilidades.mostrarAlertaSimple("Campo vacío", "Por favor, ingresa el número de destino.", Alert.AlertType.WARNING);
        return false;
    }

   
    if (cbEstado.getSelectionModel().isEmpty()) {
        Utilidades.mostrarAlertaSimple("Campo vacío", "Por favor, selecciona un estado.", Alert.AlertType.WARNING);
        return false;
    }
    if (cbMunicipio.getSelectionModel().isEmpty()) {
        Utilidades.mostrarAlertaSimple("Campo vacío", "Por favor, selecciona un municipio.", Alert.AlertType.WARNING);
        return false;
    }
    if (cbConductores.getSelectionModel().isEmpty()) {
        Utilidades.mostrarAlertaSimple("Campo vacío", "Por favor, selecciona un conductor.", Alert.AlertType.WARNING);
        return false;
    }
    if (cbClientes.getSelectionModel().isEmpty()) {
        Utilidades.mostrarAlertaSimple("Campo vacío", "Por favor, selecciona un cliente.", Alert.AlertType.WARNING);
        return false;
    }
    if (cbEstatus.getSelectionModel().isEmpty()) {
        Utilidades.mostrarAlertaSimple("Campo vacío", "Por favor, selecciona un estatus.", Alert.AlertType.WARNING);
        return false;
    }

    return true;
}

    private void guardarDatosEnvio(DatosRegistroEnvio datosRegistroEnvio) {
        Mensaje msj = EnvioDAO.registrarEnvio(datosRegistroEnvio);
        cerrarPantalla();
        observador.notificarOperacionExitosa("Registro", datosRegistroEnvio.getEnvio().getNoGuia());
        
        if(msj.isError()){
           Utilidades.mostrarAlertaSimple("Hubo un error al intenetar registrar el envio",
            msj.getContenido(), Alert.AlertType.ERROR);
        }else{
           Utilidades.mostrarAlertaSimple("Registro realizado.", msj.getContenido(), Alert.AlertType.INFORMATION);
           }
    }
    

    private void editarRegistroEnvio(DatosRegistroEnvio datosRegistroEnvio) {
        Mensaje msj = EnvioDAO.editarEnvio(datosRegistroEnvio);
        cerrarPantalla();
        observador.notificarOperacionExitosa("Edicion", datosRegistroEnvio.getEnvio().getNoGuia());
        if(msj.isError()){
           Utilidades.mostrarAlertaSimple("Hubo un error al intenetar registrar el envio",
            msj.getContenido(), Alert.AlertType.ERROR);
        }else{
           Utilidades.mostrarAlertaSimple("Registro realizado.", msj.getContenido(), Alert.AlertType.INFORMATION);
           }
    }
    
    
    
    private void configurarDatosEntrada() {
   
    // Validar tfCodigoPostal: Solo números, máximo 5 caracteres
    tfCodigoPostal.textProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue.matches("\\d*") || newValue.length() > 5) {
            tfCodigoPostal.setText(oldValue);
        }
    });
    tfCodigoPostal.setPromptText("Ingrese un código postal válido (5 dígitos)");

    // Validar tfNumeroDestino: Cualquier carácter, máximo 5 caracteres
    tfNumeroDestino.textProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue.length() > 5) {
            tfNumeroDestino.setText(oldValue);
        }
    });
    tfNumeroDestino.setPromptText("Máximo 5 caracteres");
}



    
}
