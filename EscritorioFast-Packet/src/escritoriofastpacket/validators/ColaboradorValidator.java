/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package escritoriofastpacket.validators;

import escritoriofastpacket.modelo.dao.ColaboradorDAO;
import escritoriofastpacket.modelo.pojo.Colaborador;
import escritoriofastpacket.modelo.pojo.Mensaje;
import java.util.HashMap;
import java.util.Objects;
import javafx.scene.control.Label;

/**
 *
 * @author david
 */

public class ColaboradorValidator {

    public static Mensaje validarColaborador(Colaborador colaborador,Colaborador ColaboradorEdicion, HashMap<String, Label> hashlblErrores) {
        hashlblErrores.values().forEach(label -> {
            label.setVisible(false);
            label.setText("");
        });
        
        Mensaje respuesta = comprobarFormatos(colaborador, hashlblErrores);
        if(respuesta.isError()) return respuesta;   
        respuesta = comrpobarDuplicidad(colaborador, ColaboradorEdicion, hashlblErrores);
        return respuesta;
    }

    private static boolean validarCampo(String valor, String campo, HashMap<String, Label> hashlblErrores) {
        if (valor == null || valor.trim().isEmpty()) {
            Label label = hashlblErrores.get(campo);
            if (label != null) {
                label.setText("*Campo obligatorio.");
                label.setVisible(true);
            }
            return false;
        }
        if (valor.length() > 50) {
            Label label = hashlblErrores.get(campo);
            if (label != null) {
                label.setText("*El campo " + campo + " no puede exceder 50 caracteres.");
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
                label.setText("*El correo no tiene un formato válido.");
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
                label.setText("*El CURP no tiene un formato válido.");
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
                label.setText("*La contraseña debe tener entre 8 y 20 caracteres.");
                label.setVisible(true);
            }
            return false;
        }
        return true;
    }
    
    private static boolean validarNoLicencia(String noLicencia, HashMap<String, Label> hashlblErrores) {
        if (noLicencia != null && !noLicencia.matches("\\w{4,20}")) {
            Label label = hashlblErrores.get("licencia");
            if (label != null) {
                label.setText("*El número de licencia debe tener entre 5 y 20 caracteres alfanuméricos.");
                label.setVisible(true);
            }
            return false;
        }
        return true;
    }
    private static boolean validarIdRol(Integer idRol, HashMap<String,Label> hashlblErrores){
        if(idRol == null || idRol == 0){
            Label label = hashlblErrores.get("rol");
            if (label != null){
                label.setText("*Campo obligatorio.");
                label.setVisible(true);
            }
            return false;
        }
        return true;
    }
    
       private static void comprobarLabelEnMensaje(Mensaje mensaje, HashMap<String, Label> hashlblErrores){
        if(mensaje.getContenido().contains("correo")) {
            hashlblErrores.get("correo").setText("*Correo ya registrado");
            hashlblErrores.get("correo").setVisible(true);
        }
        if(mensaje.getContenido().contains("CURP")) {
            hashlblErrores.get("curp").setText("*CURP  ya registrada");
            hashlblErrores.get("curp").setVisible(true);
        }
        if(mensaje.getContenido().contains("número de personal")) {
            hashlblErrores.get("noPersonal").setText("*Número de personal ya registrado");
            hashlblErrores.get("noPersonal").setVisible(true);
        }
        if(mensaje.getContenido().contains("número de licencia")) {
            hashlblErrores.get("licencia").setText("*Número de licencia ya registrado");
            hashlblErrores.get("licencia").setVisible(true);
        }
    }
    
       private static Mensaje comprobarFormatos(Colaborador colaborador,HashMap<String, Label> hashlblErrores ){
           Mensaje respuesta = new Mensaje();
        if (colaborador == null) {
            Label labelGeneral = hashlblErrores.get("general");
            if (labelGeneral != null) {
                labelGeneral.setText("El objeto colaborador no puede ser nulo.");
                labelGeneral.setVisible(true);
            }
            respuesta.setError(true);
        }
        
        if (!validarCampo(colaborador.getNombre(), "nombre", hashlblErrores)) respuesta.setError(true);
        if (!validarCampo(colaborador.getApellidoPaterno(), "apellidoPaterno", hashlblErrores)) respuesta.setError(true);
        if (!validarCampo(colaborador.getApellidoMaterno(), "apellidoMaterno", hashlblErrores)) respuesta.setError(true);
        if (!validarCampo(colaborador.getNoPersonal(), "noPersonal", hashlblErrores)) respuesta.setError(true);
        if (!validarCorreo(colaborador.getCorreo(), hashlblErrores)) respuesta.setError(true);
        if (!validarCurp(colaborador.getCurp(), hashlblErrores)) respuesta.setError(true);
        if (!validarPassword(colaborador.getPassword(), hashlblErrores)) respuesta.setError(true);
        if (!validarIdRol(colaborador.getIdRol(), hashlblErrores)) respuesta.setError(true);
        if (!validarNoLicencia(colaborador.getNoLicencia(), hashlblErrores) && "Conductor".equals(colaborador.getRol())) respuesta.setError(true);
        
        if(respuesta.isError()){
            respuesta.setContenido("Uno o varios de los campos no son valídos");
            respuesta.setError(true);
        }else{
            respuesta.setContenido("NO hay errores en los formatos");
            respuesta.setError(false);
        }
        return respuesta;
    }
       
    public static Mensaje comrpobarDuplicidad(Colaborador colaborador,Colaborador colaboradorEdicion,HashMap<String, Label> hashlblErrores){
        Mensaje respuesta;
        if(colaboradorEdicion != null){
            Colaborador colaboradorMensaje = new Colaborador();
            String correo = Objects.equals(colaborador.getCorreo(), colaboradorEdicion.getCorreo()) ? null : colaborador.getCorreo();
            String curp = Objects.equals(colaborador.getCurp(), colaboradorEdicion.getCurp()) ? null : colaborador.getCurp();
            String noLicencia = null;
            colaboradorMensaje.setCorreo(correo);
            colaboradorMensaje.setCurp(curp);
            if ("Conductor".equals(colaborador.getRol())) {
                noLicencia= Objects.equals(colaborador.getNoLicencia(), colaboradorEdicion.getNoLicencia()) ? null : colaborador.getNoLicencia();
            }
            colaboradorMensaje.setNoLicencia(noLicencia);
            colaboradorMensaje.setRol(colaborador.getRol());   
            respuesta = ColaboradorDAO.comprobarValoresRepetidos(colaboradorMensaje);
        }else{
            respuesta = ColaboradorDAO.comprobarValoresRepetidos(colaborador);
        }

        if(respuesta.isError()){
              comprobarLabelEnMensaje(respuesta,hashlblErrores);
              respuesta.setContenido(respuesta.getContenido());
              respuesta.setError(true);
        }else{
            respuesta.setContenido("Sin errores en el formularío");
            respuesta.setError(false);
        }
        return respuesta;
    }
}
