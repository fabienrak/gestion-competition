package org.app.attila;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.app.attila.util.DatabaseConnection;

import java.sql.Connection;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/splash-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Connection connection = DatabaseConnection.getConnection();

        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

        String iconLink = this.getClass().getResource("/img/samourai.png").toExternalForm();
        stage.setTitle("APP");
        stage.getIcons().add(new Image(iconLink));
        //stage.initStyle(StageStyle.UNDECORATED)

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
