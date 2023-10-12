package com.example.p1_1accesodatosvet;

import com.example.p1_1accesodatosvet.Mascota;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class ControladorPantalla2 {

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtRaza;

    @FXML
    private TextField txtPeso;

    @FXML
    private TextField txtFechaN;

    @FXML
    private TextField txtCausaConsulta;

    @FXML
    private TextField txtOtros;

    @FXML
    private Button btnGuardar;

    private Mascota mascotaSeleccionada;

    @FXML
    private Button btnVolver;

    @FXML
    private Button btnAnyadir;

    public void setNombreMascota(String nombreMascota) {
        if (nombreMascota != null) {
            MascotaDAO mascotaDAO = new MascotaDAO();
            try {
                mascotaDAO.loadDriver();
                mascotaDAO.connect();
                ResultSet rs = mascotaDAO.buscarMascota(nombreMascota);

                if (rs.next()) {
                    txtId.setText(rs.getString("id"));
                    txtNombre.setText(rs.getString("nombre"));
                    txtRaza.setText(rs.getString("raza"));
                    txtPeso.setText(rs.getString("peso"));
                    txtFechaN.setText(rs.getString("fechaN"));
                    txtCausaConsulta.setText(rs.getString("causaConsulta"));
                    txtOtros.setText(rs.getString("otros"));
                } else {
                    AlertUtils.mostrarError("MASCOTA NO ENCONTRADA EN LA BASE DE DATOS");
                }
            } catch (SQLException e) {
                System.out.println("Algo ha ido mal en la funcion setNombreMascota");
                throw new RuntimeException(e);
            }
        }
    }


    @FXML
    void guardarModificacion(ActionEvent event) {
        try {
            int id = Integer.parseInt(txtId.getText());
            String nombre = txtNombre.getText();
            String raza = txtRaza.getText();
            double peso = Double.parseDouble(txtPeso.getText());
            Date fechaN = Date.valueOf(txtFechaN.getText());
            String causaConsulta = txtCausaConsulta.getText();
            String otros = txtOtros.getText();

            Mascota mascotaActualizada = new Mascota(id, nombre, raza, peso, fechaN, causaConsulta, otros);

            MascotaDAO mascotaDAO = new MascotaDAO();
            mascotaDAO.actualizarMascota(mascotaActualizada);
        } catch (Exception ex) {
            System.out.println("Algo ha ido mal en la funcion guardarModificacion");
            ex.printStackTrace();
        }

    }

    @FXML
    void volverAPantalla1(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent root = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setTitle("Veterinario!");
            Scene scene = new Scene(root);
            stage.setScene(scene);


            Node source = (Node) event.getSource();
            Stage ventanaActual = (Stage) source.getScene().getWindow();
            ventanaActual.close();

            stage.show();
        } catch (IOException e) {
            System.out.println("Algo ha ido mal en la funcion volverAPantalla1");
            e.printStackTrace();
        }
    }


}


