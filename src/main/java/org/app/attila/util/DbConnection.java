package org.app.attila.util;

import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbConnection {

    private static  final String db_location = DbConnection.class.getResource("/database/database.db").toExternalForm();

    private static Connection db_connect(String db_location) {
        String db_prefix = "jdbc:sqlite:";
        Connection connection;
        try {
            connection = DriverManager.getConnection(db_prefix + db_location);

            Alert alertDB = new Alert(Alert.AlertType.INFORMATION);
            alertDB.setTitle("INFORMATION");
            alertDB.setContentText("CONNEXION SUCCESS");
            alertDB.showAndWait();

        } catch (SQLException sqlException) {
            Logger.getAnonymousLogger().log(Level.SEVERE, null, LocalDateTime.now() + ": ERREUR CONNEXION DATABASE DANS : " + db_location);
            sqlException.printStackTrace();

            Alert  alertDB = new Alert(Alert.AlertType.ERROR);
            alertDB.setTitle("ERREUR");
            alertDB.setContentText("ERREUR DE CONNEXION");
            alertDB.showAndWait();

            return null;
        }
        return connection;
    }
}
