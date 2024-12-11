
package escritoriofastpacket.modelo.dao;

import com.google.gson.Gson;
import escritoriofastpacket.modelo.ConexionWS;
import escritoriofastpacket.modelo.pojo.HistorialEnvio;
import escritoriofastpacket.modelo.pojo.Mensaje;
import escritoriofastpacket.modelo.pojo.RespuestaHTTP;
import escritoriofastpacket.utils.Constantes;
import java.net.HttpURLConnection;

/**
 *
 * @author lizet
 */
public class HistorialEnvioDAO {
    
   
    public static Mensaje registrarCambioEstatus(HistorialEnvio historialEnvio){
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS+"historial/registrarCambioHistorial";
        Gson gson = new Gson();
        
        try{
            String parametros = gson.toJson(historialEnvio);
            RespuestaHTTP respuesta = ConexionWS.peticionPOSTJson(url, parametros);
            if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
                msj = gson.fromJson(respuesta.getContenido(), Mensaje.class);    
            }else{
                msj.setError(true);
                msj.setContenido("Hubo un error al intentar registrar el cambio.");
            }
           
        }catch(Exception e){
            msj.setError(true);
            e.printStackTrace();
        
        }
        
        return msj;
        
    }
    
}
