
package escritoriofastpacket.vista.envios;

import escritoriofastpacket.interfaz.INotificarOperacion;
import escritoriofastpacket.modelo.dao.CatalogoDAO;
import escritoriofastpacket.modelo.dao.ClienteDAO;
import escritoriofastpacket.modelo.dao.ColaboradorDAO;
import escritoriofastpacket.modelo.dao.EnvioDAO;
import escritoriofastpacket.modelo.dao.HistorialEnvioDAO;
import escritoriofastpacket.modelo.pojo.Cliente;
import escritoriofastpacket.modelo.pojo.Colaborador;
import escritoriofastpacket.modelo.pojo.DatosRegistroEnvio;
import escritoriofastpacket.modelo.pojo.Direccion;
import escritoriofastpacket.modelo.pojo.Envio;
import escritoriofastpacket.modelo.pojo.Estado;
import escritoriofastpacket.modelo.pojo.EstadoEnvio;
import escritoriofastpacket.modelo.pojo.HistorialEnvio;
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
import javafx.scene.control.Label;
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
    private Integer idColaborador;
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
    @FXML
    private Label lbErrorCliente;
    @FXML
    private Label lbErrorConductor;
    @FXML
    private Label lbErrorCalle;
    @FXML
    private Label lbErrorColonia;
    @FXML
    private Label lbErrorEstado;
    @FXML
    private Label lbErrorMunicipio;
    @FXML
    private Label lbErrorCosto;
    @FXML
    private Label lbErrorEstatus;
    @FXML
    private Label lbErrorNumero;
    @FXML
    private Label lbErrorCodigoPostal;

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
              HistorialEnvio historial = new HistorialEnvio();
              datosRegistroEnvio.getEnvio().setIdEnvio(envioEdicion.getIdEnvio());
              Integer idEstadoEnvio = (cbEstatus.getSelectionModel().getSelectedItem() != null)
                        ? cbEstatus.getSelectionModel().getSelectedItem().getIdEstadoEnvio()
                        :0;
             datosRegistroEnvio.getEnvio().setIdEstadoEnvio(idEstadoEnvio);

              editarRegistroEnvio(datosRegistroEnvio);
              historial.setIdColaborador(this.idColaborador);
              //Pasar el id del colaborador  logeado
              historial.setIdEnvio(datosRegistroEnvio.getEnvio().getIdEnvio());
              historial.setIdEstadoEnvio(idEstadoEnvio);
              guardarCambioHistorial(historial);


            }
        }
    }
    
 
    
    @FXML
    private void btnCancelar(ActionEvent event) {
        cerrarPantalla();
    }

    void inicializarValores(INotificarOperacion observador, Envio envioEdicion, Integer idColaborador) {
        this.observador = observador;
        this.envioEdicion = envioEdicion;
        this.idColaborador = idColaborador;
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
      
      if(idColaborador != null){
      int posicionConductor = buscarIdColaborador(idColaborador);
      cbConductores.getSelectionModel().select(posicionConductor);
      }else{
      lbErrorConductor.setText("Por el momento, no hay un conductor asignado a este envío");
      }  
      
      
        
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
    boolean camposValidos = true;

    if (tfCalleDestino.getText() == null || tfCalleDestino.getText().trim().isEmpty()) {
        lbErrorCalle.setText("Ingrese la calle");
        camposValidos = false;
    } else {
        lbErrorCalle.setText("");
    }

    if (tfCodigoPostal.getText() == null || tfCodigoPostal.getText().trim().isEmpty()) {
        lbErrorCodigoPostal.setText("Ingrese el código postal");
        camposValidos = false;
    } else {
        lbErrorCodigoPostal.setText("");
    }

    if (tfCostoEnvio.getText() == null || tfCostoEnvio.getText().trim().isEmpty()) {
        lbErrorCosto.setText("Ingrese el costo del envío");
        camposValidos = false;
    } else {
        lbErrorCosto.setText("");
    }

    // Validación de Colonia Destino
    if (tfColoniaDestino.getText() == null || tfColoniaDestino.getText().trim().isEmpty()) {
        lbErrorColonia.setText("Ingrese la colonia de destino");
        camposValidos = false;
    } else {
        lbErrorColonia.setText("");
    }

    // Validación de Número de Destino
    if (tfNumeroDestino.getText() == null || tfNumeroDestino.getText().trim().isEmpty()) {
        lbErrorNumero.setText("Ingrese el número de destino");
        camposValidos = false;
    } else {
        lbErrorNumero.setText("");
    }

    // Validación de Estado
    if (cbEstado.getSelectionModel().isEmpty()) {
        lbErrorEstado.setText("Seleccione un estado");
        camposValidos = false;
    } else {
        lbErrorEstado.setText("");
    }

    // Validación de Municipio
    if (cbMunicipio.getSelectionModel().isEmpty()) {
        lbErrorMunicipio.setText("Seleccione un municipio");
        camposValidos = false;
    } else {
        lbErrorMunicipio.setText("");
    }

   
    // Validación de Cliente
    if (cbClientes.getSelectionModel().isEmpty()) {
        lbErrorCliente.setText("Seleccione un cliente");
        camposValidos = false;
    } else {
        lbErrorCliente.setText("");
    }

    
    if (modoEdicion && cbEstatus.getSelectionModel().isEmpty()) {
        lbErrorEstatus.setText("Seleccione un estatus");
        camposValidos = false;
    } else {
        lbErrorEstatus.setText("");
    }

    return camposValidos;
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
    
  // Validar tfCostoEnvio: Solo números y un punto decimal, máximo 10 caracteres
tfCostoEnvio.textProperty().addListener((observable, oldValue, newValue) -> {
    // Expresión regular para validar números decimales con un punto y hasta 10 caracteres
    if (!newValue.matches("\\d{0,9}(\\.\\d{0,1})?") || newValue.length() > 10) {
        tfCostoEnvio.setText(oldValue);
    }
});




    
    
}

    private void guardarCambioHistorial(HistorialEnvio historial) {
        Mensaje msj = HistorialEnvioDAO.registrarCambioEstatus(historial);
        if(msj.isError()){
            System.out.println(msj.getContenido());
        }else{
            System.out.println("Historial guardado" + ": " +msj.getContenido());
        }
    }



    
}
