package com.example.p1_1accesodatosvet;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertUtils {

    public static void mostrarError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setContentText(mensaje);
        alerta.show();
    }
    public static void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setContentText(mensaje);
        alerta.show();
    }
    public static boolean mostrarConfirmacion(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        //creo botones con la opcion si o no
        ButtonType botonSi = new ButtonType("Sí");
        ButtonType botonNo = new ButtonType("No");

        alert.getButtonTypes().setAll(botonSi, botonNo);

        Optional<ButtonType> resultado = alert.showAndWait();

        return resultado.isPresent() && resultado.get() == botonSi;
    }

}
