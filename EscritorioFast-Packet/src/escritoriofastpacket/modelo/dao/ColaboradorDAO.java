/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package escritoriofastpacket.modelo.dao;
import escritoriofastpacket.modelo.ConexionWS;
import escritoriofastpacket.modelo.pojo.Colaborador;
import escritoriofastpacket.modelo.pojo.Mensaje;
import escritoriofastpacket.modelo.pojo.RespuestaHTTP;
import escritoriofastpacket.modelo.pojo.Rol;
import escritoriofastpacket.utils.Constantes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import escritoriofastpacket.modelo.pojo.RegistroColaboradorUnidad;
import escritoriofastpacket.modelo.pojo.Unidad;
import java.lang.reflect.Type;
import java.net.HttpURLConnection; 
import java.util.List; 

public class ColaboradorDAO {
    public static List<Colaborador> obtenerColaboradores(){
        List<Colaborador> lista = null;
        String url = Constantes.URL_WS+"/colaboradores/obtenerColaboradores";
        RespuestaHTTP respuestaWS = ConexionWS.peticionGET(url);
        try {
             if (respuestaWS.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) { 
                Gson gson = new Gson();
                Type tipoLista = new TypeToken<List<Colaborador>>(){}.getType();
                lista = gson.fromJson(respuestaWS.getContenido(), tipoLista);
             }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
    public static List<Rol> obtentenerRolesColaborador(){
        List<Rol> roles = null;
        String url = Constantes.URL_WS+"/catalogo/obtenerRoles";
        RespuestaHTTP respuestaWS = ConexionWS.peticionGET(url);
        try {
             if (respuestaWS.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) { 
                Gson gson = new Gson();
                Type tipoLista = new TypeToken<List<Rol>>(){}.getType();
                roles = gson.fromJson(respuestaWS.getContenido(), tipoLista);
             }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return roles;
    }
    
    public static Mensaje registrarColaborador(Colaborador colaborador){
        Mensaje respuesta = new Mensaje();
            respuesta.setError(true);
        String url = Constantes.URL_WS+"/colaboradores/registrar";
        try {
            Gson gson = new Gson();
            String parametros = gson.toJson(colaborador);
            RespuestaHTTP respuestaWS = ConexionWS.peticionPOSTJson(url,parametros);
            if(respuestaWS.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
                respuesta = gson.fromJson(respuestaWS.getContenido(), Mensaje.class);
            }else{
                respuesta.setContenido(respuestaWS.getContenido());
            }
        } catch (Exception e) {
            respuesta.setContenido(e.getMessage());
        }
        return respuesta;
    }
    
    public static Mensaje modificar(Colaborador colaborador){
        Mensaje respuesta = new Mensaje();
        respuesta.setError(true);
        String url = Constantes.URL_WS+"/colaboradores/modificar";
        try {
            Gson gson = new Gson();
            String parametros = gson.toJson(colaborador);
            RespuestaHTTP respuestaWS = ConexionWS.peticionPUTJson(url,parametros);
            if(respuestaWS.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
                respuesta = gson.fromJson(respuestaWS.getContenido(), Mensaje.class);
            }else{
                respuesta.setContenido(respuestaWS.getContenido());
            }
        } catch (Exception e) {
            respuesta.setContenido(e.getMessage());
        }
        return respuesta;
    }
    
    public static Mensaje eliminar(int idColaborador){
        Mensaje respuesta = new Mensaje();
        respuesta.setError(true);
        String url = Constantes.URL_WS+"/colaboradores/eliminar";
        try {
            RespuestaHTTP respuestaWS = ConexionWS.peticionDELETEUrl(url+"/"+idColaborador);
            if(respuestaWS.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
                   Gson gson = new Gson();
                   respuesta = gson.fromJson(respuestaWS.getContenido(), Mensaje.class);
            }else{
                respuesta.setContenido(respuestaWS.getContenido());
            }
        } catch (Exception e) {
            respuesta.setContenido(e.getMessage());
        }
        return respuesta;
    }
    
    public static Mensaje obtenerImagen(Integer IdColaborador){
        Mensaje respuesta = new Mensaje();
        respuesta.setError(true);
        String url = Constantes.URL_WS+"/colaboradores/obtenerFotografia/"+IdColaborador;
        try {
            Gson gson = new Gson();
            RespuestaHTTP respuestaWS = ConexionWS.peticionGET(url);
            if(respuestaWS.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
                respuesta = gson.fromJson(respuestaWS.getContenido(), Mensaje.class);
            }else{
                respuesta.setContenido(respuestaWS.getContenido());
            }
        } catch (Exception e) {
            respuesta.setContenido(e.getMessage());
        }
        return respuesta;
    }
    
    public static Mensaje comprobarValoresRepetidos(Colaborador colaborador){
        Mensaje respuesta = new Mensaje();
            respuesta.setError(true);
        String url = Constantes.URL_WS+"/colaboradores/comprobarValoresRepetidos";
        try {
            Gson gson = new Gson();
            String parametros = gson.toJson(colaborador);
            RespuestaHTTP respuestaWS = ConexionWS.peticionPOSTJson(url,parametros);
            if(respuestaWS.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
                respuesta = gson.fromJson(respuestaWS.getContenido(), Mensaje.class);
            }else{
                respuesta.setContenido(respuestaWS.getContenido());
            }
        } catch (Exception e) {
            respuesta.setContenido(e.getMessage());
        }
        return respuesta;
    }
    
    public static Mensaje comprobarEnvios(Integer idColaborador){
        Mensaje respuesta = new Mensaje();
            respuesta.setError(true);
        String url = Constantes.URL_WS+"/colaboradores/comprobarEnvios/"+idColaborador;
        try {
            Gson gson = new Gson();
            RespuestaHTTP respuestaWS = ConexionWS.peticionGET(url);
            if(respuestaWS.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
                respuesta = gson.fromJson(respuestaWS.getContenido(), Mensaje.class);
            }else{
                respuesta.setContenido(respuestaWS.getContenido());
            }
        } catch (Exception e) {
            respuesta.setContenido(e.getMessage());
        }
        return respuesta;
    }
    
    public static Mensaje asignarUnidad(Integer idColaborador, Integer idUnidad){
        Mensaje respuesta = new Mensaje();
        respuesta.setError(true);
        String url = Constantes.URL_WS+"/colaboradores/asignarUnidad/"+idColaborador+"/"+idUnidad;
        try {
            Gson gson = new Gson();
            RespuestaHTTP respuestaWS = ConexionWS.peticionPOST(url,"");
            if(respuestaWS.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
                respuesta = gson.fromJson(respuestaWS.getContenido(), Mensaje.class);
            }else{
                respuesta.setContenido(respuestaWS.getContenido());
            }
        } catch (Exception e) {
            respuesta.setContenido(e.getMessage());
        }
        return respuesta;
    }
    
    public static Mensaje comprobarColaboradorUnidad(Integer idColaborador, Integer idUnidad){
        Mensaje respuesta = new Mensaje();
        respuesta.setError(true);
        String url = Constantes.URL_WS+"/colaboradores/comprobarColaboradorUnidad/"+idColaborador+"/"+idUnidad;
        try {
            Gson gson = new Gson();
            RespuestaHTTP respuestaWS = ConexionWS.peticionGET(url);
            if(respuestaWS.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
                respuesta = gson.fromJson(respuestaWS.getContenido(), Mensaje.class);
            }else{
                respuesta.setContenido(respuestaWS.getContenido());
            }
        } catch (Exception e) {
            respuesta.setContenido(e.getMessage());
        }
        return respuesta;
    }
    
    public static Mensaje quitarAsignacionUnidad(Integer idColaborador, Integer idUnidad){
        Mensaje respuesta = new Mensaje();
        respuesta.setError(true);
        String url = Constantes.URL_WS+"/colaboradores/quitarAsignacionUnidad/"+idColaborador+"/"+idUnidad;
        try {
            Gson gson = new Gson();
            RespuestaHTTP respuestaWS = ConexionWS.peticionDELETEJson(url,"");
            if(respuestaWS.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
                respuesta = gson.fromJson(respuestaWS.getContenido(), Mensaje.class);
            }else{
                respuesta.setContenido(respuestaWS.getContenido());
            }
        } catch (Exception e) {
            respuesta.setContenido(e.getMessage());
        }
        return respuesta;
    }
    
    public static Unidad obtenerUnidad(Integer idColaborador){
        Unidad unidad = null;
        String url = Constantes.URL_WS+"/colaboradores/obtenerUnidad/"+idColaborador;
        try {
            Gson gson = new Gson();
            RespuestaHTTP respuestaWS = ConexionWS.peticionGET(url);
            if(respuestaWS.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
                unidad = gson.fromJson(respuestaWS.getContenido(), Unidad.class);
            }
            System.out.println("respuesta: " + respuestaWS.getContenido());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return unidad;
    }
    
    public static List<RegistroColaboradorUnidad> obtenerRegistrosColaboradorUnidad(){
        List<RegistroColaboradorUnidad> registros = null;
        String url =Constantes.URL_WS+"/colaboradores/obtenerRegistrosColaboradorUnidad";
        RespuestaHTTP respuestaWS = ConexionWS.peticionGET(url);
        try {
             if (respuestaWS.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) { 
                Gson gson = new Gson();
                Type tipoLista = new TypeToken<List<RegistroColaboradorUnidad>>(){}.getType();
                registros = gson.fromJson(respuestaWS.getContenido(), tipoLista);
                 for (RegistroColaboradorUnidad registro : registros) {
                     System.out.println("colaborador "+registro.getColaborador());
                 }
             }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return registros;
    }
    
    public static List<Colaborador> obtenerConductores(){
        List<Colaborador> lista = null;
        String url = Constantes.URL_WS+"/colaboradores/obtenerAsignacionesColaboradores";
        RespuestaHTTP respuestaWS = ConexionWS.peticionGET(url);
        try {
             if (respuestaWS.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) { 
                Gson gson = new Gson();
                Type tipoLista = new TypeToken<List<Colaborador>>(){}.getType();
                lista = gson.fromJson(respuestaWS.getContenido(), tipoLista);
             }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    public static List<Unidad> obtenerAsignacionesUnidades(){
        List<Unidad> lista = null;
        String url = Constantes.URL_WS+"/colaboradores/obtenerAsignacionesUnidades";
        RespuestaHTTP respuestaWS = ConexionWS.peticionGET(url);
        try {
             if (respuestaWS.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) { 
                Gson gson = new Gson();
                Type tipoLista = new TypeToken<List<Unidad>>(){}.getType();
                lista = gson.fromJson(respuestaWS.getContenido(), tipoLista);
             }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}