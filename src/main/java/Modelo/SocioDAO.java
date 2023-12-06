/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Vista.VistaSocio;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.TypedQuery;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author jujis
 */
public class SocioDAO {

    Session sesion = null;
    PreparedStatement ps = null;
    VistaSocio Vsocio = new VistaSocio();
    Scanner sc = new Scanner(System.in);

    public SocioDAO(Session sesion) {
        this.sesion = sesion;
    }

    public ArrayList<Socio> listaSociosHQL() throws SQLException {

        Transaction transaccion = sesion.beginTransaction();

        Query consulta = sesion.createQuery("FROM Socio", Socio.class);
        ArrayList<Socio> socios = (ArrayList<Socio>) consulta.list();

        transaccion.commit();

        rellenarTablaSocios(socios);

        return socios;
    }

    public ArrayList<Socio> listaSociosSQLNativo() throws SQLException {

        Transaction transaccion = sesion.beginTransaction();

        Query consulta = sesion.createNativeQuery("SELECT * FROM SOCIO", Socio.class);
        ArrayList<Socio> socios = (ArrayList<Socio>) consulta.list();

        transaccion.commit();

        rellenarTablaSocios(socios);

        return socios;

    }

    public ArrayList<Socio> listaSociosConsultaNombrada() throws SQLException {

        Transaction transaccion = sesion.beginTransaction();

        Query consulta = sesion.createNamedQuery("Socio.findAll", Socio.class);
        ArrayList<Socio> socios = (ArrayList<Socio>) consulta.list();

        transaccion.commit();

        rellenarTablaSocios(socios);

        return socios;

    }

    public ArrayList<Object[]> listaSociosTelefonoyNombre() throws SQLException {

        Transaction transaccion = sesion.beginTransaction();
        Query consulta = sesion.createNamedQuery("Socio.findNombreTelefono");
        ArrayList<Object[]> Socios = (ArrayList<Object[]>) consulta.getResultList();
        transaccion.commit();

        rellenarTablaSocios(Socios, 1);

        return Socios;

    }

    public ArrayList<Object[]> listaSociosNombreyCategoria(char Categoria) throws SQLException {

        Transaction transaccion = sesion.beginTransaction();
        Query consulta = sesion.createNamedQuery("Socio.findNombreCategoria");
        consulta.setParameter("categoria", Categoria);
        ArrayList<Object[]> Socios = (ArrayList<Object[]>) consulta.getResultList();
        transaccion.commit();

        rellenarTablaSocios(Socios, 2);

        return Socios;

    }

    public List<Socio> listaSociosActividad(String codigo) {

        Transaction transaccion = sesion.beginTransaction();

        Query consulta = sesion.createNativeQuery("SELECT SOCIO.* FROM SOCIO JOIN REALIZA ON SOCIO.NUMEROSOCIO = REALIZA.NUMEROSOCIO "
                + "WHERE REALIZA.IDACTIVIDAD = :cod", Socio.class).setParameter("cod", codigo);

        ArrayList<Socio> socios = (ArrayList<Socio>) consulta.list();

        transaccion.commit();

        System.out.println("Cantidad de Socios encontrados: " + socios.size());

        for (int i = 0; i < socios.size(); i++) {
            Socio socio = socios.get(i);
            if (socio != null && socio.getNombre() != null) {
                System.out.println("Socio " + i + " con nombre: " + socio.getNombre());
            } else {
                System.out.println("Socio " + i + " tiene información nula.");
            }
        }

        return socios;

    }

    public void AltaSocio(int codigo) throws SQLException {

        Transaction transaccion = sesion.beginTransaction();
        Socio socio = new Socio();
        char categoria = 'A';
        String correo = "correoEjemplo";
        String dni = "99999999Z";
        String fechaEntrada = "2023/01/01";
        String fechaNacimiento = "2000/01/01";
        String nombre = "nombreEjemplo";
        String telefono = "999999999";
        Query consulta = sesion.createNativeQuery("SELECT MAX(numeroSocio) FROM SOCIO");
        String aux = (String) consulta.getSingleResult();
        aux = aux.replaceAll("[^0-9]", "");
        //int maxNumeroSocio = Integer.parseInt((String) consulta.getSingleResult()); //consulta.getSingleResult()
        int Nsocio = Integer.parseInt(aux) + 1;
        String numeroSocio = "S" + Nsocio;
        System.out.println("El numero de socio sera: " + numeroSocio);
        if (codigo == 1) {
            socio.setActividades(null);
            socio.setCategoria(categoria);
            socio.setCorreo(correo);
            socio.setDni(dni);
            socio.setFechaEntrada(fechaEntrada);
            socio.setFechaNacimiento(fechaNacimiento);
            socio.setNombre(nombre);
            socio.setNumeroSocio(String.valueOf(numeroSocio));
            socio.setTelefono(telefono);
        } else {

            System.out.println("Introduzca el Nombre del socio:");
            nombre = sc.nextLine();
            socio.setNombre(nombre);
            System.out.println("Introduzca el DNI del socio:");
            dni = sc.nextLine();
            socio.setDni(dni);
            System.out.println("Introduzca el Correo del socio:");
            correo = sc.nextLine();
            socio.setCorreo(correo);
            System.out.println("Introduzca el Telefono del socio:");
            telefono = sc.nextLine();
            socio.setTelefono(telefono);
            System.out.println("Introduzca la Fecha de entrada del socio: AAAA/MM/DD");
            fechaEntrada = sc.nextLine();
            socio.setFechaEntrada(fechaEntrada);
            System.out.println("Introduzca el Fecha de nacimiento del socio: AAAA/MM/DD");
            fechaNacimiento = sc.nextLine();
            socio.setFechaNacimiento(fechaNacimiento);
            System.out.println("Introduzca la categoria del socio:");
            categoria = Character.toUpperCase(sc.nextLine().charAt(0));
            socio.setCategoria(categoria);

            socio.setNumeroSocio(String.valueOf(numeroSocio));
            System.out.println("En numero de socio asignado es: " + numeroSocio);

            socio.setActividades(null);
        }

        // Guardar el socio en la base de datos
        sesion.save(socio);

        // Confirmar la transacción
        transaccion.commit();

        System.out.println("Socio insertado exitosamente en la base de datos.");

    }

    public void bajaSocio(String numeroSocio) {

        Transaction transaccion = sesion.beginTransaction();

        Socio socio = sesion.get(Socio.class, numeroSocio);

        if (socio != null) {
            sesion.delete(socio);
            transaccion.commit();
            System.out.println("Socio con número " + numeroSocio + " eliminado de la base de datos.");
        } else {
            System.out.println("No se encontró un socio con el número " + numeroSocio + ".");
        }
    }

    public void actualizarCategoriaSocio() {

        Transaction transaccion = sesion.beginTransaction();
        System.out.println("Introduzca el número de socio:");
        String numeroSocio = sc.nextLine();

        Socio socio = sesion.get(Socio.class, numeroSocio);

        if (socio != null) {
            System.out.println("Introduzca la nueva categoría:");
            char nuevaCategoria = Character.toUpperCase(sc.nextLine().charAt(0));

            socio.setCategoria(nuevaCategoria);
            sesion.update(socio);

            transaccion.commit();
            System.out.println("Categoría del socio con número " + numeroSocio + " actualizada a: " + nuevaCategoria);
        } else {
            System.out.println("No se encontró un socio con el número " + numeroSocio + ".");
        }
    }

    public void UpdateSocio(String aux, Socio socio) throws SQLException {

    }

    public void mostrarActividadesDeSocio() {
        System.out.println("Indicar el número de socio:");
        String numeroSocio = sc.nextLine();

        Transaction transaccion = sesion.beginTransaction();

        // Crear la consulta SQL nativa para obtener las actividades inscritas por el socio
        String sql = "SELECT a.* FROM ACTIVIDAD a "
                + "JOIN REALIZA r ON a.IDACTIVIDAD = r.IDACTIVIDAD "
                + "WHERE r.NUMEROSOCIO = :numeroSocio";

        // Ejecutar la consulta y obtener los resultados
        List<Actividad> actividades = sesion.createNativeQuery(sql, Actividad.class)
                .setParameter("numeroSocio", numeroSocio)
                .list();

        // Mostrar los resultados
        for (int i = 0; i < actividades.size(); i++) {
            System.out.println("Nombre de la actividad: " + actividades.get(i).getNombre() + ", Precio: " + actividades.get(i).getPrecioBaseMes());
        }

        transaccion.commit();

    }

    public boolean ExisteSocio(String numeroSocio) {
        Query consulta = sesion.createNativeQuery("SELECT COUNT(*) FROM SOCIO WHERE numeroSocio = :numeroSocio");
        consulta.setParameter("numeroSocio", numeroSocio);

        int count = ((Number) consulta.getSingleResult()).intValue();
        return count > 0;
    }

    public void rellenarTablaSocios(ArrayList<Socio> Socios) {

        System.out.println(Socios.size());

        for (int i = 0; i < Socios.size(); i++) {

            if (Socios.get(i).getNumeroSocio() != null) {
                System.out.println("Número de Socio: " + Socios.get(i).getNumeroSocio());
            }

            if (Socios.get(i).getNombre() != null) {
                System.out.println("Nombre: " + Socios.get(i).getNombre());
            }

            if (Socios.get(i).getDni() != null) {
                System.out.println("DNI: " + Socios.get(i).getDni());
            }

            if (Socios.get(i).getFechaNacimiento() != null) {
                System.out.println("Fecha de Nacimiento: " + Socios.get(i).getFechaNacimiento());
            }

            if (Socios.get(i).getTelefono() != null) {
                System.out.println("Teléfono: " + Socios.get(i).getTelefono());
            }

            if (Socios.get(i).getCorreo() != null) {
                System.out.println("Correo: " + Socios.get(i).getCorreo());
            }

            if (Socios.get(i).getFechaEntrada() != null) {
                System.out.println("Fecha de Entrada: " + Socios.get(i).getFechaEntrada());
            }

            if (Socios.get(i).getCategoria() != null) {
                System.out.println("Categoría: " + Socios.get(i).getCategoria());
            }

        }
    }

    public void rellenarTablaSocios(ArrayList<Object[]> Socios, int diferencial) {
        if (diferencial == 1) {

            for (Object[] socio : Socios) {
                String valor1 = (String) socio[0];
                String valor2 = (String) socio[1];

                System.out.println("Nombre: " + valor1 + ", Teléfono: " + valor2);

            }
        }

        if (diferencial == 2) {
            for (Object[] socio : Socios) {
                String valor1 = (String) socio[0];
                char valor2 = (char) socio[1];

                System.out.println("Nombre: " + valor1 + ", categoria: " + valor2);
            }
        }

    }

}
