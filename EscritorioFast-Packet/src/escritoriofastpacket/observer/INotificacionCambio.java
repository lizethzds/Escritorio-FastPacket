/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package escritoriofastpacket.observer;

import escritoriofastpacket.modelo.pojo.Colaborador;

/**
 *
 * @author david
 */
public interface INotificacionCambio {
    
    public void notificarCambioColaboradorSesion(Colaborador colaboradorCambio);
}
