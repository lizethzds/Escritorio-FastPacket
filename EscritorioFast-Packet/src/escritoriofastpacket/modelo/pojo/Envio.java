
package escritoriofastpacket.modelo.pojo;

/**
 *
 * @author lizet
 */
public class Envio {
    
    private Integer idEnvio;
    private String noGuia;
    private float costoEnvio;
    private Integer idEstadoEnvio;
    private Integer idCliente;
    private Integer idColaborador;
    private Integer idDireccionDestino;
    private String cliente;
    private String conductor;
    private String direccionOrigen;
    private String estatus;

    public Envio() {
    }

    public Envio(Integer idEnvio, String noGuia, float costoEnvio, Integer idEstadoEnvio, Integer idCliente, Integer idColaborador, Integer idDireccionDestino, String cliente, String conductor, String direccionOrigen, String estatus) {
        this.idEnvio = idEnvio;
        this.noGuia = noGuia;
        this.costoEnvio = costoEnvio;
        this.idEstadoEnvio = idEstadoEnvio;
        this.idCliente = idCliente;
        this.idColaborador = idColaborador;
        this.idDireccionDestino = idDireccionDestino;
        this.cliente = cliente;
        this.conductor = conductor;
        this.direccionOrigen = direccionOrigen;
        this.estatus = estatus;
    }

  

    public Integer getIdEnvio() {
        return idEnvio;
    }

    public void setIdEnvio(Integer idEnvio) {
        this.idEnvio = idEnvio;
    }

    public String getNoGuia() {
        return noGuia;
    }

    public void setNoGuia(String noGuia) {
        this.noGuia = noGuia;
    }

    public Integer getIdEstadoEnvio() {
        return idEstadoEnvio;
    }

    public void setIdEstadoEnvio(Integer idEstadoEnvio) {
        this.idEstadoEnvio = idEstadoEnvio;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(Integer idColaborador) {
        this.idColaborador = idColaborador;
    }

    public Integer getIdDireccionDestino() {
        return idDireccionDestino;
    }

    public void setIdDireccionDestino(Integer idDireccionDestino) {
        this.idDireccionDestino = idDireccionDestino;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getConductor() {
        return conductor;
    }

    public void setConductor(String conductor) {
        this.conductor = conductor;
    }

    public String getDireccionOrigen() {
        return direccionOrigen;
    }

    public void setDireccionOrigen(String direccionOrigen) {
        this.direccionOrigen = direccionOrigen;
    }

    public float getCostoEnvio() {
        return costoEnvio;
    }

    public void setCostoEnvio(float costoEnvio) {
        this.costoEnvio = costoEnvio;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }
    
    
    
    
    
    
}
