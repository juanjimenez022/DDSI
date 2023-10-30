/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Conexion;
import Vista.VistaInfoBD;
import Vista.VistaMensajes;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jujis
 */
public class ControladorLogin {

    private Conexion conexion;
    private VistaMensajes vMensaje;
    private VistaInfoBD vInfodb;
    private ControladorPrincipal CP;

    public ControladorLogin() {

        vMensaje = new VistaMensajes();
        vInfodb = new VistaInfoBD();
        conectarBD();
        try {
            vInfodb.infoMetadatos(conexion.informacionBD());
        } catch (SQLException ex) {
            vMensaje.mensajeConsola("Error al obtener los metadatos de la base de datos ", ex.getMessage());
        }
        desconectarBD();

    }

    private Conexion conectarBD() {
        try {

            String server = "oracle";
            String ip = "172.17.20.39";
            String bd = "etsi";
            String u = "DDSI_021";
            String p = "76088140";

            if (true) {
                server = "mariadb";
                ip = "172.18.1.241";
                bd = "DDSI_021";
                u = "DDSI_021";
                p = "76088140";
            }

            conexion = new Conexion(server, ip, bd, u, p);
            vMensaje.mensajeConsola("Conexión Correcta");
        } catch (SQLException sqle) {
            vMensaje.mensajeConsola("Error de conexión ", sqle.getMessage());
        } catch (ClassNotFoundException ex) {
            vMensaje.mensajeConsola("Error indeterminado ", ex.getMessage());
        }
        CP = new ControladorPrincipal(conexion);
        CP.menu();
        return conexion;
    }

    private void desconectarBD() {

        try {
            conexion.desconexion();
        } catch (SQLException ex) {
            vMensaje.mensajeConsola("Error al desconectar ", ex.getMessage());
        }

    }

}
