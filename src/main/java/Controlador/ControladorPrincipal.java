/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Conexion;
import Modelo.SocioDAO;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jujis
 */
public class ControladorPrincipal {

    private Conexion conexion;
    
    public ControladorPrincipal(Conexion conexion) {
        this.conexion=conexion;
        
    }
    
    public void menu(){
        int eleccion=0;
        ControladorSocio CS = new ControladorSocio();
        System.out.println("\n\nQue desea visualizar: \n     1)Socios \n     2)Monitores \n     3)Actividades \n\n     0)Salir");
        Scanner sc = new Scanner(System.in);
        eleccion = sc.nextInt();
        switch (eleccion) {
            case 0: 
                break;
            case 1:
                CS.listarSocios(conexion);
                break;
            case 2:
                System.out.println("Pendiente de contruir");
                menu();
                break;
            case 3:
                System.out.println("Pendiente de contruir");
                menu();
                break;
            default:
                System.out.println("Elija una opcion dentro del rango permitido");
                menu();
        }
    }
    
}
