package org.app.attila.util;


import java.sql.DriverManager;
import java.sql.SQLException;

public class Connection {

    // Driver
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    // Connection
    private static java.sql.Connection connection = null;

    // Url
    private static final String url_db = "jdbc:mysql://localhost:3306/competition";

    // Connect
    public static void db_connect() throws SQLException, ClassNotFoundException {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException exception){
            System.out.println("ERREUR CHARGEMENT DRIVER");
            exception.printStackTrace();
            throw exception;
        }

        System.out.println("DRIVER CHARGER !!");

        try {
            connection = DriverManager.getConnection(url_db, "root","");
        } catch (SQLException sqlException){
            System.out.println("ERREUR CONNEXION !!" + sqlException);
            sqlException.printStackTrace();
            throw sqlException;
        }
        System.out.println("CONNECTER !!");
    }

    // Fermeture Connexion
    public static void db_disconnect() throws SQLException {
        try {
            if (connection != null && !connection.isClosed()){
                connection.close();
            }
        } catch (Exception exception){
            throw exception;
        }

    }

    public static java.sql.Connection getConnection() {

        return connection;
    }

    //  DB Query
    /*
    public static ResultSet db_execute_query(String query_statement) throws SQLException, ClassNotFoundException {
        Statement statement = null;
        ResultSet resultSet = null;
        CachedRowSetImpl cachedRowSet = null;

        try {
            db_connect();
            System.out.println("SELECT STATEMENT : " + query_statement + " \n");
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query_statement);
            cachedRowSet = new CachedRowSetImpl();
            cachedRowSet.populate(resultSet);
        } catch (SQLException sqlException) {
            System.out.println("PROBLEME D'EXECUTION DE LA REQUETE " + sqlException);
            sqlException.printStackTrace();
            throw sqlException;
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            db_disconnect();
        }
        return cachedRowSet;
    }
    */

}
