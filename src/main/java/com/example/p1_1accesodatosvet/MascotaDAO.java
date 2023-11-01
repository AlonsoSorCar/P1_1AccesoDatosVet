package com.example.p1_1accesodatosvet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class MascotaDAO {
    private static Connection conexion;

    /*   // Conexión a la base de datos
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
       }*/
    public void conectar() throws ClassNotFoundException, SQLException, IOException {
        System.out.println("Conectando a la base de datos...");

                Properties properties= new Properties();

                properties.load(new FileInputStream(new File("src/main/resources/configuration/database.properties")));
                String host=String.valueOf(properties.get("host"));
                String port=String.valueOf(properties.get("port"));
                String name=String.valueOf(properties.get("name"));
                String username=String.valueOf(properties.get("username"));
                String password=String.valueOf(properties.get("password"));
                Class.forName("com.mysql.cj.jdbc.Driver");
                conexion = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + name + "?serverTimezone=UTC",
                        username, password);


    }

    public void desconectar() throws SQLException {
        conexion.close();
    }

    public ResultSet datosMascotas() {
        try {
            String sql = "SELECT * FROM mascotas";
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            ResultSet resultado = sentencia.executeQuery(sql);
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet buscarMascota(String nombre) {
        try {
            String sql = "SELECT * FROM mascotas WHERE nombre = ?";
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, nombre);
            ResultSet resultado = sentencia.executeQuery();
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void eliminarMascota(String nombre) {
        try {
            String sql = "DELETE FROM mascotas WHERE nombre = ?";
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, nombre);
            int borrado = sentencia.executeUpdate();

            if (borrado > 0) {
                System.out.println("Mascota borrada exitosamente.");
                AlertUtils.mostrarAlerta("Mascota borrada exitosamente.");
            } else {
                System.out.println("No se pudo borrar la mascota.");
                AlertUtils.mostrarError("No se pudo borrar la mascota.");
            }
        } catch (SQLException e) {
            System.out.println("Algo ha ido mal en la función eliminarMascota");
            throw new RuntimeException(e);
        }
    }


    public boolean comprobarMascotas(String nombre) {
        try {
            String sql = "SELECT * FROM mascotas WHERE nombre = ?";
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, nombre);
            ResultSet rs = sentencia.executeQuery();

            if (rs.next()) {
                // Si hay registros la mascota EXISTE
                return true;
            } else {
                // Si no hay registros la mascota NO existe
                return false;
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }


    public void actualizarMascota(Mascota mascota) {
        try {
            String sql = "UPDATE mascotas SET nombre = ?, raza = ?, peso = ?, fechaN = ?, causaConsulta = ?, otros = ? WHERE id = ?";
            PreparedStatement sentencia = conexion.prepareStatement(sql);
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

            PreparedStatement sentencia = conexion.prepareStatement(sql);
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
