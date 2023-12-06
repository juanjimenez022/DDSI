/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Config.HibernateUtilMariaDB;
import Config.HibernateUtilOracle;
import Vista.VistaMensajes;
import java.sql.Connection;
import java.sql.SQLException;
import org.hibernate.SessionFactory;

/**
 *
 * @author jujis
 */
public class ControladorLogin {
    
    private SessionFactory sessionFactory;
    
    Boolean BD = false; //True = Oracle, False = MariaDB
    VistaMensajes vMensaje = new VistaMensajes();
    
    
    private Connection conn = null;
    
    String sgbd;
    String ip;
    String service_bd;
    String usuario;
    String password;
    
    public ControladorLogin() {

        conectarBD();
        ControladorPrincipal controladorP = new ControladorPrincipal(sessionFactory.openSession());

        desconectarBD();

    }

    private void conectarBD() {
        
        try {
            if (BD) {
                sessionFactory = HibernateUtilOracle.getSessionFactory();
            } else if (!BD) {
                sessionFactory = HibernateUtilMariaDB.getSessionFactory();
            }
            vMensaje.mensajeConsola("Conexión Correcta con Hibernate");
        } catch (ExceptionInInitializerError e) {
            Throwable cause = e.getCause();
            System.out.println("Error en la conexión. Revise el fichero .cfg.xml: " + cause.getMessage());
        }

    }

    private void desconectarBD() {
        
        sessionFactory.close();

    }

}
