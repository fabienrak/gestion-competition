package org.app.attila.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.concurrent.atomic.AtomicReference;

public class PoolController implements Initializable {

    // Tableview element
    @FXML
    private TableView<Athlete> table_data_categorized;

    @FXML
    private TableColumn<Athlete,String> COL_NOM;

    @FXML
    private TableColumn<Athlete,String> COL_PRENOM;

    @FXML
    private TableColumn<Athlete,String> COL_GENRE;

    @FXML
    private TableColumn<Athlete, String> COL_AGE;

    @FXML
    private TableColumn<Athlete, String> COL_CEINTURE;

    @FXML
    private TableColumn<Athlete, String> COL_POIDS;

    @FXML
    private TableColumn<Athlete, String> COL_CATEGORY;

    @FXML
    private TableColumn<Athlete, Integer> COL_ID;

    // Fin table view

    @FXML
    private Button liste_cat;

    @FXML
    private Button back_to_category;

    @FXML
    private ComboBox category_combobox;

    @FXML
    private Label label_temp_log;

    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Connection connection = DatabaseConnection.getConnection();
    Athlete athlete = new Athlete();
    Parent parent;
    Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        populateCombobox();
        //loadDataByCategory();
    }

    @FXML
    private void backToCategory(ActionEvent event) throws IOException {

        if (event.getSource() == back_to_category){
            stage = (Stage) back_to_category.getScene().getWindow();
            parent = FXMLLoader.load(getClass().getResource("/fxml/categorie-view.fxml"));
        }
        Scene scene  = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    /**
     *  Get liste category
     */
    public ArrayList<String> getListCategory() {
        ArrayList<String> list_all_category = new ArrayList();
        String query_category = "SELECT categorie FROM athlete";
        try {
            preparedStatement = connection.prepareStatement(query_category);
            resultSet = preparedStatement.executeQuery();
            String data = null;
            while (resultSet.next()) {
                data = resultSet.getString("categorie");
                list_all_category.add(data);
            }

//            for (int i = 0; i < list_all_category.size(); i++) {
//                System.out.println("XXXX : " + list_all_category.get(i));
//            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list_all_category;
    }

    /**
     *  Populate combobox
     */
    public void populateCombobox() {
        List<String> list_category = getListCategory();
        // Remove duplicates
        Set<String> list_all_category = new HashSet<>(list_category);
        // Remove null values
        list_all_category.removeAll(Collections.singleton(null));
        list_category.clear();
        list_category.addAll(list_all_category);
        category_combobox.getItems().addAll(list_all_category);
    }

    /**
     * Get data by categorie
     */
    public ObservableList<Athlete> getDataByCategory() {
        ObservableList<Athlete> list_all_athlete = FXCollections.observableArrayList();

        String category = (String) category_combobox.getValue();
        if (category == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Aucun categorie selectionner");
            alert.setContentText("Veuillez selectionner un categorie");
            alert.show();
        }
        String query_category = "SELECT * FROM athlete WHERE categorie=\'"+category+"\'";
        try {
            preparedStatement = connection.prepareStatement(query_category);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Athlete athlete_data = new Athlete();
                athlete_data.setId(resultSet.getInt("id"));
                athlete_data.setNom(resultSet.getString("nom"));
                athlete_data.setPrenom(resultSet.getString("prenom"));
                athlete_data.setSexe(resultSet.getString("genre"));
                athlete_data.setAge(resultSet.getString("age"));
                athlete_data.setCeinture(resultSet.getString("ceinture"));
                athlete_data.setPoids(resultSet.getString("poids"));
                athlete_data.setCategorie(resultSet.getString("categorie"));

                list_all_athlete.add(athlete_data);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list_all_athlete;
    }

    public void loadAthleteByCategory(){
        ObservableList<Athlete> list_athlete = getDataByCategory();
        COL_ID.setCellValueFactory(new PropertyValueFactory<Athlete,  Integer>("id"));
        COL_NOM.setCellValueFactory(new PropertyValueFactory<Athlete, String>("nom"));
        COL_PRENOM.setCellValueFactory(new PropertyValueFactory<Athlete, String>("prenom"));
        COL_GENRE.setCellValueFactory(new PropertyValueFactory<Athlete, String>("sexe"));
        COL_AGE.setCellValueFactory(new PropertyValueFactory<Athlete, String>("age"));
        COL_CEINTURE.setCellValueFactory(new PropertyValueFactory<Athlete, String>("ceinture"));
        COL_POIDS.setCellValueFactory(new PropertyValueFactory<Athlete, String>("poids"));
        COL_CATEGORY.setCellValueFactory(new PropertyValueFactory<Athlete, String>("categorie"));

        table_data_categorized.setItems(list_athlete);
    }

    /**
     * Creation pool
     */
    public void createPool() {
        table_data_categorized.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        ObservableList<Athlete> athlete_pool = table_data_categorized.getSelectionModel().getSelectedItems();

        ContextMenu menu = new ContextMenu();
        MenuItem menu_creer_pool = new MenuItem("CREER POOL");
        MenuItem menu_category = new MenuItem("SUPPRIMER");
        MenuItem menu_modif = new MenuItem("EXPORTER");

        menu.getItems().addAll(menu_creer_pool, menu_category, menu_modif);
        table_data_categorized.setContextMenu(menu);

        menu_creer_pool.setOnAction(event -> {
            TextInputDialog dialog_pool = new TextInputDialog();
            dialog_pool.setTitle("Creer Pool");
            dialog_pool.setHeaderText("Creer Pool ");
            dialog_pool.setContentText("Nom Pool");

            Optional<String> result_dialog = dialog_pool.showAndWait();

            result_dialog.ifPresent(nom -> {
                label_temp_log.setText(nom);
            });

            for (int i = 0; i < athlete_pool.size(); i++) {
                //System.out.println("BOUCLE: " + athlete_pool.get(i));

                String query_pool = "UPDATE athlete SET pool = \'" + label_temp_log.getText() + "\' WHERE id = " + athlete_pool.get(i).getId();
                System.out.println("========= QUERY =========== " + query_pool);
                try {
                    preparedStatement = connection.prepareStatement(query_pool);
                    preparedStatement.executeUpdate();
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("INFORMATION");
            alert.setContentText("Pool creer !!");
            alert.showAndWait();
            table_data_categorized.getItems().removeAll(athlete_pool);
        });
    }
}
