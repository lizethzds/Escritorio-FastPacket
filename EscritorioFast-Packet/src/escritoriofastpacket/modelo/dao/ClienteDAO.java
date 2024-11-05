/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package escritoriofastpacket.modelo.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import escritoriofastpacket.modelo.ConexionWS;
import escritoriofastpacket.modelo.pojo.Cliente;
import escritoriofastpacket.modelo.pojo.RespuestaHTTP;
import escritoriofastpacket.utils.Constantes;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.List;

/**
 *
 * @author lizet
 */
public class ClienteDAO {
    
    public static List<Cliente> obtenerClientes()
    {
    
        List<Cliente> clientes = null;
        
        String url = Constantes.URL_WS+"cliente/obtenerLista";
        RespuestaHTTP respuesta = ConexionWS.peticionGET(url);
        
        try{
            if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
                Gson gson = new Gson();
                Type tipoLista = new TypeToken<List<Cliente>>(){}.getType();
                clientes = gson.fromJson(respuesta.getContenido(), tipoLista);
                
            }
                
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return clientes;
    }
    
}
