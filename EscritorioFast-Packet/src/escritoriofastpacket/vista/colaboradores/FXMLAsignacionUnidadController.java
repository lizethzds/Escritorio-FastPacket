/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package escritoriofastpacket.vista.colaboradores;

import escritoriofastpacket.modelo.pojo.Colaborador;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author david
 */
public class FXMLAsignacionUnidadController implements Initializable {
    
    private Colaborador colaborador;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void inicializarValores(Colaborador colaborador){
        this.colaborador = colaborador;
    }
    
}
