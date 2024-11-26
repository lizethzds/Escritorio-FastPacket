/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package escritoriofastpacket.modelo.dao;

import com.google.gson.Gson;
import escritoriofastpacket.modelo.ConexionWS;
import escritoriofastpacket.modelo.pojo.Login;
import escritoriofastpacket.modelo.pojo.RespuestaHTTP;
import escritoriofastpacket.utils.Constantes;
import java.net.HttpURLConnection;

/**
 *
 * @author lizet
 */
public class AutenticacionDAO {
    
    public static Login inciarSesionColaborador (String noPersonal, String password){
        Login respuesta = new Login();
        
        String url = Constantes.URL_WS+"autenticacion/loginEscritorio";
        String parametros = String.format("noPersonal=%s&password=%s", noPersonal,password);
        
        RespuestaHTTP respuestaWS = ConexionWS.peticionPOST(url, parametros);
        
        
        if(respuestaWS.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            respuesta = gson.fromJson(respuestaWS.getContenido(), Login.class);
            
        }else{
            respuesta.setError(true);
            respuesta.setMensaje("Por el momento, el servicio no está disponible");
        }
        
        return respuesta;
                
    
    }
    
}
