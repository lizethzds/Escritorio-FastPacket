
package escritoriofastpacket.modelo.pojo;

/**
 *
 * @author lizet
 */
public class Paquete {
    
    private Integer idPaquete;
    private Integer ancho;
    private Integer altura;
    private Integer profundidad;
    private float peso;
    private Integer idEnvio;
    private String descripcion;

    public Paquete() {
    }

    public Paquete(Integer idPaquete, Integer ancho, Integer altura, Integer profundidad, float peso, Integer idEnvio, String descripcion) {
        this.idPaquete = idPaquete;
        this.ancho = ancho;
        this.altura = altura;
        this.profundidad = profundidad;
        this.peso = peso;
        this.idEnvio = idEnvio;
        this.descripcion = descripcion;
    }

    public Integer getIdPaquete() {
        return idPaquete;
    }

    public void setIdPaquete(Integer idPaquete) {
        this.idPaquete = idPaquete;
    }

    public Integer getAncho() {
        return ancho;
    }

    public void setAncho(Integer ancho) {
        this.ancho = ancho;
    }

    public Integer getAltura() {
        return altura;
    }

    public void setAltura(Integer altura) {
        this.altura = altura;
    }

    public Integer getProfundidad() {
        return profundidad;
    }

    public void setProfundidad(Integer profundidad) {
        this.profundidad = profundidad;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public Integer getIdEnvio() {
        return idEnvio;
    }

    public void setIdEnvio(Integer idEnvio) {
        this.idEnvio = idEnvio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
    
}
