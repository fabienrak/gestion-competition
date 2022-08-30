package org.app.attila.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AppController implements Initializable {

    @FXML
    private AnchorPane background_pane;

    @FXML
    private ProgressIndicator progress_start_app;

    Stage stage = null;
    Parent parent = null;
    static double progress_value = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        splash();
    }

    private void splash() {
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    EventHandler<ActionEvent> actionEvent = new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            progress_value += 0.1;
                            progress_start_app.setProgress(10f);
                        }
                    };
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/auth/login-view.fxml"));
                            Stage stage = new Stage();
                            Scene scene = new Scene(fxmlLoader.load());
                            stage.setScene(scene);
                            stage.show();

                            background_pane.getScene().getWindow().hide();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }.start();
    }
}
