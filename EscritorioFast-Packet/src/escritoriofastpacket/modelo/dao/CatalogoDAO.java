
package escritoriofastpacket.modelo.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import escritoriofastpacket.modelo.ConexionWS;
import escritoriofastpacket.modelo.pojo.Estado;
import escritoriofastpacket.modelo.pojo.EstadoEnvio;
import escritoriofastpacket.modelo.pojo.Municipio;
import escritoriofastpacket.modelo.pojo.RespuestaHTTP;
import escritoriofastpacket.utils.Constantes;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lizet
 */
public class CatalogoDAO {
    
    
    public static List<Estado> obtenerEstados(){
        List<Estado> estados = new ArrayList<>();
        
        String url = Constantes.URL_WS+"catalogo/obtenerEstados";
        RespuestaHTTP respuesta = ConexionWS.peticionGET(url);
    try{
    if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            Type tipoLista = new TypeToken<ArrayList<Estado>>(){}.getType();
            estados = gson.fromJson(respuesta.getContenido(), tipoLista);
        }
    }catch(Exception e){
    e.printStackTrace();
    }

        return estados;
    }
    
     public static List<Municipio> obtenerMunicipios(Integer idEstado){
        List<Municipio> municipios = new ArrayList<>();
        
        String url = Constantes.URL_WS+"catalogo/obtenerMunicipiosEstado/"+idEstado;
        RespuestaHTTP respuesta = ConexionWS.peticionGET(url);
    try{
    if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            Type tipoLista = new TypeToken<ArrayList<Municipio>>(){}.getType();
            municipios = gson.fromJson(respuesta.getContenido(), tipoLista);
        }
    }catch(Exception e){
    e.printStackTrace();
    }

        return municipios;
    }
    
     
     public static Estado obtenerEstadoMunicipio(Integer idMunicipio){
         Estado estado = new Estado();
         String url = Constantes.URL_WS+"catalogo/obtenerEstadoMunicipio/"+idMunicipio;
         RespuestaHTTP respuesta = ConexionWS.peticionGET(url);
         if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK);{
         Gson gson = new Gson();
         estado = gson.fromJson(respuesta.getContenido(), Estado.class);
        }
         
         return estado;
         
     }
     
     
     public static List<EstadoEnvio> obtenerEstatusEnvio(){
         List<EstadoEnvio> estadosEnvio = new ArrayList<>();
         String url = Constantes.URL_WS+"catalogo/obtenerEstatus";
         RespuestaHTTP respuesta = ConexionWS.peticionGET(url);
         try{
          if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            Type tipoLista = new TypeToken<ArrayList<EstadoEnvio>>(){}.getType();
            estadosEnvio = gson.fromJson(respuesta.getContenido(), tipoLista);
         }
         
        
        }catch(Exception e){
            e.printStackTrace();
        }
         
         return estadosEnvio;
     }
    
    
    
}
