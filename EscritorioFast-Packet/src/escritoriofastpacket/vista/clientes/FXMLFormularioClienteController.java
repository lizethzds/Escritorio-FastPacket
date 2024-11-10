
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarEstados();
        configuracionSeleccionEstado();
        
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
        // Verificar que los campos no estén vacíos
    if (camposLlenos()) {
        // Crear el objeto DatosRegistroCliente
        DatosRegistroCliente datosRegistroCliente = new DatosRegistroCliente();
        
        // Crear el objeto Cliente y asignar los valores de los campos
        Cliente cliente = new Cliente();
        cliente.setNombre(tfNombre.getText());
        cliente.setApellidoPaterno(tfApellidoPat.getText());
        cliente.setApellidoMaterno(tfApellidoMat.getText());
        cliente.setCorreo(tfCorreo.getText());
        cliente.setTelefono(tfTelefono.getText());
        
        // Crear el objeto Direccion y asignar los valores de los campos
        Direccion direccion = new Direccion();
        direccion.setCalle(tfCalle.getText());
        direccion.setNumero(tfNumero.getText()); 
        direccion.setColonia(tfColonia.getText());
        direccion.setCodigoPostal(tfCodigoPostal.getText());

        // Obtener el municipio seleccionado y asignarlo a la dirección
        Integer idMunicipio = (cbMunicipio.getSelectionModel().getSelectedItem() != null) 
                    ? cbMunicipio.getSelectionModel().getSelectedItem().getIdMunicipio() 
                    : 0;
        direccion.setIdMunicipio(idMunicipio);

        // Asignar la dirección y cliente  al objeto DatosRegistroCliente
        datosRegistroCliente.setCliente(cliente);
        datosRegistroCliente.setDireccion(direccion);

        // Guardar el cliente y la dirección en la base de datos
        
        if(modoEdicion == false){
        guardarDatosCliente(datosRegistroCliente);
        }else{
            editarRegistroCliente();
        }
        

    } else {
        Utilidades.mostrarAlertaSimple("Campos incompletos", "Por favor, complete todos los campos.", Alert.AlertType.WARNING);
    }
       
    }

    @FXML
    private void btnCancelar(ActionEvent event) {
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
       
            // Obtener idEstado a partir del idMunicipio
        Integer idMunicipio = clienteDatos.getDireccion().getIdMunicipio();
        Estado estadoDireccion;
        estadoDireccion = CatalogoDAO.obtenerEstadoMunicipio(idMunicipio);
        Integer idEstado = estadoDireccion.getIdEstado();

        // Seleccionar el estado en el comboBox
        int posicionEstado = buscarIdEstado(idEstado);
        cbEstado.getSelectionModel().select(posicionEstado);

        // Cargar municipios del estado seleccionado y seleccionar el municipio correspondiente
        cargarMunicipios(idEstado);
        int posicionMunicipio = buscarIdMunicipio(idMunicipio);
        cbMunicipio.getSelectionModel().select(posicionMunicipio);


        tfCorreo.setEditable(false);

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


    private void guardarDatosCliente(DatosRegistroCliente datosRegistroCliente) {
        
           Mensaje msj = ClienteDAO.registrarCliente(datosRegistroCliente);
           cerrarPantalla();
           observador.notificarOperacionExitosa("Registro", datosRegistroCliente.getCliente().getNombre());
           
           if(msj.isError()){
               Utilidades.mostrarAlertaSimple("Hubo un error al intenetar registrar el cliente", msj.getContenido(), Alert.AlertType.ERROR);
           }else{
           Utilidades.mostrarAlertaSimple("Registro realizado.", msj.getContenido(), Alert.AlertType.INFORMATION);
           }
        
        
    }
    
     private void editarRegistroCliente(){
        
    }


    private void cerrarPantalla() {
       ((Stage) tfNombre.getScene().getWindow()).close();
    }
    
    
    
   
     
     
    

 

   
    
}
