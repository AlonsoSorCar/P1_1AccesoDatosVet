package com.example.p1_1accesodatosvet;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class MascotaDAO {
    //private static Connection conexion;

    // Conexión a la base de datos
    private static Connection conn = null;

    // Configuración de la conexión a la base de datos
    private static final String DB_HOST = "localhost";
    private static final String DB_PORT = "3306";
    private static final String DB_NAME = "veterinario";
    private static final String DB_URL = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME + "?serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "toor";
    private static final String DB_MSQ_CONN_OK = "CONEXIÓN CORRECTA";
    private static final String DB_MSQ_CONN_NO = "ERROR EN LA CONEXIÓN";

    public static boolean loadDriver() {
        try {
            System.out.print("Cargando Driver...");
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("OK!");
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean connect() {
        try {
            System.out.print("Conectando a la base de datos...");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            System.out.println("OK!");
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
/*    public void conectar() throws ClassNotFoundException, SQLException, IOException {
        System.out.println("conectando...");
        Properties configuration = new Properties();
        configuration.load(R.getProperties("database.properties"));
        String host = configuration.getProperty("host");
        String port = configuration.getProperty("port");
        String name = configuration.getProperty("name");
        String username = configuration.getProperty("username");
        String password = configuration.getProperty("password");

        Class.forName("com.mysql.cj.jdbc.Driver");
        conexion = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + name + "?serverTimezone=UTC",
                username, password);

    }

   public void desconectar() throws SQLException {
        conexion.close();
    }*/

    public ResultSet datosMascotas() {
        try {
            String sql = "SELECT * FROM mascotas";
            PreparedStatement sentencia = conn.prepareStatement(sql);
            ResultSet resultado = sentencia.executeQuery(sql);
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet buscarMascota(String mas) {
        try {
            String sql = "SELECT * FROM mascotas WHERE nombre=+ '" + mas + "';";
            PreparedStatement sentencia = conn.prepareStatement(sql);
            ResultSet resultado = sentencia.executeQuery(sql);
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void eliminarMascota(String mas) {
        try {
            String sql = "DELETE FROM mascotas WHERE nombre = '" + mas + "';";
            PreparedStatement sentencia = conn.prepareStatement(sql);
            sentencia.executeUpdate();

            int borrado = sentencia.executeUpdate();
            System.out.println(sentencia.executeUpdate());
            if (borrado <= 1) {
                System.out.println("Mascota borrada exitosamente.");
                AlertUtils.mostrarAlerta("Mascota borrada exitosamente.");
            } else {
                System.out.println("No se pudo borrar la mascota.");
                AlertUtils.mostrarError("No se pudo borrar la mascota.");
            }
        } catch (SQLException e) {
            System.out.println("Algo ha ido mal en la funcion eliminarMascota");
            throw new RuntimeException(e);
        }
    }

    public boolean comprobarMascotas(String nombre) {
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM mascotas WHERE nombre=+ '" + nombre + "';";
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                //si hay registro return true
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void actualizarMascota(Mascota mascota) {
        try {
            String sql = "UPDATE mascotas SET nombre = ?, raza = ?, peso = ?, fechaN = ?, causaConsulta = ?, otros = ? WHERE id = ?";
            PreparedStatement sentencia = conn.prepareStatement(sql);
            sentencia.setString(1, mascota.getNombre());
            sentencia.setString(2, mascota.getRaza());
            sentencia.setDouble(3, mascota.getPeso());
            sentencia.setDate(4, (Date) mascota.getFechaN());
            sentencia.setString(5, mascota.getCausaConsulta());
            sentencia.setString(6, mascota.getOtros());
            sentencia.setInt(7, mascota.getId());

            int filasActualizadas = sentencia.executeUpdate();
            if (filasActualizadas == 1) {
                System.out.println("Mascota actualizada exitosamente.");
                AlertUtils.mostrarAlerta("Mascota actualizada exitosamente.");
            } else {
                System.out.println("No se pudo actualizar la mascota.");
                AlertUtils.mostrarError("No se pudo actualizar la mascota.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void insertarMascota(Mascota mascota) {
        try {
            String sql = "INSERT INTO mascotas (nombre, raza, peso, fechaN, causaConsulta, otros) VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement sentencia = conn.prepareStatement(sql);
            sentencia.setString(1, mascota.getNombre());
            sentencia.setString(2, mascota.getRaza());
            sentencia.setDouble(3, mascota.getPeso());
            sentencia.setDate(4, new java.sql.Date(mascota.getFechaN().getTime()));
            sentencia.setString(5, mascota.getCausaConsulta());
            sentencia.setString(6, mascota.getOtros());

            int filasInsertadas = sentencia.executeUpdate();
            if (filasInsertadas == 1) {
                System.out.println("Mascota insertada exitosamente.");
                AlertUtils.mostrarAlerta("Mascota insertada exitosamente.");
            } else {
                System.out.println("No se pudo insertar la mascota.");
                AlertUtils.mostrarError("No se pudo insertar la mascota.");
            }
        } catch (SQLException e) {
            System.out.println("Algo ha ido mal en la función insertarMascota");
            throw new RuntimeException(e);
        }
    }


}
