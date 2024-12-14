
package escritoriofastpacket.modelo.pojo;

/**
 *
 * @author lizet
 */
public class HistorialEnvio {
    
   private Integer idEnvio;
   private Integer idColaborador;
   private Integer idEstadoEnvio;
   private String comentario;

    public HistorialEnvio() {
    }

    public HistorialEnvio(Integer idEnvio, Integer idColaborador, Integer idEstadoEnvio, String comentario) {
        this.idEnvio = idEnvio;
        this.idColaborador = idColaborador;
        this.idEstadoEnvio = idEstadoEnvio;
        this.comentario = comentario;
    }

   

    public Integer getIdEnvio() {
        return idEnvio;
    }

    public void setIdEnvio(Integer idEnvio) {
        this.idEnvio = idEnvio;
    }

    public Integer getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(Integer idColaborador) {
        this.idColaborador = idColaborador;
    }

    public Integer getIdEstadoEnvio() {
        return idEstadoEnvio;
    }

    public void setIdEstadoEnvio(Integer idEstadoEnvio) {
        this.idEstadoEnvio = idEstadoEnvio;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
   
    
   
    
}
