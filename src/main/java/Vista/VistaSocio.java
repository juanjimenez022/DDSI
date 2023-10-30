/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Modelo.Socio;

/**
 *
 * @author jujis
 */
public class VistaSocio {

    public void MostrarSocio(Socio socio) {

        System.out.println("**********************************************************\n");
        System.out.println(socio.getNumeroSocio());
        System.out.println(socio.getNombre());
        System.out.println(socio.getDni());
        System.out.println(socio.getFechaNacimiento());
        System.out.println(socio.getTelefono());
        System.out.println(socio.getCorreo());
        System.out.println(socio.getFechaEntrada());
        System.out.println(socio.getCategoria());
        System.out.println("**********************************************************\n");

    }
    
    
    
    
}
