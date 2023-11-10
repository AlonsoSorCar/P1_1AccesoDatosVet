package com.example.p1_1accesodatosvet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class MascotaDAO {
    static Connection conexion;

    /**
     * Metodo conectar
     * Metodo para hacer la conexion con la base de datos MySQL
     * Utiliza fichero propierties para hacer la lectura de los datos para la conexion
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws IOException
     */
    public void conectar() throws ClassNotFoundException, SQLException, IOException {
        System.out.println("Conectando a la base de datos...");

        Properties properties = new Properties();

        properties.load(new FileInputStream(new File("src/main/resources/configuration/database.properties")));
        String host = String.valueOf(properties.get("host"));
        String port = String.valueOf(properties.get("port"));
        String name = String.valueOf(properties.get("name"));
        String username = String.valueOf(properties.get("username"));
        String password = String.valueOf(properties.get("password"));
        Class.forName("com.mysql.cj.jdbc.Driver");
        conexion = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + name + "?serverTimezone=UTC",
                username, password);


    }

    /**
     *Metodo desconectar
     * Metodo para hacer la desconexion a la bd
     * @throws SQLException
     */
    public void desconectar() throws SQLException {
        conexion.close();
    }

    /**
     * Metodo datosMascotas
     * Metodo para hacer la busqueda de los datos de las mascotas
     *
     * @return
     */
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

    /**
     * Metodo buscarMascota
     * Para hacer una busqueda de las mascotas en la BD por el nombre de la mascota.
     * Se usa en un controlador pasando el nombre de la mascota por el campo de texto y dandole al boton buscar
     * @param nombre
     * @return
     */
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

    /**
     * Metodo buscarPersonas
     * Para hacer una busqueda de los usuarios en la BD por el nombre de la persona.
     * @param nombre
     * @return
     */
    public ResultSet buscarPersonas(String nombre) {
        try {
            String sql = "SELECT * FROM usuarios WHERE nombre = ?";
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, nombre);
            ResultSet resultado = sentencia.executeQuery();
            return resultado;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *  Metodo eliminarMascota
     * Metodo para hacer la eliminacion de una mascota buscandola por el nombre
     * Manda una serie de alerts si hay errores
     * @param nombre
     */
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

    /**
     * Metodo comprobarMascotas
     * Metodo hecho para hacer la comprobación de si la mascota existe o no en la BD
     * @param nombre
     * @return
     */
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

    /**
     * Metodo actualizarMascota
     * Metodo para hacer el update de las mascotas con todos sus atributos
     * Manda alert si algo ha ido mal
     * @param mascota
     */
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

    /**
     * Metodo insertarMascota
     * Inserta una nueva mascota con todos los parametros necesarios.
     * Manda alert si algo ha ido mal
     * @param mascota
     */
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

    /**
     * Metodo insertarUsuario
     * Metodo para hacer la insercion de usuarios.
     * Se utiliza en el registro
     * @param nombre
     * @param salt
     * @param contrasenaHash
     */
    public void insertarUsuario(String nombre, String salt, String contrasenaHash) {
        try {
            String sql = "INSERT INTO usuarios (nombre, salt, contrasena_hash) VALUES (?, ?, ?)";

            PreparedStatement sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, nombre);
            sentencia.setString(2, salt);
            sentencia.setString(3, contrasenaHash);

            int filasInsertadas = sentencia.executeUpdate();
            if (filasInsertadas == 1) {
                System.out.println("Usuario insertado exitosamente.");
                AlertUtils.mostrarAlerta("Usuario insertado exitosamente.");
            } else {
                System.out.println("No se pudo insertar el usuario.");
                AlertUtils.mostrarError("No se pudo insertar el usuario.");
            }
        } catch (SQLException e) {
            System.out.println("Algo ha ido mal en la función insertarUsuario");
            throw new RuntimeException(e);
        }
    }


}
