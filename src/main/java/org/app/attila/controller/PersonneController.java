package org.app.attila.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.app.attila.model.Personne;
import org.app.attila.util.DatabaseConnection;
import org.app.attila.util.SqliteConnection;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersonneController implements Initializable {

    @FXML
    private TextField age_field;

    @FXML
    private DatePicker date_field;

    @FXML
    private TextField nom_field;

    @FXML
    private TextField poids_field;

    @FXML
    private TextField prenom_field;

    @FXML
    private Button save_person_button;

    @FXML
    private Button delete_person_button1;

    @FXML
    private Button edit_person_button;

    @FXML
    private ComboBox<String> sexe_field;

    @FXML
    private ComboBox<String> categorie_field;

    @FXML
    private ComboBox<String> grade_field;

    @FXML
    private ComboBox<String> club_field;

    @FXML
    private TableView<Personne> table_personne;

    @FXML
    private TableColumn<Personne, Integer> id_col;

    @FXML
    private TableColumn<Personne, String> nom_col;

    @FXML
    private TableColumn<Personne, String> prenom_col;

    @FXML
    private TableColumn<Personne, String> date_col;

    @FXML
    private TableColumn<Personne, Integer> age_col;

    @FXML
    private TableColumn<Personne, String> poids_col;

    @FXML
    private TableColumn<Personne, String> sexe_col;

    @FXML
    private TableColumn<Personne, Integer> categorie_col;

    @FXML
    private TableColumn<?, ?> grade_col;


    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Connection connection = DatabaseConnection.getConnection();
    Personne personne =  null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Select sexe
        ObservableList<String> sexeList = FXCollections.observableArrayList("MASCULIN","FEMININ");
        sexe_field.setItems(sexeList);
        sexe_field.setValue("MASCULIN");

        // Select categorie
        /*
        ArrayList<String> list_cat = getAllCategorie();
        ObservableList<String> cat_options = FXCollections.observableArrayList(list_cat);
        categorie_field.setItems(cat_options);

         */

        // Select grade
        /*
        ArrayList<String> list_grade = getAllGrade();
        ObservableList<String> grade_options = FXCollections.observableArrayList(list_grade);
        grade_field.setItems(grade_options);

         */

        // Select club
        /*
        ArrayList<String> list_club = getAllClub();
        ObservableList<String> club_options = FXCollections.observableArrayList(list_club);
        club_field.setItems(club_options);

         */

        /* Get id item  selected combobox
        club_field.getSelectionModel();
        int s_index  = club_field.getSelectionModel().getSelectedIndex();
        System.out.println("SELECTED INDEX  : " + s_index);
         */

        affiche();
    }

    @FXML
    private void tableHandleButtonAction(MouseEvent event) {
        Personne personne = table_personne.getSelectionModel().getSelectedItem();
        id_col.setText(String.valueOf(personne.getId()));
    }

    @FXML
    private void enregistrer_personne(ActionEvent event) {
        insert();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("SUCCESS");
        alert.setContentText("PERSONNE ENREGISTRER AVEC SUCCES !");

        alert.showAndWait();
        clear();
    }

    //  Get liste personne
    public ObservableList<Personne> getPersonne() {
        ObservableList<Personne> list_personne = FXCollections.observableArrayList();

        String query = "SELECT * FROM  personne";
        //String query = "SELECT personne.id,nom,prenom,date_naissance,age,sexe,poids,categorie.id, nom_categorie FROM  personne INNER JOIN categorie ON categorie.id = " + categorie_field.getSelectionModel().getSelectedIndex();
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Personne personne = new Personne();
                personne.setId(resultSet.getInt("id"));
                personne.setNom(resultSet.getString("nom"));
                personne.setPrenom(resultSet.getString("prenom"));
                personne.setAge(resultSet.getInt("age"));
                personne.setDate_naissance(resultSet.getDate("date_naissance"));
                personne.setSexe(resultSet.getString("sexe"));
                personne.setPoids(resultSet.getString("poids"));

                //personne.setCategorie_id(resultSet.getInt("categorie_id"));

                list_personne.add(personne);

                System.out.println("liste: "+personne);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            Logger.getLogger(PersonneController.class.getName()).log(Level.SEVERE, null, sqlException);
        }
        return list_personne;
    }

    // Get liste categorie
    /*
    public ArrayList<String> getAllCategorie() {
        String categori_query = "SELECT * FROM categorie";

        ArrayList<String> cat_list = new ArrayList();
        try {
            preparedStatement = connection.prepareStatement(categori_query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                cat_list.add(resultSet.getString("nom_categorie"));
            }
            return cat_list;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }
     */

    // Get liste Club
    /*
    public ArrayList<String> getAllClub() {
        String club_query = "SELECT *  FROM club";
        ArrayList<String> club_list = new ArrayList();
        try {
            preparedStatement = connection.prepareStatement(club_query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                club_list.add(resultSet.getString("nom_club"));
            }
            return club_list;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

     */

    // Get liste grade
    /*
    public ArrayList<String> getAllGrade() {
        String grade_query = "SELECT * FROM grade";
        ArrayList<String> grade_list = new ArrayList();

        try {
            preparedStatement = connection.prepareStatement(grade_query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                grade_list.add(resultSet.getString("titre_grade"));
            }
            return grade_list;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

     */

    //  affiche liste personne datatable
    public void affiche() {
        ObservableList<Personne> list_pers = getPersonne();
        id_col.setCellValueFactory(new PropertyValueFactory<Personne, Integer>("id"));
        nom_col.setCellValueFactory(new PropertyValueFactory<Personne, String>("nom"));
        prenom_col.setCellValueFactory(new PropertyValueFactory<Personne, String>("prenom"));
        date_col.setCellValueFactory(new PropertyValueFactory<Personne, String>("date_naissance"));
        age_col.setCellValueFactory(new PropertyValueFactory<Personne,  Integer>("age"));
        sexe_col.setCellValueFactory(new PropertyValueFactory<Personne, String>("sexe"));
        poids_col.setCellValueFactory(new PropertyValueFactory<Personne, String>("poids"));
        categorie_col.setCellValueFactory(new PropertyValueFactory<Personne,Integer>("categorie_id"));

        table_personne.setItems(list_pers);
    }

    public void insert() {
        String query = "INSERT INTO personne (nom, prenom, date_naissance, age, sexe, poids) VALUES (?,?,?,?,?,?)";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,nom_field.getText());
            preparedStatement.setString(2,prenom_field.getText());
            preparedStatement.setDate(3, Date.valueOf(date_field.getValue()));
            preparedStatement.setInt(4, Integer.parseInt(age_field.getText()));
            preparedStatement.setString(5, sexe_field.getSelectionModel().getSelectedItem());
            preparedStatement.setString(6,poids_field.getText());
           // preparedStatement.setInt(7,categorie_field.getSelectionModel().getSelectedIndex());

            preparedStatement.executeUpdate();
            affiche();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            Logger.getLogger(PersonneController.class.getName()).log(Level.SEVERE,null,sqlException);
        }
    }

    public void delete() {
        personne = table_personne.getSelectionModel().getSelectedItem();
//        String delete_query = "DELETE FROM personne WHERE id = ?";
        String delete_query = "DELETE FROM personne WHERE id = " + personne.getId();
        try {
            preparedStatement = connection.prepareStatement(delete_query);
            //preparedStatement.setInt(1,Integer.parseInt(id_col.getText()));
            preparedStatement.executeUpdate();
            affiche();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            Logger.getLogger(PersonneController.class.getName())
                    .log(Level.SEVERE, null,sqlException);
        }
    }

    private void update() {
        personne = table_personne.getSelectionModel().getSelectedItem();
        String query_update = "UPDATE personne SET nom =?, prenom  =?, age =?, date_naissance =?, sexe =?, poids =? where id="+personne.getId();
        try {
            preparedStatement = connection.prepareStatement(query_update);
            preparedStatement.setString(1,nom_field.getText());
            preparedStatement.setString(2,prenom_field.getText());
//            preparedStatement.setInt(3, Integer.parseInt(age_field.getText()));
            preparedStatement.setInt(3, Integer.parseInt(String.valueOf(age_field.getText())));
            preparedStatement.setDate(4, Date.valueOf(date_field.getValue()));
            preparedStatement.setString(5,sexe_field.getSelectionModel().getSelectedItem());
            preparedStatement.setString(6,poids_field.getText());

            preparedStatement.executeUpdate();
            affiche();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            Logger.getLogger(PersonneController.class.getName())
                    .log(Level.SEVERE, null, sqlException);
        }
    }

    void clear() {
        nom_field.clear();
        prenom_field.clear();
        date_field.getEditor().clear();
        age_field.clear();
        sexe_field.valueProperty().setValue(null);
        poids_field.clear();
        save_person_button.setDisable(false);
    }

    @FXML
    private void clearEvent(ActionEvent event) {
        clear();
    }

    @FXML
    private void updateEvent(ActionEvent event) {
        update();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("SUCCESS");
        alert.setContentText("PERSONNE MODIFIE AVEC SUCCES !");

        alert.showAndWait();
        clear();
        save_person_button.setDisable(false);
    }

    @FXML
    private void deleteEvent(ActionEvent event) {
        delete();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("SUCCESS");
        alert.setContentText("PERSONNE SUPPRIMER AVEC SUCCES !");

        alert.showAndWait();
        clear();
    }

    /*
    @FXML
    private void saveEvent(ActionEvent event) {
        insert();
        clear();
    }*/


}
