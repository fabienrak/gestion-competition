package org.app.attila.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.app.attila.model.Partenaire;
import org.app.attila.util.DatabaseConnection;
import org.app.attila.util.SqliteConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PartenaireController {

    @FXML
    private TextField nom_part_field;

    @FXML
    private Button btn_upload_logo;

    @FXML
    private TextField contact_part_field;

    @FXML
    private Button btn_add_part;

    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Connection connection = DatabaseConnection.getConnection();
    Partenaire partenaire = new Partenaire();

    Stage stage;

    /*private void insert() {
        String query_partenaire = "INSERT INTO partenaire (nom_partenaire, logo, contact)";

        try {
            preparedStatement = connection.prepareStatement(query_partenaire);
            preparedStatement.setString(1, nom_part_field.getText());
//            preparedStatement.setByte(2, );

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }*/

    /*private void uploadLogo(){
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extensionFilterJpg = new FileChooser.ExtensionFilter("IMAGE FILE (*.jpg)","*.jpg");
        FileChooser.ExtensionFilter extensionFilterPng = new FileChooser.ExtensionFilter("IMAGE FILE (*.png)","*.png");
        FileChooser.ExtensionFilter extensionFilterJpeg = new FileChooser.ExtensionFilter("IMAGE FILE (*.jpeg)","*.jpeg");

        fileChooser.getExtensionFilters().addAll(extensionFilterJpg,extensionFilterPng, extensionFilterJpeg);

        File logo_file = fileChooser.showOpenDialog(stage);
        System.out.println("IMAGE : "+logo_file.getName());

    }*/

}
