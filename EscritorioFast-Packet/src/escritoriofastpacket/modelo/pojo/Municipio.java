/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package escritoriofastpacket.modelo.pojo;

/**
 *
 * @author lizet
 */
public class Municipio {
    private Integer idMunicipio;
    private String nombreMunicipio;
    private Integer idEstado;

    public Municipio() {
    }

    public Municipio(Integer idMunicipio, String nombreMunicipio, Integer idEstado) {
        this.idMunicipio = idMunicipio;
        this.nombreMunicipio = nombreMunicipio;
        this.idEstado = idEstado;
    }

    public Integer getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(Integer idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public String getNombreMunicipio() {
        return nombreMunicipio;
    }

    public void setNombreMunicipio(String nombreMunicipio) {
        this.nombreMunicipio = nombreMunicipio;
    }

    public Integer getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }

    @Override
    public String toString() {
        return "-" + nombreMunicipio;
    }
    
    
    
    
    
}
