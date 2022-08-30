package org.app.attila.util;

import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SqliteConnection {

    /*
     *   Location Fichier Database Driver
     */
    private static  final String db_location = DbConnection.class.getResource("/database/database.db").toExternalForm();

    /*
     *   Verification Driver
     */
    private static boolean check_driver() {
        try {
            Class.forName("org.sqlite.JDBC");
            DriverManager.registerDriver(new org.sqlite.JDBC());
            return true;
        } catch (ClassNotFoundException | SQLException exception) {
            Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() + ": DRIVER NON TROUVER");
            exception.printStackTrace();
            return false;
        }
    }

    /*
     *    Verification Connection
     */
    private static boolean check_connection() throws SQLException {
        try(Connection connection = db_connect()) {
            return connection != null;
        }  catch (SQLException exception) {
            Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() + ": CONNEXION NON ETABLIE !!");
            return false;
        }
    }

    /*
     *    DB Connection
     */
    public static Connection db_connect() {
        String db_prefix = "jdbc:sqlite:";
        Connection connection;
        try {

            check_driver();
            check_connection();

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
