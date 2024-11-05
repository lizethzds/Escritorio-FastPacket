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
public class DatosRegistroCliente {
    
    private Cliente cliente;
    private Direccion direccion;
    private Integer filasAfectadas;
    private String error;

    public DatosRegistroCliente() {
    }

    public DatosRegistroCliente(Cliente cliente, Direccion direccion, Integer filasAfectadas, String error) {
        this.cliente = cliente;
        this.direccion = direccion;
        this.filasAfectadas = filasAfectadas;
        this.error = error;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public Integer getFilasAfectadas() {
        return filasAfectadas;
    }

    public void setFilasAfectadas(Integer filasAfectadas) {
        this.filasAfectadas = filasAfectadas;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
    
    
    
}
