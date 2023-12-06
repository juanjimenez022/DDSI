/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.ActividadDAO;
import Modelo.MonitorDAO;
import Modelo.SocioDAO;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author jujis
 */
class ControladorPrincipal {

    private Session sesion;
    //private Session sesion;
    private Transaction transaccion;

    private MonitorDAO mDAO;
    private SocioDAO sDAO;
    private ActividadDAO aDAO;
    Scanner sc = new Scanner(System.in);
    Scanner sc2 = new Scanner(System.in);

    public ControladorPrincipal(Session sesion) {
        this.sesion = sesion;
        mDAO = new MonitorDAO(sesion);
        sDAO = new SocioDAO(sesion);
        aDAO = new ActividadDAO(sesion);
        try {
            menu();
        } catch (Exception ex) {
            Logger.getLogger(ControladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void menu() throws SQLException, Exception {
        int eleccion = 0;
        String Scodigo;
        int Ncodigo;
        do {

            eleccion = SubMenu();

            switch (eleccion) {
                case 0:
                    break;
                case 1:
                    sDAO.listaSociosHQL();
                    break;
                case 2:
                    sDAO.listaSociosSQLNativo();
                    break;
                case 3:
                    sDAO.listaSociosConsultaNombrada();
                    break;
                case 4:
                    sDAO.listaSociosTelefonoyNombre();
                    break;
                case 5:
                    System.out.println("Indique la categoria deseaada: ");
                    char Categoria = sc2.nextLine().charAt(0);
                    sDAO.listaSociosNombreyCategoria(Categoria);
                    break;
                case 6:
                    System.out.println("Indique el codigo de la actividad deseaada: ");
                    Scodigo = sc2.nextLine();
                    mDAO.listaMonitoresResponsables(Scodigo);
                    break;
                case 7:
                    System.out.println("Indique el codigo de la actividad deseaada: ");
                    Scodigo = sc2.nextLine();
                    sDAO.listaSociosActividad(Scodigo);
                    break;
                case 8:
                    do {
                        System.out.println("1) Socio aleatorio \n2) Introcudir datos socio");
                        Ncodigo = sc2.nextInt();
                    } while (Ncodigo != 1 && Ncodigo != 2);

                    sDAO.AltaSocio(Ncodigo);
                    break;

                case 9:
                    System.out.println("Introcudir numero de socio");
                    Scodigo = sc2.nextLine();
                    sDAO.bajaSocio(Scodigo);
                    break;
                    
                case 10:
                    sDAO.actualizarCategoriaSocio();
                    break;
                    
                case 11:
                    aDAO.darAltaSocioEnActividad();
                    break;
                    
                case 12:
                    aDAO.darBajaSocioDeActividad();
                    break;
                
                case 13:
                    aDAO.mostrarSociosDeActividad();
                    break;    
                    
                case 14:
                    sDAO.mostrarActividadesDeSocio();
                    break;  
                    
                default:
                    System.out.println("Elija una opcion dentro del rango permitido");

            }
        } while (eleccion != 0);
    }

    public int SubMenu() {
        int eleccion = 0;
        System.out.println("\n\nQue desea visualizar: "
                + "\n       1) Socios (HQL) "
                + "\n       2) Socios (SQL) "
                + "\n       3) Socios (Consulta Nombrada) "
                + "\n       4) Nombre y Telefono de los socios"
                + "\n       5) Nombre y Categoria de los socios"
                + "\n       6) Responsable de una actividad"
                + "\n       7) Socios de una actividad"
                + "\n       8) Alta de un socio"
                + "\n       9) Baja de un socio"
                + "\n       10) Actualización de la categoría de un socio"
                + "\n       11) Inscripción de un socio en una actividad"
                + "\n       12) Baja de un socio de una actividad"
                + "\n       13) Listado de socios inscritos en una actividad"
                + "\n       14) Listado de actividades de un socio"
                + "\n\n     0) Salir");
        eleccion = sc.nextInt();

        return eleccion;

    }

}
