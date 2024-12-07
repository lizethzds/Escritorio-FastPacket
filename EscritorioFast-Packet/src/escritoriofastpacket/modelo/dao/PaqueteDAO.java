/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package escritoriofastpacket.modelo.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import escritoriofastpacket.modelo.ConexionWS;
import escritoriofastpacket.modelo.pojo.Mensaje;
import escritoriofastpacket.modelo.pojo.Paquete;
import escritoriofastpacket.modelo.pojo.RespuestaHTTP;
import escritoriofastpacket.utils.Constantes;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.List;

/**
 *
 * @author lizet
 */
public class PaqueteDAO {
        
    
    
    public static List<Paquete> obtenerPaquetesEnvio(Integer idEnvio){
        
        
        
        List<Paquete> paquetes = null;
        
        
        String url = Constantes.URL_WS+"paquete/obtenerPaquetesEnvio/" + idEnvio;
        RespuestaHTTP respuesta = ConexionWS.peticionGET(url);
        
        try{
            if(respuesta.getCodigoRespuesta()== HttpURLConnection.HTTP_OK ){
                Gson gson = new Gson();
                Type tipoLista = new TypeToken<List<Paquete>>(){}.getType();
                paquetes = gson.fromJson(respuesta.getContenido(), tipoLista);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return paquetes;
    }
    
    
    public static Mensaje registrarPaquete(Paquete paquete){
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS+"paquete/registrar";
        Gson gson = new Gson();
        
        try{
            
            String parametros = gson.toJson(paquete);
            RespuestaHTTP respuesta = ConexionWS.peticionPOSTJson(url, parametros);
            if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
                msj = gson.fromJson(respuesta.getContenido(), Mensaje.class);
                
            }else{
                msj.setError(true);
                msj.setContenido("Hubo un error al intentar registrar el paquete.");
            }
        }catch(Exception e){
            msj.setError(true);
            e.printStackTrace();
        
        }
        
        return msj;
    
    }
    
    
    public static Mensaje editarPaquete(Paquete paquete){
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS+"paquete/editar";
        Gson gson = new Gson();
        
        try{
            
            String parametros = gson.toJson(paquete);
            RespuestaHTTP respuesta = ConexionWS.peticionPUTJson(url, parametros);
            if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
                msj = gson.fromJson(respuesta.getContenido(), Mensaje.class);
                
            }else{
                msj.setError(true);
                msj.setContenido("Hubo un error al intentar registrar el paquete.");
            }
        }catch(Exception e){
            msj.setError(true);
            e.printStackTrace();
        
        }
        
        return msj;
    
    }
    
    
    
    
    
    
    
    public static Mensaje eliminarPaquete(Integer idPaquete){
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS+"paquete/eliminar/"+idPaquete;
        Gson gson = new Gson();
        msj.setError(true);
        
        try{
            RespuestaHTTP respuesta = ConexionWS.peticionDELETEUrl(url);
            
            if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
                msj.setError(false);
                msj = gson.fromJson(respuesta.getContenido(), Mensaje.class);
                
            }else{
                
                msj.setContenido(respuesta.getContenido());
            }
        }catch(Exception e){
            msj.setContenido(e.getMessage());
            
        }
        
        return msj;
        
    }
    
}
