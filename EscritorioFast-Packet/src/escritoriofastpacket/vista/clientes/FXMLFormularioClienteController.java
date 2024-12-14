 
package escritoriofastpacket.vista.clientes;

import escritoriofastpacket.interfaz.INotificarOperacion;
import escritoriofastpacket.modelo.dao.CatalogoDAO;
import escritoriofastpacket.modelo.dao.ClienteDAO;
import escritoriofastpacket.modelo.pojo.Cliente;
import escritoriofastpacket.modelo.pojo.DatosRegistroCliente;
import escritoriofastpacket.modelo.pojo.Direccion;
import escritoriofastpacket.modelo.pojo.Estado;
import escritoriofastpacket.modelo.pojo.Mensaje;
import escritoriofastpacket.modelo.pojo.Municipio;
import escritoriofastpacket.utils.Utilidades;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
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
public class FXMLFormularioClienteController implements Initializable {
    
    private INotificarOperacion observador;
    private Cliente clienteEdicion;
    private DatosRegistroCliente clienteDatos;
    private boolean modoEdicion = false;
    
    private ObservableList<Estado> estados;
    private ObservableList<Municipio> municipios;

    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfApellidoPat;
    @FXML
    private TextField tfCorreo;
    @FXML
    private TextField tfApellidoMat;
    @FXML
    private TextField tfTelefono;
    @FXML
    private TextField tfCalle;
    @FXML
    private TextField tfNumero;
    @FXML
    private TextField tfColonia;
    @FXML
    private ComboBox<Municipio> cbMunicipio;
    @FXML
    private ComboBox<Estado> cbEstado;
    @FXML
    private TextField tfCodigoPostal;
    @FXML
    private Label lbErrorNombre;
    @FXML
    private Label lbErrorApellidos;
    @FXML
    private Label lbErrorCorreo;
    @FXML
    private Label lbErrorTelefono;
    @FXML
    private Label lbErrorCalle;
    @FXML
    private Label lbErrorNum;
    @FXML
    private Label lbErrorColonia;
    @FXML
    private Label lbErrorEstado;
    @FXML
    private Label lbErrorMunicipio;
    @FXML
    private Label lbErrorCP;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarEstados();
        configuracionSeleccionEstado();
        configurarDatosEntrada();
        
    }    
    
    
    public void inicializarValores(INotificarOperacion observador, Cliente clienteEdicion){
        this.observador = observador;
        this.clienteEdicion = clienteEdicion;
        if(clienteEdicion != null){
            modoEdicion = true;
            cargarDatosCliente(clienteEdicion.getIdCliente());
          
        }
                
    }

    @FXML
    private void btnGuardar(ActionEvent event) {
   
    if (validarCamposLlenos()) {
    
        DatosRegistroCliente datosRegistroCliente = new DatosRegistroCliente();
        
        Cliente cliente = new Cliente();
        cliente.setNombre(tfNombre.getText());
        cliente.setApellidoPaterno(tfApellidoPat.getText());
        cliente.setApellidoMaterno(tfApellidoMat.getText());
        cliente.setCorreo(tfCorreo.getText());
        cliente.setTelefono(tfTelefono.getText());
        
        Direccion direccion = new Direccion();
        direccion.setCalle(tfCalle.getText());
        direccion.setNumero(tfNumero.getText()); 
        direccion.setColonia(tfColonia.getText());
        direccion.setCodigoPostal(tfCodigoPostal.getText());

       
        Integer idMunicipio = (cbMunicipio.getSelectionModel().getSelectedItem() != null) 
                    ? cbMunicipio.getSelectionModel().getSelectedItem().getIdMunicipio() 
                    : 0;
        direccion.setIdMunicipio(idMunicipio);

      
        datosRegistroCliente.setCliente(cliente);
        datosRegistroCliente.setDireccion(direccion);
  
        if(modoEdicion == false){
        guardarDatosCliente(datosRegistroCliente);
        }else{
            datosRegistroCliente.getCliente().setIdCliente(clienteEdicion.getIdCliente());
            
            editarRegistroCliente(datosRegistroCliente);
        }
        

    } else {
        Utilidades.mostrarAlertaSimple("Campos incompletos", "Por favor, complete los datos requeridos.", Alert.AlertType.WARNING);
    }
       
    }

    @FXML
    private void btnCancelar(ActionEvent event) {
        cerrarPantalla();
    }

    private void cargarDatosCliente(Integer idCliente) {
        
       clienteDatos = ClienteDAO.obtenerClientePorID(idCliente);
       tfNombre.setText(clienteDatos.getCliente().getNombre());
       tfApellidoPat.setText(clienteDatos.getCliente().getApellidoPaterno());
       tfApellidoMat.setText(clienteDatos.getCliente().getApellidoMaterno());
       tfCorreo.setText(clienteDatos.getCliente().getCorreo());
       tfTelefono.setText(clienteDatos.getCliente().getTelefono());
       tfCalle.setText(clienteDatos.getDireccion().getCalle());
       tfNumero.setText(clienteDatos.getDireccion().getNumero());
       tfColonia.setText(clienteDatos.getDireccion().getColonia());
       tfCodigoPostal.setText(clienteDatos.getDireccion().getCodigoPostal());
       

        Integer idMunicipio = clienteDatos.getDireccion().getIdMunicipio();
        Estado estadoDireccion;
        estadoDireccion = CatalogoDAO.obtenerEstadoMunicipio(idMunicipio);
        Integer idEstado = estadoDireccion.getIdEstado();

        int posicionEstado = buscarIdEstado(idEstado);
        cbEstado.getSelectionModel().select(posicionEstado);

        cargarMunicipios(idEstado);
        int posicionMunicipio = buscarIdMunicipio(idMunicipio);
        cbMunicipio.getSelectionModel().select(posicionMunicipio);
        

    }
    
   
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
    
    
   
    private boolean camposLlenos() {
    return (tfNombre.getText() != null && !tfNombre.getText().isEmpty()) &&
           (tfApellidoPat.getText() != null && !tfApellidoPat.getText().isEmpty()) &&
           (tfApellidoMat.getText() != null && !tfApellidoMat.getText().isEmpty()) &&
           (tfCorreo.getText() != null && !tfCorreo.getText().isEmpty()) &&
           (tfTelefono.getText() != null && !tfTelefono.getText().isEmpty()) &&
           (tfCalle.getText() != null && !tfCalle.getText().isEmpty()) &&
           (tfNumero.getText() != null && !tfNumero.getText().isEmpty()) &&
           (tfColonia.getText() != null && !tfColonia.getText().isEmpty()) &&
           (tfCodigoPostal.getText() != null && !tfCodigoPostal.getText().isEmpty()) &&
           cbMunicipio.getValue() != null;
}
    
    
    private boolean validarCamposLlenos(){
        boolean camposValidos = true;
        
        if(tfNombre.getText() == null || tfNombre.getText().trim().isEmpty()){
            lbErrorNombre.setText("Ingresa el nombre");
            camposValidos = false;
        }
        
        if(tfApellidoPat.getText() == null || tfApellidoPat.getText().trim().isEmpty() ||
                tfApellidoMat.getText() == null || tfApellidoMat.getText().trim().isEmpty()){
            lbErrorApellidos.setText("Ingresa los apellidos");
            camposValidos = false;
        }
        if(tfCorreo.getText() == null || tfCorreo.getText().trim().isEmpty()){
            lbErrorCorreo.setText("Ingresa el correo electrónico");
            camposValidos = false;
        }
        if(tfTelefono.getText() == null || tfTelefono.getText().trim().isEmpty()){
            lbErrorTelefono.setText("Ingresa el numero telefónico");
            camposValidos = false;
        }
        if(tfCalle.getText() == null || tfCalle.getText().trim().isEmpty()){
            lbErrorCalle.setText("Ingresa la calle");
            camposValidos = false;
        }
        
        if(tfNumero.getText() == null || tfNumero.getText().trim().isEmpty()){
            lbErrorNum.setText("Ingresa el número");
            camposValidos = false;
        }
        
        if(tfColonia.getText() == null || tfColonia.getText().trim().isEmpty()){
            lbErrorColonia.setText("Ingresa la colonia");
            camposValidos = false;
        }
        
        if(tfCodigoPostal.getText() == null || tfCodigoPostal.getText().trim().isEmpty()){
            lbErrorCP.setText("Ingresa el C.P");
            camposValidos = false;
        }
        
        if (cbEstado.getSelectionModel().isEmpty()) {
            lbErrorEstado.setText("Seleccione un estado");
            camposValidos = false;
        } else {
            lbErrorEstado.setText("");
        }

        if (cbMunicipio.getSelectionModel().isEmpty()) {
            lbErrorMunicipio.setText("Seleccione un municipio");
            camposValidos = false;
        } else {
            lbErrorMunicipio.setText("");
        }
     
        return camposValidos;
        
    }


    private void guardarDatosCliente(DatosRegistroCliente datosRegistroCliente) {
        
           Mensaje msj = ClienteDAO.registrarCliente(datosRegistroCliente);
           observador.notificarOperacionExitosa("Registro", datosRegistroCliente.getCliente().getNombre());
           
           if(msj.isError()){
               Utilidades.mostrarAlertaSimple("Hubo un error al intentar registrar el cliente", msj.getContenido(), Alert.AlertType.ERROR);
           }else{
           Utilidades.mostrarAlertaSimple("Registro realizado.", msj.getContenido(), Alert.AlertType.INFORMATION);
           cerrarPantalla();
           }
        
        
    }
    
     private void editarRegistroCliente(DatosRegistroCliente datosEdicionCliente){
         Mensaje msj = ClienteDAO.editarCliente(datosEdicionCliente);
        
         observador.notificarOperacionExitosa("Edicion", datosEdicionCliente.getCliente().getNombre());
         if(msj.isError()){
               Utilidades.mostrarAlertaSimple("Hubo un error al intentar actualizar el cliente", msj.getContenido(), Alert.AlertType.ERROR);
           }else{
           Utilidades.mostrarAlertaSimple("Actualización realizada.", msj.getContenido(), Alert.AlertType.INFORMATION);
            cerrarPantalla();
           }
        
    }


    private void cerrarPantalla() {
       ((Stage) tfNombre.getScene().getWindow()).close();
    }

    
    
    private void configurarDatosEntrada() {
        tfTelefono.textProperty().addListener((observable, oldValue, newValue) -> {
    if (!newValue.matches("\\d*") || newValue.length() > 10) {
        tfTelefono.setText(oldValue);
    }
        });
        
        tfTelefono.setPromptText("Ingrese un número de 10 digitos");
        
        tfCodigoPostal.textProperty().addListener((observable, oldValue, newValue) -> {
    if (!newValue.matches("\\d*") || newValue.length() > 5) {
        tfCodigoPostal.setText(oldValue);
    }   
        });
        
        
       tfNumero.textProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.length() > 7) {
            tfNumero.setText(oldValue);
      }
        });
          tfNumero.setPromptText("Máximo 7 caracteres");

    }
    
    
}
