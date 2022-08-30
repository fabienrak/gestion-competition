package org.app.attila.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {

    private Parent root;
    private Stage stage;
    private Scene scene;

    //@FXML
    //private AnchorPane root;

    @FXML
    void btnCompetition(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/competition-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        //System.out.println("COUCOU JAVAFX");
    }
}