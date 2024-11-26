/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package escritoriofastpacket.vista.colaboradores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author lizet
 */
public class FXMLAdminColaboradoresController implements Initializable {

    @FXML
    private TableColumn<?, ?> tcNumPersonal;
    @FXML
    private TableColumn<?, ?> tcNombre;
    @FXML
    private TextField tfBuscar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnAgregarColaborador(ActionEvent event) {
    }

    @FXML
    private void btnEditarColaborador(ActionEvent event) {
    }

    @FXML
    private void btnEliminarColaborador(ActionEvent event) {
    }
    
}
