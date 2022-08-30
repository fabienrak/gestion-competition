package org.app.attila.util;

import javafx.scene.control.Alert;
import javafx.stage.StageStyle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static Connection connection = null;


    static {
        // TODO : Mysql database pour tester  , utiliser sqlite

        String url_db = "jdbc:mysql://localhost:3306/attila";
        String db_user = "root";
        String db_password = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url_db, db_user, db_password);

            Alert  alertDB = new Alert(Alert.AlertType.INFORMATION);
            alertDB.setTitle("INFORMATION");
            alertDB.setContentText("CONNEXION SUCCESS");
            alertDB.initStyle(StageStyle.UNDECORATED);
            alertDB.showAndWait();

        } catch (SQLException | ClassNotFoundException exception) {
            Alert  alertDB = new Alert(Alert.AlertType.ERROR);
            alertDB.setTitle("ERREUR");
            alertDB.setContentText("ERREUR DE CONNEXION");
            alertDB.initStyle(StageStyle.UNDECORATED);
            alertDB.showAndWait();

            exception.printStackTrace();
            exception.getMessage();
        }

    }

    public static Connection getConnection(){
        return connection;
    }
}
