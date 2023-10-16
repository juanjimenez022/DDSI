/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author jujis
 */
public class Conexion {

    private Connection conn = null;
    
    String sgbd;
    String ip;
    String service_bd;
    String usuario;
    String password;

    public Conexion(String sgbd, String ip, String service_bd, String usuario,
            String password) throws ClassNotFoundException, SQLException {

        this.sgbd = sgbd;
        this.ip = ip;
        this.service_bd = service_bd;
        this.usuario = usuario;
        this.password = password;

        if (sgbd == "oracle") {


            conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@" + ip + ":1521:" + service_bd, usuario , password);

        } else {

            conn = DriverManager.getConnection(
                    "jdbc:mariadb://" + ip + ":3306/" + service_bd , usuario , password);
        }
    }

    Connection getConexion() {

        return conn;
    }

    public void desconexion() throws SQLException {
        conn.close();
    }
    
    public DatabaseMetaData  informacionBD() throws SQLException{
        
        return conn.getMetaData();
        
    }

}
