/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package escritoriofastpacket.validators;

import escritoriofastpacket.modelo.pojo.Colaborador;
import java.util.HashMap;
import javafx.scene.control.Label;

/**
 *
 * @author david
 */

public class ColaboradorValidator {

    public static boolean validarColaborador(Colaborador colaborador, HashMap<String, Label> hashlblErrores) {
        hashlblErrores.values().forEach(label -> {
            label.setVisible(false);
            label.setText("");
        });
        
        Boolean error = false;
        if (colaborador == null) {
            Label labelGeneral = hashlblErrores.get("general");
            if (labelGeneral != null) {
                labelGeneral.setText("El objeto colaborador no puede ser nulo.");
                labelGeneral.setVisible(true);
            }
            error = true;
        }

        if (!validarCampo(colaborador.getNombre(), "nombre", hashlblErrores)) error =  true;
        if (!validarCampo(colaborador.getApellidoPaterno(), "apellidoPaterno", hashlblErrores)) error = true;
        if (!validarCampo(colaborador.getApellidoMaterno(), "apellidoMaterno", hashlblErrores)) error = true;
        if (!validarCampo(colaborador.getNoPersonal(), "noPersonal", hashlblErrores)) error = true;
        if (!validarCorreo(colaborador.getCorreo(), hashlblErrores)) error = true;
        if (!validarCurp(colaborador.getCurp(), hashlblErrores)) error = true;
        if (!validarPassword(colaborador.getPassword(), hashlblErrores)) error = true;
        if (!validarCampo(colaborador.getRol(), "rol", hashlblErrores)) error = true;
        if (!validarNoLicencia(colaborador.getNoLicencia(), hashlblErrores)) error = true;

        return error;
    }

    private static boolean validarCampo(String valor, String campo, HashMap<String, Label> hashlblErrores) {
        if (valor == null || valor.trim().isEmpty()) {
            Label label = hashlblErrores.get(campo);
            if (label != null) {
                label.setText("El campo " + campo + " no puede estar vacío.");
                label.setVisible(true);
            }
            return false;
        }
        if (valor.length() > 50) {
            Label label = hashlblErrores.get(campo);
            if (label != null) {
                label.setText("El campo " + campo + " no puede exceder 50 caracteres.");
                label.setVisible(true);
            }
            return false;
        }
        return true;
    }

    private static boolean validarCorreo(String correo, HashMap<String, Label> hashlblErrores) {
        String correoRegex = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$";
        if (correo == null || !correo.matches(correoRegex)) {
            Label label = hashlblErrores.get("correo");
            if (label != null) {
                label.setText("El correo no tiene un formato válido.");
                label.setVisible(true);
            }
            return false;
        }
        return true;
    }

    private static boolean validarCurp(String curp, HashMap<String, Label> hashlblErrores) {
        String curpRegex = "^[A-Z]{4}\\d{6}[HM][A-Z]{5}[A-Z0-9]{2}$";
        if (curp == null || !curp.matches(curpRegex)) {
            Label label = hashlblErrores.get("curp");
            if (label != null) {
                label.setText("El CURP no tiene un formato válido.");
                label.setVisible(true);
            }
            return false;
        }
        return true;
    }

    private static boolean validarPassword(String password, HashMap<String, Label> hashlblErrores) {
        if (password == null || password.length() < 8 || password.length() > 20) {
            Label label = hashlblErrores.get("password");
            if (label != null) {
                label.setText("La contraseña debe tener entre 8 y 20 caracteres.");
                label.setVisible(true);
            }
            return false;
        }
        return true;
    }
    
    private static boolean validarNoLicencia(String noLicencia, HashMap<String, Label> hashlblErrores) {
        if (noLicencia != null && !noLicencia.matches("\\w{5,20}")) {
            Label label = hashlblErrores.get("licencia");
            if (label != null) {
                label.setText("El número de licencia debe tener entre 5 y 20 caracteres alfanuméricos.");
                label.setVisible(true);
            }
            return false;
        }
        return true;
    }
}
