package org.app.attila.controller;

import javafx.animation.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DashboardController {

    @FXML
    private AnchorPane  anchor_root;

    @FXML
    private StackPane parent_container;

    @FXML
    private Button btn_perso;

    @FXML
    private Button btn_competition;

    @FXML
    private Label label_time;

    @FXML
    private Label label_date;

    Stage stage;
    Parent parent;

    @FXML
    public void initialize(){
        
        Date androany = new Date();
        DateFormat fullDateFormat = DateFormat.getDateInstance(DateFormat.FULL);
        label_date.setText(fullDateFormat.format(androany).toUpperCase());

        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.ZERO, event -> label_time.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")))),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    /*
    private void handleButtonClick(ActionEvent event) {
       if (event.getSource() == btn_competition){
            loadStage(this.getClass().getResource("/fxml/competition-view.fxml").toExternalForm());
       }
    }

    private void loadStage(String fxml) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image(getClass().getResource("/img/samourai.png").toExternalForm()));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */

    @FXML
    private void switchToMenuItem(ActionEvent event) throws IOException {
        Stage stage = null;
        Parent root = null;

        if (event.getSource() == btn_perso){
            stage = (Stage) btn_perso.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/fxml/athlete-view.fxml"));
        }

        Scene  scene  = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void switchToCompetitionItem(ActionEvent event) throws IOException {
        Stage stage = null;
        Parent root = null;

        if (event.getSource() == btn_competition){
            stage = (Stage) btn_competition.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/fxml/competition-view.fxml"));
        }
        Scene  scene  = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
