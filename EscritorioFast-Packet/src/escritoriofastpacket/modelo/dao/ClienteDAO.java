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
import escritoriofastpacket.modelo.pojo.Mensaje;
import escritoriofastpacket.modelo.pojo.DatosRegistroCliente;
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
    
    
    
    public static DatosRegistroCliente obtenerClientePorID(Integer idCliente){
        DatosRegistroCliente datosCliente = null;
        String url = Constantes.URL_WS+"cliente/obtenerClientePorId/"+idCliente;
        RespuestaHTTP respuesta = ConexionWS.peticionGET(url);
        
      
            if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
                Gson gson = new Gson();
                datosCliente = gson.fromJson(respuesta.getContenido(), DatosRegistroCliente.class);
            }
        
        return datosCliente;
    
    }
    
    
    public static Mensaje registrarCliente(DatosRegistroCliente cliente){
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS+"cliente/registrar";
        Gson gson = new Gson();
        
        try{
            String parametros = gson.toJson(cliente);
            RespuestaHTTP respuesta = ConexionWS.peticionPOSTJson(url, parametros);
            System.out.println("JSON enviado: " + parametros);
            if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
                msj = gson.fromJson(respuesta.getContenido(), Mensaje.class);
            }else{
                msj.setError(true);
                msj.setContenido(respuesta.getContenido());
            }
        
        }catch(Exception e ){
           msj.setError(true);
           msj.setContenido(e.getMessage());
        }
        
        return msj;
    
    }
    
}
