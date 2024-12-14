
package escritoriofastpacket.modelo.pojo;

/**
 *
 * @author lizet
 */
public class HistorialEnvio {
    
   private Integer idEnvio;
   private Integer idColaborador;
   private Integer idEstadoEnvio;

    public HistorialEnvio() {
    }

    public HistorialEnvio(Integer idEnvio, Integer idColaborador, Integer idEstadoEnvio) {
        this.idEnvio = idEnvio;
        this.idColaborador = idColaborador;
        this.idEstadoEnvio = idEstadoEnvio;
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
   
   
    
}
