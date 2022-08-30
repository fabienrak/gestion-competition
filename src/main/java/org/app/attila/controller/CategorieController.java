package org.app.attila.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.app.attila.model.Athlete;
import org.app.attila.util.DatabaseConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CategorieController implements Initializable {

    @FXML
    private TableView<Athlete> table_data_athlete;

    @FXML
    private TableColumn<Athlete, String> COL_AGE;

    @FXML
    private TableColumn<Athlete, String> COL_CEINTURE;

    @FXML
    private TableColumn<Athlete, String> COL_CLUB;

    @FXML
    private TableColumn<Athlete, String> COL_GENRE;

    @FXML
    private TableColumn<Athlete, String> COL_GI;

    @FXML
    private TableColumn<Athlete, String> COL_ID;

    @FXML
    private TableColumn<Athlete, String> COL_NOGI;

    @FXML
    private TableColumn<Athlete, String> COL_NOM;

    @FXML
    private TableColumn<Athlete, String> COL_NUM;

    @FXML
    private TableColumn<Athlete, String> COL_POIDS;

    @FXML
    private TableColumn<Athlete, String> COL_PRENOM;

    @FXML
    private Button btn_load_data;

    @FXML
    private TextField search_field;

    // bouton tri

    @FXML
    private Button btn_creation_categorie;

    @FXML
    private Button btn_tri_poids;

    // Checkbox TRI

    @FXML
    private CheckBox check_age;

    @FXML
    private CheckBox check_genre;

    @FXML
    private CheckBox check_grade;

    @FXML
    private CheckBox check_poids;

    @FXML
    private ComboBox<String> combobox_age;

    @FXML
    private ComboBox<String> combobox_poids;

    @FXML
    private ComboBox<String> combobox_genre;

    @FXML
    private ComboBox<String> combobox_ceinture;

    @FXML
    private Button btn_debug;

    @FXML
    private Label label_nom_categorie;

    @FXML
    private Button go_to_match;

    @FXML
    private Button btn_retour;

    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Connection connection = DatabaseConnection.getConnection();
    Athlete athlete = new Athlete();
    Parent parent;
    Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        table_data_athlete.getColumns().addAll();

        combobox_age.setDisable(true);
        combobox_poids.setDisable(true);
        combobox_genre.setDisable(true);
        combobox_ceinture.setDisable(true);
        
        handleTriCheckBox();
        ObservableList<String> genreList = FXCollections.observableArrayList("Homme","Femme");
        combobox_genre.setItems(genreList);

        ObservableList<String> gradeList = FXCollections.observableArrayList("BLANCHE","BLEU","VIOLET","MARRON","NOIR","ROUGE");
        combobox_ceinture.setItems(gradeList);

        //table_data_athlete.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        //Bindings.isEmpty(table_data_athlete.getItems());

        /**
         * Retour
         */
        btn_retour.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (event.getSource() == btn_retour){
                    stage = (Stage) btn_retour.getScene().getWindow();
                    try {
                        parent = FXMLLoader.load(getClass().getResource("/fxml/athlete-view.fxml"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Scene scene  = new Scene(parent);
                stage.setScene(scene);
                stage.show();
            }
        });

        /**
         * Go to Match screen
         */
        go_to_match.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (event.getSource() == go_to_match){
                    stage = (Stage) go_to_match.getScene().getWindow();
                    try {
                        parent = FXMLLoader.load(getClass().getResource("/fxml/pool-view.fxml"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Scene scene  = new Scene(parent);
                stage.setScene(scene);
                stage.show();
            }
        });
    }

    /**
     *  Get athlete data from the database
     */
    public ObservableList<Athlete> getAthleteData() {
        ObservableList<Athlete> list_athlete = FXCollections.observableArrayList();
        String query_ath_data = "SELECT * FROM athlete";

        try {
            preparedStatement = connection.prepareStatement(query_ath_data);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Athlete athlete = new Athlete();
                athlete.setId(resultSet.getInt("id"));
                athlete.setClub(resultSet.getString("club"));
                athlete.setNom(resultSet.getString("nom"));
                athlete.setPrenom(resultSet.getString("prenom"));
                athlete.setSexe(resultSet.getString("genre"));
                athlete.setAge(resultSet.getString("age"));
                athlete.setCeinture(resultSet.getString("ceinture"));
                athlete.setPoids(resultSet.getString("poids"));
                athlete.setCombat_gi(resultSet.getString("gi"));
                athlete.setCombat_nogi(resultSet.getString("nogi"));
                athlete.setID_ATH(resultSet.getString("ID_ATH"));

                list_athlete.add(athlete);

                System.out.println("athlete" + list_athlete);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return list_athlete;
    }

    /**
     * Load data to tableview
     */
    public void loadAthleteData() {
        ObservableList<Athlete> list_athlete = getAthleteData();

        COL_NUM.setCellValueFactory(new PropertyValueFactory<Athlete, String>("id"));
        COL_CLUB.setCellValueFactory(new PropertyValueFactory<Athlete, String>("club"));
        COL_NOM.setCellValueFactory(new PropertyValueFactory<Athlete, String>("nom"));
        COL_PRENOM.setCellValueFactory(new PropertyValueFactory<Athlete, String>("prenom"));
        COL_GENRE.setCellValueFactory(new PropertyValueFactory<Athlete, String>("sexe"));
        COL_AGE.setCellValueFactory(new PropertyValueFactory<Athlete, String>("age"));
        COL_CEINTURE.setCellValueFactory(new PropertyValueFactory<Athlete, String>("ceinture"));
        COL_POIDS.setCellValueFactory(new PropertyValueFactory<Athlete, String>("poids"));
        COL_GI.setCellValueFactory(new PropertyValueFactory<Athlete, String>("combat_gi"));
        COL_NOGI.setCellValueFactory(new PropertyValueFactory<Athlete, String>("combat_nogi"));
        COL_ID.setCellValueFactory(new PropertyValueFactory<Athlete, String>("ID_ATH"));

        table_data_athlete.setItems(list_athlete);

        FilteredList<Athlete> athlete_data = new FilteredList<>(list_athlete, b -> true);
        search_field.textProperty().addListener((observable, oldValue, newValue) -> {
            athlete_data.setPredicate(list_athlete_data -> {
                if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                    return true;
                }

                String keyword = newValue.toLowerCase();

                if(list_athlete_data.getNom().toLowerCase().indexOf(keyword) > -1) {
                    return true;
                } else if(list_athlete_data.getPrenom().toLowerCase().indexOf(keyword) > -1) {
                    return true;
                } else if(list_athlete_data.getClub().toLowerCase().indexOf(keyword) > -1) {
                    return true;
                } else if(list_athlete_data.getID_ATH().toLowerCase().indexOf(keyword) > -1) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        populateCombobox();

        SortedList<Athlete> sorted_list = new SortedList<>(athlete_data);
        sorted_list.comparatorProperty().bind(table_data_athlete.comparatorProperty());

    }

    /**
     *  Tri par age
     */
    public ObservableList<Athlete> triParAge() {
        ObservableList<Athlete> list_athlete_par_age = FXCollections.observableArrayList();
        Athlete  athlete1 =  table_data_athlete.getSelectionModel().getSelectedItem();

        String ageTaloha = athlete1.getAge().replaceAll("[^0-9]","");
        int ageVaovao =  Integer.parseInt(ageTaloha);

        String query_tri_age = "SELECT * FROM athlete WHERE age="+ ageVaovao;

        try {
            preparedStatement = connection.prepareStatement(query_tri_age);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Athlete athlete = new Athlete();
                athlete.setId(resultSet.getInt("id"));
                athlete.setClub(resultSet.getString("club"));
                athlete.setNom(resultSet.getString("nom"));
                athlete.setPrenom(resultSet.getString("prenom"));
                athlete.setSexe(resultSet.getString("genre"));
                athlete.setAge(resultSet.getString("age"));
                athlete.setCeinture(resultSet.getString("ceinture"));
                athlete.setPoids(resultSet.getString("poids"));
                athlete.setCombat_gi(resultSet.getString("gi"));
                athlete.setCombat_nogi(resultSet.getString("nogi"));
                athlete.setID_ATH(resultSet.getString("ID_ATH"));

                list_athlete_par_age.add(athlete);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return list_athlete_par_age;
    }

    @FXML
    public void loadAthleteDataByAge() {
        ObservableList<Athlete> list_athlete_age = triParAge();

        COL_NUM.setCellValueFactory(new PropertyValueFactory<Athlete, String>("id"));
        COL_CLUB.setCellValueFactory(new PropertyValueFactory<Athlete, String>("club"));
        COL_NOM.setCellValueFactory(new PropertyValueFactory<Athlete, String>("nom"));
        COL_PRENOM.setCellValueFactory(new PropertyValueFactory<Athlete, String>("prenom"));
        COL_GENRE.setCellValueFactory(new PropertyValueFactory<Athlete, String>("sexe"));
        COL_AGE.setCellValueFactory(new PropertyValueFactory<Athlete, String>("age"));
        COL_CEINTURE.setCellValueFactory(new PropertyValueFactory<Athlete, String>("ceinture"));
        COL_POIDS.setCellValueFactory(new PropertyValueFactory<Athlete, String>("poids"));
        COL_GI.setCellValueFactory(new PropertyValueFactory<Athlete, String>("combat_gi"));
        COL_NOGI.setCellValueFactory(new PropertyValueFactory<Athlete, String>("combat_nogi"));
        COL_ID.setCellValueFactory(new PropertyValueFactory<Athlete, String>("ID_ATH"));

        table_data_athlete.setItems(list_athlete_age);
    }

    /**
     *  Tri par Poids
     */
    public ObservableList<Athlete> triParPoids() {
        ObservableList<Athlete> list_athlete_par_poids = FXCollections.observableArrayList();
        Athlete  athlete1 =  table_data_athlete.getSelectionModel().getSelectedItem();
        String poids_string = athlete1.getPoids().replaceAll("[^0-9]","");
        int poids_int =  Integer.parseInt(poids_string);

        String query_tri_age = "SELECT * FROM athlete WHERE poids="+ poids_int;

        try {
            preparedStatement = connection.prepareStatement(query_tri_age);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Athlete athlete = new Athlete();
                athlete.setId(resultSet.getInt("id"));
                athlete.setClub(resultSet.getString("club"));
                athlete.setNom(resultSet.getString("nom"));
                athlete.setPrenom(resultSet.getString("prenom"));
                athlete.setSexe(resultSet.getString("genre"));
                athlete.setAge(resultSet.getString("age"));
                athlete.setCeinture(resultSet.getString("ceinture"));
                athlete.setPoids(resultSet.getString("poids"));
                athlete.setCombat_gi(resultSet.getString("gi"));
                athlete.setCombat_nogi(resultSet.getString("nogi"));
                athlete.setID_ATH(resultSet.getString("ID_ATH"));

                list_athlete_par_poids.add(athlete);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return list_athlete_par_poids;
    }

    @FXML
    public void loadAthleteDataByPoids() {
        ObservableList<Athlete> list_athlete_poids = triParPoids();

        COL_NUM.setCellValueFactory(new PropertyValueFactory<Athlete, String>("id"));
        COL_CLUB.setCellValueFactory(new PropertyValueFactory<Athlete, String>("club"));
        COL_NOM.setCellValueFactory(new PropertyValueFactory<Athlete, String>("nom"));
        COL_PRENOM.setCellValueFactory(new PropertyValueFactory<Athlete, String>("prenom"));
        COL_GENRE.setCellValueFactory(new PropertyValueFactory<Athlete, String>("sexe"));
        COL_AGE.setCellValueFactory(new PropertyValueFactory<Athlete, String>("age"));
        COL_CEINTURE.setCellValueFactory(new PropertyValueFactory<Athlete, String>("ceinture"));
        COL_POIDS.setCellValueFactory(new PropertyValueFactory<Athlete, String>("poids"));
        COL_GI.setCellValueFactory(new PropertyValueFactory<Athlete, String>("combat_gi"));
        COL_NOGI.setCellValueFactory(new PropertyValueFactory<Athlete, String>("combat_nogi"));
        COL_ID.setCellValueFactory(new PropertyValueFactory<Athlete, String>("ID_ATH"));

        table_data_athlete.setItems(list_athlete_poids);
    }

    /**
     *  Tri par  genre
     */
    public ObservableList<Athlete> triParGenre()  {
        ObservableList<Athlete> list_athlete_par_genre = FXCollections.observableArrayList();
        String genre_selected = combobox_genre.getValue();

        String query_genre = "SELECT * FROM athlete WHERE genre=\'" + genre_selected +"\'";
        try {
            preparedStatement = connection.prepareStatement(query_genre);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.isBeforeFirst()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Pas de donner");
                alert.setContentText("AUCUNE DONNEE TROUVER");
                alert.show();
            } else {
                while (resultSet.next()) {
                    Athlete athlete_genre = new Athlete();
                    athlete_genre.setId(resultSet.getInt("id"));
                    athlete_genre.setClub(resultSet.getString("club"));
                    athlete_genre.setNom(resultSet.getString("nom"));
                    athlete_genre.setPrenom(resultSet.getString("prenom"));
                    athlete_genre.setSexe(resultSet.getString("genre"));
                    athlete_genre.setAge(resultSet.getString("age"));
                    athlete_genre.setCeinture(resultSet.getString("ceinture"));
                    athlete_genre.setPoids(resultSet.getString("poids"));
                    athlete_genre.setCombat_gi(resultSet.getString("gi"));
                    athlete_genre.setCombat_nogi(resultSet.getString("nogi"));
                    athlete_genre.setID_ATH(resultSet.getString("ID_ATH"));

                    list_athlete_par_genre.add(athlete_genre);
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return list_athlete_par_genre;
    }

    @FXML
    public void loadAthleteDataByGenre() {
        ObservableList<Athlete> list_athlete_genre = triParGenre();

        COL_NUM.setCellValueFactory(new PropertyValueFactory<Athlete, String>("id"));
        COL_CLUB.setCellValueFactory(new PropertyValueFactory<Athlete, String>("club"));
        COL_NOM.setCellValueFactory(new PropertyValueFactory<Athlete, String>("nom"));
        COL_PRENOM.setCellValueFactory(new PropertyValueFactory<Athlete, String>("prenom"));
        COL_GENRE.setCellValueFactory(new PropertyValueFactory<Athlete, String>("sexe"));
        COL_AGE.setCellValueFactory(new PropertyValueFactory<Athlete, String>("age"));
        COL_CEINTURE.setCellValueFactory(new PropertyValueFactory<Athlete, String>("ceinture"));
        COL_POIDS.setCellValueFactory(new PropertyValueFactory<Athlete, String>("poids"));
        COL_GI.setCellValueFactory(new PropertyValueFactory<Athlete, String>("combat_gi"));
        COL_NOGI.setCellValueFactory(new PropertyValueFactory<Athlete, String>("combat_nogi"));
        COL_ID.setCellValueFactory(new PropertyValueFactory<Athlete, String>("ID_ATH"));

        table_data_athlete.setItems(list_athlete_genre);
    }

    /**
     *  handle combobox tri
     */
    public void handleTriCheckBox() {
        check_age.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean notChecked, Boolean checked) {
                if (checked){
                    //loadAthleteDataByAge();
                    combobox_age.setDisable(false);
                } else {
                    //loadAthleteData();
                    combobox_age.setDisable(true);
                }
            }
        });
        check_poids.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean notChecked, Boolean checked) {
                if(checked){
                    //loadAthleteDataByPoids();
                    combobox_poids.setDisable(false);
                } else {
                    //loadAthleteData();
                    combobox_poids.setDisable(true);
                }
            }
        });
        check_genre.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean notChecked, Boolean checked) {
                if(checked){
                    combobox_genre.setDisable(false);
                } else {
                    combobox_genre.setDisable(true);
                }
            }
        });
        check_grade.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean notChecked, Boolean checked) {
                if(checked){
                    combobox_ceinture.setDisable(false);
                } else {
                    combobox_ceinture.setDisable(true);
                }
            }
        });
    }

    /**
     * Populate combobox from tableview
     */
    public void populateCombobox() {

        ObservableList<String> poids_value = FXCollections.observableArrayList();
        ObservableList<String> age_value = FXCollections.observableArrayList();
//        ObservableList<String> genres_value = FXCollections.observableArrayList();
//        ObservableList<String> ceinture_value = FXCollections.observableArrayList();

        for (Athlete athlete_poids : table_data_athlete.getItems()) {
            poids_value.add(COL_POIDS.getCellObservableValue(athlete_poids).getValue());
        }

        for (Athlete athlete_age : table_data_athlete.getItems()) {
            age_value.add(COL_AGE.getCellObservableValue(athlete_age).getValue());
        }

//        for (Athlete athlete_genre : table_data_athlete.getItems()) {
//            genres_value.add(COL_GENRE.getCellObservableValue(athlete_genre).getValue());
//        }
//
//        for (Athlete athlete_ceinture : table_data_athlete.getItems()) {
//            ceinture_value.add(COL_CEINTURE.getCellObservableValue(athlete_ceinture).getValue());
//        }

        combobox_age.setItems(age_value);
        combobox_poids.setItems(poids_value);

    }

    /**
     * Filtre athlete
     */
    public ObservableList<Athlete> trier() {
        ObservableList<Athlete> list_athlete_tri = FXCollections.observableArrayList();

        String query = null;

        // Par Age
        combobox_age.getValue();
        String ageTaloha = combobox_age.getValue();
        String query_tri_age = "SELECT * FROM athlete WHERE age=\'"+ ageTaloha+"\'";

        // Par Poids
        String poids_string = combobox_poids.getValue();
        String query_tri_poids = "SELECT * FROM athlete WHERE poids=\'"+ poids_string+"\'";

        // Par poids et age
        String age = combobox_age.getValue();
        String poids = combobox_poids.getValue();
        String query_poids_age = "SELECT * FROM athlete WHERE age=\'" + age + "\' AND poids=\'" + poids + "\'";

        // Par genre
        String genre = combobox_genre.getValue();
        String query_genre = "SELECT * FROM athlete WHERE genre=\'"+ genre + "\'";

        // Par grade
        String grade = combobox_ceinture.getValue();
        String query_grade = "SELECT * FROM athlete WHERE ceinture=\'"+ grade + "\'";

        System.out.println("CHECKED VE :" +check_age.isSelected());
        System.out.println("CHECKED VE 2:" +check_poids.isSelected());

        if (check_poids.isSelected()) {
            query = query_tri_poids;
        } else if (check_age.isSelected()) {
            query = query_tri_age;
        } else if (check_genre.isSelected()) {
            query = query_genre;
        } else if (check_grade.isSelected()) {
            query = query_grade;
        } else if (check_age.isSelected() == true && check_poids.isSelected() == true) {
            query = query_poids_age;
        }

        try {
            System.out.println("-------------------------------- query : " + query);
            if(query == null){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Pas de donner");
                alert.setContentText("VEUILLEZ CHARGER LES DONNEE");
                alert.show();
            } else {
                preparedStatement = connection.prepareStatement(query);
                resultSet = preparedStatement.executeQuery();

                if (!resultSet.isBeforeFirst()){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Pas de donner");
                    alert.setContentText("AUCUNE DONNEE TROUVER");
                    alert.show();
                    loadAthleteData();
                } else {
                    while (resultSet.next()) {
                        Athlete athlete_tri = new Athlete();
                        athlete_tri.setId(resultSet.getInt("id"));
                        athlete_tri.setClub(resultSet.getString("club"));
                        athlete_tri.setNom(resultSet.getString("nom"));
                        athlete_tri.setPrenom(resultSet.getString("prenom"));
                        athlete_tri.setSexe(resultSet.getString("genre"));
                        athlete_tri.setAge(resultSet.getString("age"));
                        athlete_tri.setCeinture(resultSet.getString("ceinture"));
                        athlete_tri.setPoids(resultSet.getString("poids"));
                        athlete_tri.setCombat_gi(resultSet.getString("gi"));
                        athlete_tri.setCombat_nogi(resultSet.getString("nogi"));
                        athlete_tri.setID_ATH(resultSet.getString("ID_ATH"));

                        list_athlete_tri.add(athlete_tri);
                    }
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.out.println("ERROR");
        }
        return list_athlete_tri;
    }

    @FXML
    public void loadTriData() {

        ObservableList<Athlete> list_tri_athlete = trier();

        COL_NUM.setCellValueFactory(new PropertyValueFactory<Athlete, String>("id"));
        COL_CLUB.setCellValueFactory(new PropertyValueFactory<Athlete, String>("club"));
        COL_NOM.setCellValueFactory(new PropertyValueFactory<Athlete, String>("nom"));
        COL_PRENOM.setCellValueFactory(new PropertyValueFactory<Athlete, String>("prenom"));
        COL_GENRE.setCellValueFactory(new PropertyValueFactory<Athlete, String>("sexe"));
        COL_AGE.setCellValueFactory(new PropertyValueFactory<Athlete, String>("age"));
        COL_CEINTURE.setCellValueFactory(new PropertyValueFactory<Athlete, String>("ceinture"));
        COL_POIDS.setCellValueFactory(new PropertyValueFactory<Athlete, String>("poids"));
        COL_GI.setCellValueFactory(new PropertyValueFactory<Athlete, String>("combat_gi"));
        COL_NOGI.setCellValueFactory(new PropertyValueFactory<Athlete, String>("combat_nogi"));
        COL_ID.setCellValueFactory(new PropertyValueFactory<Athlete, String>("ID_ATH"));

        table_data_athlete.setItems(list_tri_athlete);
    }

    /**
     *  Get Multiple value
     */
    public void getMulipleValue() {
        table_data_athlete.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        ObservableList<Athlete> data_selected = table_data_athlete.getSelectionModel().getSelectedItems();

        // Menu Click droite
        ContextMenu menu = new ContextMenu();
        MenuItem menu_creer = new MenuItem("CREER CATEGORIE");
        MenuItem menu_category = new MenuItem("SUPPRIMER");
        MenuItem menu_modif = new MenuItem("EXPORTER");

        menu.getItems().addAll(menu_creer, menu_category, menu_modif);
        table_data_athlete.setContextMenu(menu);

        System.out.println("Table data value : " + data_selected);

        menu_creer.setOnAction(event1 -> {

            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Categorie");
            dialog.setHeaderText("Nom Categorie : ");
            dialog.setContentText("Nom Categorie");

            Optional<String> result = dialog.showAndWait();

            result.ifPresent(nom -> {
                label_nom_categorie.setText(nom);
            });

            for (int i = 0; i < data_selected.size(); i++) {
                String  query = "UPDATE athlete SET categorie = \'" + label_nom_categorie.getText() + "\' WHERE id = " + data_selected.get(i).getId();
                System.out.println("========= QUERY =========== " + query);
                try {
                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.executeUpdate();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("INFORMATION");
            alert.setContentText("Categorie creer !!");
            alert.showAndWait();
            table_data_athlete.getItems().removeAll(data_selected);
        });
    }
}
