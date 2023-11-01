package com.example.p1_1accesodatosvet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.security.Principal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private TableView<Mascota> tabla;
    @FXML
    private TableColumn<Mascota, Integer> cidMascota;

    @FXML
    private TableColumn<Mascota, String> cNombreMascota;

    @FXML
    private TableColumn<Mascota, String> cRazaMascota;

    @FXML
    private TableColumn<Mascota, Double> cPesoMascota;

    @FXML
    private TableColumn<Mascota, Date> cFechaNMascota;

    @FXML
    private TableColumn<Mascota, String> cCausaConsultaMascota;

    @FXML
    private TableColumn<Mascota, String> cOtrosMascota;
    ObservableList<Mascota> mascotas;
    @FXML
    private Button btnBuscar;

    @FXML
    private TextField txtBuscar;

    @FXML
    private TextField txtModificar;

    private Mascota mascotaSeleccionada;

    @FXML
    private Button btnAnyadir;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mascotas = FXCollections.observableArrayList();

        //Funcion de seteado de la tabla, para no repetir codigo
        funcionSeteadoTabla();

        try {
            MascotaDAO mascotaDAO = new MascotaDAO();
            mascotaDAO.conectar();
            ResultSet rs = mascotaDAO.datosMascotas();

            llenadoTabla(rs);

        } catch (Exception e) {
            System.out.println("Algo ha ido mal en el initialize");
            e.printStackTrace();
        }
        //para la seleccion de la mascota
        tabla.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                mascotaSeleccionada = tabla.getSelectionModel().getSelectedItem();
                if (mascotaSeleccionada != null) {
                    //el import!!
                    String mensaje = "¿Estás seguro de que deseas eliminar a " + mascotaSeleccionada.getNombre() + "?";
                    if (AlertUtils.mostrarConfirmacion(mensaje)) {
                        MascotaDAO mascotaDAO = new MascotaDAO();
                        mascotaDAO.eliminarMascota(mascotaSeleccionada.getNombre());
                    }
                }
            }
        });

    }

    @FXML
    void buscarMascota(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {
        MascotaDAO mascotaDAO = new MascotaDAO();
//        mascotaDAO.loadDriver();
        mascotaDAO.conectar();
        String nombreMascota = txtBuscar.getText();
        if (!nombreMascota.isEmpty() && mascotaDAO.comprobarMascotas(txtBuscar.getText())) {
            mascotaDAO.buscarMascota(txtBuscar.getText());
            tabla.getItems().clear();
            //Funcion de seteado de la tabla, para no repetir codigo
            funcionSeteadoTabla();
            ResultSet rs = mascotaDAO.buscarMascota(txtBuscar.getText());
            llenadoTabla(rs);
        } else {
            AlertUtils.mostrarError("INTRODUCE NOMBRE CORRECTO");
        }

    }


    @FXML
    void volverTabla(ActionEvent event) {
        mascotas = FXCollections.observableArrayList();
        //Funcion de seteado de la tabla, para no repetir codigo
        funcionSeteadoTabla();
        try {
            MascotaDAO mascotaDAO = new MascotaDAO();
            mascotaDAO.conectar();
            ResultSet rs = mascotaDAO.datosMascotas();

            llenadoTabla(rs);

        } catch (Exception e) {
            System.out.println("Algo ha ido mal en la funcion volverTabla");
            e.printStackTrace();
        }
    }

    @FXML
    void cambiarPantallaModificar(ActionEvent event) {
        MascotaDAO mascotaDAO = new MascotaDAO();

        String nombreMascota = txtModificar.getText();
        if (!nombreMascota.isEmpty() && mascotaDAO.comprobarMascotas(txtModificar.getText())) {
            try {
                Node source = (Node) event.getSource();
                Stage escena = (Stage) source.getScene().getWindow();
                escena.close();

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("pantalla2.fxml"));
                Parent root = fxmlLoader.load();
                ControladorPantalla2 controller = fxmlLoader.getController();
                controller.setNombreMascota(txtModificar.getText());
                Stage stage = new Stage();
                stage.setResizable(false);
                stage.sizeToScene();
                stage.setTitle("Pantalla2");

                Scene scene = new Scene(root);
                stage.setScene(scene);

                stage.show();
            } catch (IOException e) {
                System.out.println("algo ha ido mal en la funcion cambiarPantallaModificar");
                e.printStackTrace();
            }
        } else {
            AlertUtils.mostrarError("INTRODUCE NOMBRE CORRECTO");
        }
    }

    @FXML
    void anyadirMascota(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("pantalla3.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Pantalla 3");
            stage.setScene(scene);
            Node source = (Node) event.getSource();
            Stage ventanaActual = (Stage) source.getScene().getWindow();
            ventanaActual.close();
            stage.show();
        } catch (IOException e) {
            System.out.println("Algo ha ido mal al cambiar a la Pantalla 3");
            e.printStackTrace();
        }
    }


    void funcionSeteadoTabla() {
        try {
            this.cidMascota.setCellValueFactory(new PropertyValueFactory("id"));
            this.cNombreMascota.setCellValueFactory(new PropertyValueFactory("nombre"));
            this.cRazaMascota.setCellValueFactory(new PropertyValueFactory("raza"));
            this.cPesoMascota.setCellValueFactory(new PropertyValueFactory("peso"));
            this.cFechaNMascota.setCellValueFactory(new PropertyValueFactory("fechaN"));
            this.cCausaConsultaMascota.setCellValueFactory(new PropertyValueFactory("causaConsulta"));
            this.cOtrosMascota.setCellValueFactory(new PropertyValueFactory("otros"));
        } catch (Exception ex) {
            System.out.println("algo ha ido mal en la funcion funcionSeteadoTabla");
            ex.printStackTrace();
        }
    }

    private void llenadoTabla(ResultSet rs) {
        try {
            while (rs.next()) {
                int id = Integer.parseInt(rs.getString("id"));
                String nombre = (rs.getString("nombre"));
                String raza = (rs.getString("raza"));
                Double peso = Double.parseDouble(rs.getString("peso"));
                Date fechaN = Date.valueOf(rs.getString("fechaN"));
                String causaConsulta = (rs.getString("causaConsulta"));
                String otros = (rs.getString("otros"));
                this.mascotas.add(new Mascota(id, nombre, raza, peso, fechaN, causaConsulta, otros));
                this.tabla.setItems(this.mascotas);
            }
        } catch (SQLException ex) {
            System.out.println("Algo ha ido mal en la funcion llenadoTabla");
            ex.printStackTrace();
        }
    }
}
