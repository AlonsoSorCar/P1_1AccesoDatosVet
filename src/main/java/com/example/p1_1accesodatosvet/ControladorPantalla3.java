package com.example.p1_1accesodatosvet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;

public class ControladorPantalla3 {

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

    @FXML
    private Button btnVolver;

    @FXML
    void guardarInsercion(ActionEvent event) {
        try {
            if(!( txtNombre.getText().isEmpty() || txtRaza.getText().isEmpty() || txtPeso.getText().isEmpty() ||txtFechaN.getText().isEmpty()||txtCausaConsulta.getText().isEmpty())){
                String nombre = txtNombre.getText();
                String raza = txtRaza.getText();
                double peso = Double.parseDouble(txtPeso.getText());
                Date fechaN = Date.valueOf(txtFechaN.getText());
                String causaConsulta = txtCausaConsulta.getText();
                String otros = txtOtros.getText();

                Mascota mascotaNueva = new Mascota( nombre, raza, peso, fechaN, causaConsulta, otros);

                MascotaDAO mascotaDAO = new MascotaDAO();
                mascotaDAO.insertarMascota(mascotaNueva);
            }else{
                AlertUtils.mostrarAlerta("Debes rellenar los campos");
            }

        } catch (Exception ex) {
            System.out.println("Algo ha ido mal en la función guardarInsercion");
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
