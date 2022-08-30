package org.app.attila.util;

import java.sql.*;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CrudHelper {

    // TODO : Singleton pour operation crud

    /*
    *   Operations Lire
    * */
    public static Object read(String table_name, String field_name, int field_datatype, String index_field_name, int index_datatype, Object index) throws IllegalAccessException {
        StringBuilder query_builder = new StringBuilder("SELECT ");
        query_builder.append(field_name);
        query_builder.append(" FROM ");
        query_builder.append(table_name);
        query_builder.append(" WHERE ");
        query_builder.append(index_field_name);
        query_builder.append(" = ");
        query_builder.append(convert_object_to_sqlfield(index, index_datatype));

        try(Connection connection = SqliteConnection.db_connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query_builder.toString());
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                resultSet.next();
                switch(field_datatype){
                    case Types.INTEGER:
                        return resultSet.getInt(field_name);
                    case Types.VARCHAR:
                        return resultSet.getString(field_name);
                    case Types.DATE:
                        return resultSet.getDate(field_name).toString();
                    default:
                        throw new IllegalArgumentException("Index type "+ index_datatype + " non supporte");
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() +
                    " ERREUR DE REQUETE " + table_name + " PAR INDEX " + index + " COLUMN " + field_name);
            return null;
        }
    }

    /*
    *   Creation
    * */
    public static long create(String tableName, String[] columns, Object[] values, int[] types) {
        int number = Math.min(Math.min(columns.length, values.length), types.length);

        StringBuilder queryBuilder = new StringBuilder("INSERT INTO " + tableName + " (");
        for (int i = 0; i < number; i++) {
            queryBuilder.append(columns[i]);
            if (i < number - 1) queryBuilder.append(", ");
        }
        queryBuilder.append(") ");
        queryBuilder.append(" VALUES (");
        for (int i = 0; i < number; i++) {
            switch (types[i]) {
                case Types.VARCHAR:
                    queryBuilder.append("'");
                    queryBuilder.append((String) values[i]);
                    queryBuilder.append("'");
                    break;
                case Types.INTEGER:
                    queryBuilder.append((int) values[i]);
            }
            if (i < number - 1) queryBuilder.append(", ");
        }
        queryBuilder.append(");");

        try (Connection conn = SqliteConnection.db_connect()) {
            PreparedStatement pstmt = conn.prepareStatement(queryBuilder.toString());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getLong(1);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": ERREUR AJOUT DANS LA BASE DE DONNEE !! ");
            return -1;
        }
        return -1;
    }


    /*
     *  Conversion object to sql
     */
    private static String convert_object_to_sqlfield(Object value, int type) throws IllegalAccessException {
        StringBuilder  query_builder = new StringBuilder();
        switch (type) {
            case Types.VARCHAR:
                query_builder.append("'");
                query_builder.append(value);
                query_builder.append("'");
                break;
            case Types.INTEGER:
                query_builder.append(value);
                break;
            default:
                throw new IllegalAccessException("Index type " + type + " non supporte");
        }
        return query_builder.toString();
    }
}
