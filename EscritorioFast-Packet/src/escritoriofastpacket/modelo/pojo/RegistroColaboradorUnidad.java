/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package escritoriofastpacket.modelo.pojo;

/**
 *
 * @author david
 */
public class RegistroColaboradorUnidad {
    private Colaborador colaborador;
    private Unidad unidad;

    public RegistroColaboradorUnidad() {
    }

    public RegistroColaboradorUnidad(Colaborador colaborador, Unidad unidad) {
        this.colaborador = colaborador;
        this.unidad = unidad;
    }

    public Unidad getUnidad() {
        return unidad;
    }

    public Colaborador getColaborador() {
        return colaborador;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }

    public void setUnidad(Unidad unidad) {
        this.unidad = unidad;
    }
    
}

