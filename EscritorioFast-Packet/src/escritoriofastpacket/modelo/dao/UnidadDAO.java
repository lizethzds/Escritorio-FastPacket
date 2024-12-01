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
import escritoriofastpacket.modelo.pojo.RespuestaHTTP;
import escritoriofastpacket.modelo.pojo.TipoUnidad;
import escritoriofastpacket.modelo.pojo.Unidad;
import escritoriofastpacket.utils.Constantes;
import escritoriofastpacket.utils.Utilidades;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.List;
import javafx.scene.control.Alert;

/**
 *
 * @author uriel
 */
public class UnidadDAO {
    
    public static List<Unidad> obtenerUnidades (){
        List<Unidad> unidades = null;
        String url = Constantes.URL_WS+"unidad/listaUnidades";
        RespuestaHTTP respuesta = ConexionWS.peticionGET(url);
        
        try {
            if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
                Gson gson = new Gson();
                Type tipoLista = new TypeToken<List<Unidad>>(){}.getType();
                unidades = gson.fromJson(respuesta.getContenido(), tipoLista);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return unidades;
    }
    
    public static List<TipoUnidad> obtenerTipoUnidad () {
        List<TipoUnidad> tipoUnidad = null;
        String url = Constantes.URL_WS+"unidad/listaTipoUnidad";
        RespuestaHTTP respuesta = ConexionWS.peticionGET(url);
        
        try {
            if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
                Gson gson = new Gson();
                Type tipoLista = new TypeToken<List<TipoUnidad>>(){}.getType();
                tipoUnidad = gson.fromJson(respuesta.getContenido(), tipoLista);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tipoUnidad;
    }
    
    public static Mensaje registrarUnidad (Unidad unidad){
        Mensaje respuesta = new Mensaje();
        String url = Constantes.URL_WS+"unidad/registrar";
        Gson gson = new Gson();
        
        try {
            String data = gson.toJson(unidad);
            System.err.println(data);
            RespuestaHTTP respuestaWS = ConexionWS.peticionPOSTJson(url, data);
            if (respuestaWS.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
                respuesta = gson.fromJson(respuestaWS.getContenido(), Mensaje.class);
            }else{
                respuesta.setError(true);
                respuesta.setContenido(respuestaWS.getContenido());
            }
        } catch (Exception e) {
            respuesta.setError(true);
            respuesta.setContenido(e.getMessage());
        }
        return respuesta;
    }
    
}
