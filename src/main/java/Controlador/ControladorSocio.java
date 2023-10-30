/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Conexion;
import Modelo.SocioDAO;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jujis
 */
public class ControladorSocio {
    
    SocioDAO Sociodao;
    
    public void listarSocios(Conexion conexion){
        Sociodao = new SocioDAO(conexion);
        try {
            System.out.println("Listando socios");
            Sociodao.rellenarTablaSocios(Sociodao.listaSocios());
        } catch (SQLException ex) {
            Logger.getLogger(ControladorSocio.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("No se puedo listar Socios");
        }
        
    }
    
}
