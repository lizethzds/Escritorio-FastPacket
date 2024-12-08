/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package escritoriofastpacket.modelo.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import escritoriofastpacket.modelo.ConexionWS;
import escritoriofastpacket.modelo.pojo.DatosRegistroCliente;
import escritoriofastpacket.modelo.pojo.DatosRegistroEnvio;
import escritoriofastpacket.modelo.pojo.Envio;
import escritoriofastpacket.modelo.pojo.Mensaje;
import escritoriofastpacket.modelo.pojo.RespuestaHTTP;
import escritoriofastpacket.utils.Constantes;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.List;

/**
 *
 * @author lizet
 */
public class EnvioDAO {
    
    public static List<Envio> obtenerEnvios(){
        
        List<Envio> envios = null;
        
        String url = Constantes.URL_WS+"envio/obtenerEnvios";
        RespuestaHTTP respuesta = ConexionWS.peticionGET(url);
        
        try{
            if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
                Gson gson = new Gson();
                Type tipoLista = new TypeToken<List<Envio>>(){}.getType();
                envios  = gson.fromJson(respuesta.getContenido(), tipoLista);
            }
        
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return envios;
    }
    
    
    public static DatosRegistroEnvio obtenerDetallesEnvio(Integer idEnvio){
        DatosRegistroEnvio datosEnvio = null;
        String url = Constantes.URL_WS+"envio/obtenerEnvioPorId/"+idEnvio;
        RespuestaHTTP respuesta = ConexionWS.peticionGET(url);
        if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
                Gson gson = new Gson();
                datosEnvio = gson.fromJson(respuesta.getContenido(), DatosRegistroEnvio.class);
            }
        
        return datosEnvio;
        
    }
    
    
    public static Mensaje registrarEnvio(DatosRegistroEnvio envio){
        Mensaje msj = new Mensaje();
        String url =  Constantes.URL_WS+"envio/registrar";
        Gson gson = new Gson();
        try{
            String parametros = gson.toJson(envio);
            RespuestaHTTP respuesta = ConexionWS.peticionPOSTJson(url, parametros);
            if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
                msj = gson.fromJson(respuesta.getContenido(), Mensaje.class);
            }else{
                msj.setError(true);
                msj.setContenido(respuesta.getContenido());
            }
        }catch(Exception e){
        msj.setError(true);
        msj.setContenido("Hubo un problema al registrar el envio");
        e.printStackTrace();
        }
        
        return msj;
    }
    
     public static Mensaje editarEnvio(DatosRegistroEnvio envio){
        Mensaje msj = new Mensaje();
        String url =  Constantes.URL_WS+"envio/editar";
        Gson gson = new Gson();
        try{
            String parametros = gson.toJson(envio);
            RespuestaHTTP respuesta = ConexionWS.peticionPUTJson(url, parametros);
            if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
                msj = gson.fromJson(respuesta.getContenido(), Mensaje.class);
            }else{
                msj.setError(true);
                msj.setContenido(respuesta.getContenido());
            }
        }catch(Exception e){
        msj.setError(true);
        msj.setContenido("Hubo un problema al editar el envio");
        e.printStackTrace();
        }
        
        return msj;
    }
    
    
    
    
    public static Mensaje eliminarEnvio(Integer idEnvio){
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS+"envio/eliminar/"+idEnvio;
        Gson gson = new Gson();
        
        try{
            RespuestaHTTP respuesta = ConexionWS.peticionDELETEUrl(url);
            
            if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
                msj = gson.fromJson(respuesta.getContenido(), Mensaje.class);
            }else{
                msj.setError(true);
                msj.setContenido(respuesta.getContenido());
            }
            
        }catch(Exception e){
            msj.setError(true);
            msj.setContenido(e.getMessage());
        }
        
        return msj;
    }
    
}
