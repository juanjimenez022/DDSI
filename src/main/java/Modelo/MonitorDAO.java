/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Vista.VistaSocio;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author jujis
 */
public class MonitorDAO {

    Session sesion;
    PreparedStatement ps = null;
    VistaSocio VMonitor = new VistaSocio();

    public MonitorDAO(Session sesion) {
        this.sesion = sesion;
    }

    public ArrayList<Monitor> listaMonitores() throws Exception {

        Transaction transaccion = sesion.beginTransaction();

        Query consulta = sesion.createNativeQuery("SELECT * FROM MONITOR M", Monitor.class);
        ArrayList<Monitor> monitores = (ArrayList<Monitor>) consulta.list();

        transaccion.commit();

        return monitores;

    }

    public ArrayList<Monitor> listaMonitoresLetra(String Letra) throws Exception {

        Transaction transaccion = sesion.beginTransaction();
        Letra += "%";
        Query consulta = sesion.createNativeQuery("SELECT * FROM MONITOR M "
                + "WHERE NOMBRE LIKE :letra", Monitor.class).setParameter("letra", Letra);
        ArrayList<Monitor> monitores = (ArrayList<Monitor>) consulta.list();

        transaccion.commit();

        return monitores;

    }

    public boolean AltaMonitor(Monitor monitor) throws SQLException {

        Transaction transaccion = sesion.beginTransaction();
        boolean AltaSatisfactoria = false;

        try {
            sesion.save(monitor);
            transaccion.commit();
            AltaSatisfactoria = true;

        } catch (Exception e) {
            transaccion.rollback();
            System.out.println("Error al dar de alta al monitor.\n\n" + e.getMessage());
        }

        return AltaSatisfactoria;

    }

    public boolean BajaMonitor(String codigo) throws SQLException {

        Transaction transaccion = sesion.beginTransaction();
        boolean BajaSatisfactoria = false;

        try {
            Monitor monitor = sesion.get(Monitor.class, codigo);

            sesion.delete(monitor);
            transaccion.commit();
            BajaSatisfactoria = true;

        } catch (Exception e) {
            transaccion.rollback();
            System.out.println("Error al dar de de Baja al monitor.\n\n" + e.getMessage());
        }

        return BajaSatisfactoria;

    }

    public void UpdateMonitor(Monitor monitor) throws SQLException {

        Monitor mon = sesion.get(Monitor.class, monitor.getCodMonitor());

        mon.setActividadesResponsable(monitor.getActividadesResponsable());
        mon.setCodMonitor(monitor.getCodMonitor());
        mon.setCorreo(monitor.getCorreo());
        mon.setDni(monitor.getDni());
        mon.setFechaEntrada(monitor.getFechaEntrada());
        mon.setNick(monitor.getNick());
        mon.setNombre(monitor.getNombre());
        mon.setTelefono(monitor.getTelefono());

        Transaction transaccion = sesion.beginTransaction();
        sesion.save(mon);

        transaccion.commit();

    }

    public boolean ExisteMonitor(String codigo) {

        Query consulta = sesion.createNativeQuery("SELECT CODMONITOR FROM MONITOR "
                + "WHERE CODMONITOR = :cod", Monitor.class).setParameter("cod", codigo);

        List resultado = consulta.list();
        if (resultado.isEmpty()) {
            return false;
        } else {
            return true;
        }

    }

    public ArrayList<Monitor> listaMonitoresResponsables(String Codigo) throws Exception {

        Transaction transaccion = sesion.beginTransaction();

        Query consulta = sesion.createQuery("SELECT m FROM Monitor m JOIN m.actividadesResponsable a "
                + "WHERE a.idActividad = :idActividad").setParameter("idActividad", Codigo);
        ArrayList<Monitor> monitores = (ArrayList<Monitor>) consulta.list();

        transaccion.commit();

        rellenarTablaMonitor(monitores);

        return monitores;

    }

    public void rellenarTablaMonitor(ArrayList<Monitor> monitores) {

        System.out.println(monitores.size());

        for (int i = 0; i < monitores.size(); i++) {

            if (monitores.get(i).getCodMonitor()!= null) {
                System.out.println("Número de Monitor: " + monitores.get(i).getCodMonitor());
            }

            if (monitores.get(i).getNombre() != null) {
                System.out.println("Nombre: " + monitores.get(i).getNombre());
            }

            if (monitores.get(i).getDni() != null) {
                System.out.println("DNI: " + monitores.get(i).getDni());
            }

            if (monitores.get(i).getTelefono() != null) {
                System.out.println("Teléfono: " + monitores.get(i).getTelefono());
            }

            if (monitores.get(i).getCorreo() != null) {
                System.out.println("Correo: " + monitores.get(i).getCorreo());
            }

            if (monitores.get(i).getFechaEntrada() != null) {
                System.out.println("Fecha de Entrada: " + monitores.get(i).getFechaEntrada());
            }

            if (monitores.get(i).getNick()!= null) {
                System.out.println("Nick: " + monitores.get(i).getNick());
            }

        }
    }

}

