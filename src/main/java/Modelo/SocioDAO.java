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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jujis
 */
public class SocioDAO {

    Conexion conexion = null;
    PreparedStatement ps = null;
    VistaSocio Vsocio = new VistaSocio();

    public SocioDAO(Conexion c) {
        this.conexion = c;
    }

    public ArrayList<Socio> listaSocios() throws SQLException {

        ArrayList listaSocios = new ArrayList();

        String consulta = "SELECT * FROM SOCIO";
        ps = conexion.getConexion().prepareStatement(consulta);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Socio socio = new Socio(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8));
            listaSocios.add(socio);
        }

        return listaSocios;
    }

    public ArrayList<Socio> listaSocios(String letra) throws SQLException {

        ArrayList listaSocios = new ArrayList();

        String consulta = "SELECT * FROM SOCIO WHERE nombre LIKE ?";
        ps = conexion.getConexion().prepareStatement(consulta);
        letra = letra + "%";
        ps.setString(1, letra);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Socio socio = new Socio(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8));
            listaSocios.add(socio);
        }

        return listaSocios;
    }

    public void AltaSocio(Socio socio) throws SQLException {

        ps = conexion.getConexion().prepareStatement("INSERT INTO SOCIO VALUES (?,?,?,?,?,?,?,?)");
        ps.setString(1, socio.getNumeroSocio());
        ps.setString(2, socio.getNombre());
        ps.setString(3, socio.getDni());
        ps.setString(4, socio.getFechaNacimiento());
        ps.setString(5, socio.getTelefono());
        ps.setString(6, socio.getCorreo());
        ps.setString(7, socio.getFechaEntrada());
        ps.setString(8, socio.getCategoria());
        ps.executeUpdate();
    }

    public void BajaSocio(String numeroSocio) throws SQLException {
        ps = conexion.getConexion().prepareStatement("DELETE FROM SOCIO WHERE numeroSocio = ?");
        ps.setString(1, numeroSocio);
        ps.executeUpdate();
    }

    public void UpdateSocio(String aux, Socio socio) throws SQLException {
        ps = conexion.getConexion().prepareStatement("UPDATE SOCIO SET numeroSocio = ?, nombre = ?, dni = ?, fechaNacimiento = ?, telefono = ?, correo = ?, fechaEntrada = ?, categoria = ?  WHERE numeroSocio = ?");
        ps.setString(1, socio.getNumeroSocio());
        ps.setString(2, socio.getNombre());
        ps.setString(3, socio.getDni());
        ps.setString(4, socio.getFechaNacimiento());
        ps.setString(5, socio.getTelefono());
        ps.setString(6, socio.getCorreo());
        ps.setString(7, socio.getFechaEntrada());
        ps.setString(8, socio.getCategoria());
        ps.setString(9, aux);
        ps.executeUpdate();

    }

    public void salir(Conexion conexion) {

        try {
            conexion.desconexion();
        } catch (SQLException ex) {
            System.out.println("No se ha podido realizar la desconexion");
            Logger.getLogger(SocioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public boolean ExisteSocio(Socio socio) {
        boolean existencia = false;
        Socio socioaux = new Socio();
        ArrayList<Socio> listasocio = null;
        try {
            listasocio = listaSocios();
        } catch (SQLException ex) {
            System.out.println("No se pudo recurrir al metodo listasocios");
            Logger.getLogger(SocioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        int contador = 0;
        while (!existencia || contador < listasocio.size()) {
            listasocio.get(contador);
            if (socio.getDni().equals(socioaux.getDni())) {
                existencia = true;
            }
            contador++;
        }

        return existencia;
    }

    
    public void rellenarTablaSocios(ArrayList<Socio> Socios) {
        
        
        for (int i = 0; !Socios.isEmpty() && i<Socios.size(); i++) {
            Vsocio.MostrarSocio(Socios.remove(i));
            
        }

    }
}