/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package escritoriofastpacket.vista.unidades;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;

/**
 * FXML Controller class
 *
 * @author lizet
 */
public class FXMLAdminUnidadesController implements Initializable {

    @FXML
    private TableColumn<?, ?> col_vin;
    @FXML
    private TableColumn<?, ?> col_marca;
    @FXML
    private TableColumn<?, ?> col_modelo;
    @FXML
    private TableColumn<?, ?> col_tipo;
    @FXML
    private TableColumn<?, ?> col_anio;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnAgregarUnidad(ActionEvent event) {
    }

    @FXML
    private void btnEliminarUnidad(ActionEvent event) {
    }

    @FXML
    private void btnQuitarConductor(ActionEvent event) {
    }

    @FXML
    private void btnEditarUnidad(ActionEvent event) {
    }

    @FXML
    private void btnAsignarConductor(ActionEvent event) {
    }
    
}
