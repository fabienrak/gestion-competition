package org.app.attila.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.app.attila.model.Manager;
import org.app.attila.util.DatabaseConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {
    // TODO : Controller pour login

    @FXML
    private TextField username_field;

    @FXML
    private TextField mdp_field;

    @FXML
    private Button btn_login;

    Scene scene;
    Stage dialogStage = new Stage();

    Manager manager = new Manager();
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Connection connection = DatabaseConnection.getConnection();

    public void login(ActionEvent event) {
        String user = username_field.getText().toString();
        String password = mdp_field.getText().toString();

        //System.out.println("USER : " + user + " PASSWORD : "+password+" \n");

        String query_login = "SELECT * FROM manager WHERE username = ? AND password = ?";

        if (verifyInfo()){
            try {
                preparedStatement = connection.prepareStatement(query_login);
                preparedStatement.setString(1,username_field.getText());
                preparedStatement.setString(2,mdp_field.getText());

                preparedStatement.execute();
                resultSet = preparedStatement.executeQuery();
                ProgressIndicator progressIndicator = new ProgressIndicator();

                if (!resultSet.next()){
                    Alert alertLogin = new Alert(Alert.AlertType.WARNING);
                    alertLogin.setTitle("ERREUR");
                    alertLogin.setContentText("INFORMATION INVALIDE");
                    alertLogin.showAndWait();

                } else {
                    Alert alertLogin = new Alert(Alert.AlertType.WARNING);
                    alertLogin.setTitle("INFORMATION");
                    alertLogin.setContentText("LOGIN SUCCESS");
                    alertLogin.showAndWait();

                    Node source = (Node) event.getSource();
                    dialogStage = (Stage) source.getScene().getWindow();
                    dialogStage.close();
                    scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/dashboard-view.fxml")));
                    scene.getStylesheets().clear();
                    scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

                    dialogStage.setScene(scene);
                    dialogStage.show();
                }
                /*if (resultSet.next()){
                    resultSet.getString("username");
                    resultSet.getString("password");
                    System.out.println("USER : "+resultSet.getString("username"));
                    System.out.println("PASSWORD : "+resultSet.getString("password"));

                    if (username_field.getText() == resultSet.getString("username")){
                        System.out.println("USERAME VALIDE");
                    } else if (mdp_field.getText() == resultSet.getString("password")) {
                        System.out.println("PASSWORD VALIDE");
                    }
                }*/
               // System.out.println("STATEMENT : "+preparedStatement);

            } catch (SQLException | IOException sqlException) {
                sqlException.printStackTrace();
            }
        }

    }

    private boolean verifyInfo() {
        if (username_field.getText() == null || username_field.getText().isEmpty()){
            Alert  alertUsername = new Alert(Alert.AlertType.WARNING);
            alertUsername.setTitle("ERREUR");
            alertUsername.setContentText("CHAMP USERNAME VIDE");
            alertUsername.showAndWait();
        } else if (mdp_field.getText() == null || mdp_field.getText().isEmpty()) {
            Alert  alertPassword = new Alert(Alert.AlertType.WARNING);
            alertPassword.setTitle("ERREUR");
            alertPassword.setContentText("CHAMP MOT DE PASSE VIDE");
            alertPassword.showAndWait();
        }
        return true;
    }
}
