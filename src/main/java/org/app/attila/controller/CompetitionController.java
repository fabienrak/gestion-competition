package org.app.attila.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.app.attila.model.Competiteur;
import org.app.attila.model.Partenaire;
import org.app.attila.util.DatabaseConnection;
import org.app.attila.util.SqliteConnection;

import java.io.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CompetitionController {
    // TODO : controller pour la competition

    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Connection connection = DatabaseConnection.getConnection();

    @FXML
    private Button btn_retour;

    @FXML
    private Button btn_enregistrer;

    @FXML
    private TextField titre_field;

    @FXML
    private TextField desc_field;

    @FXML
    private TextField lieu_field;

    @FXML
    private DatePicker date_debut_field;

    @FXML
    private DatePicker date_fin_field;

    @FXML
    private TextField type_field;

    @FXML
    private TextField organisateur_field;

    @FXML
    private TableView<Competiteur>  table_data;

    @FXML
    private TableColumn<Competiteur, Integer> id_col;

    @FXML
    private TableColumn<Competiteur, String> nom_col;

    @FXML
    private TableColumn<Competiteur, String> prenom_col;

    @FXML
    private TableColumn<Competiteur, Integer> age_col;

    @FXML
    private TableColumn<Competiteur, String> sexe_col;

    @FXML
    private TableColumn<Competiteur, String> poids_col;

    @FXML
    private TableColumn<Competiteur, String> club_col;

    // TODO : Mettre dans partnenaire controller

    @FXML
    private Button btn_upload_logo;

    @FXML
    private Label logo_name;

    @FXML
    private TextField nom_part_field;

    @FXML
    private TextField contact_part_field;

    @FXML
    private Label logo_label;

    Stage stage;

    // TODO : Mettre dans pese controlleur

    @FXML
    private Label label_poids_donnee;

    @FXML
    private TextField field_poids_actuel;

    @FXML
    private Button btn_verif_poids;

    @FXML
    private Label label_info_poids;

    @FXML
    private Label label_info_nom;

    @FXML
    private Label label_info_prenom;

    @FXML
    private Label label_info_age;

    @FXML
    private Label label_info_sexe;

    @FXML
    private Label label_info_club;

    @FXML
    private void enregistrer_competition(ActionEvent event){
        insertCompetition();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("SUCCESS");
        alert.setContentText("COMPETITION ENREGISTRER AVEC SUCCES !");
        alert.showAndWait();
        clearForm();
    }

    public void insertCompetition(){
        String query_insert = "INSERT INTO competition (titre_competition, desc_competition, lieux_competition, date_debut, date_fin, type_competition, organisateur) VALUES (?,?,?,?,?,?,?)";
        if (validateForm()){
            try {
                preparedStatement = connection.prepareStatement(query_insert);
                preparedStatement.setString(1, titre_field.getText());
                preparedStatement.setString(2, desc_field.getText());
                preparedStatement.setString(3, lieu_field.getText());
                preparedStatement.setDate(4, Date.valueOf(date_debut_field.getValue()));
                preparedStatement.setDate(5, Date.valueOf(date_fin_field.getValue()));
                preparedStatement.setString(6,type_field.getText());
                preparedStatement.setString(7,organisateur_field.getText());
                preparedStatement.executeUpdate();


            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
                Logger.getLogger(CompetitionController.class.getName()).log(Level.SEVERE,null,sqlException);
            }
        }

    }

    void clearForm(){
        titre_field.clear();
        desc_field.clear();
        lieu_field.clear();
        date_debut_field.getEditor().clear();
        date_fin_field.getEditor().clear();
        type_field.clear();
        organisateur_field.clear();
    }

    private boolean validateForm(){
        if (titre_field.getText().isEmpty() ||
                desc_field.getText().isEmpty() ||
                lieu_field.getText().isEmpty() ||
//                date_debut_field.getEditor().getText() |
//                date_fin_field.getEditor().getText() |

                type_field.getText().isEmpty() ||
                organisateur_field.getText().isEmpty()
        ){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("INFORMATION");
            alert.setContentText("CHAMP VIDE !!");
            alert.showAndWait();
        }

        return true;
    }

    // TODO : Refactor , atokana fichier util ray

    @FXML
    private void competitionToDashboard(ActionEvent event) throws IOException {
        Stage stage = null;
        Parent root = null;

        if (event.getSource() == btn_retour){
            stage = (Stage) btn_retour.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/fxml/dashboard-view.fxml"));
        }
        Scene scene  = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // TODO : Mettre sur un controlleur | service pour tab partenaire & pesee

    public ObservableList<Competiteur> getCompetiteur() {
        ObservableList<Competiteur>  list_competiteur = FXCollections.observableArrayList();
        String query_competiteur = "SELECT * FROM competiteur";
        try {
            preparedStatement =  connection.prepareStatement(query_competiteur);
            resultSet  = preparedStatement.executeQuery();
            while (resultSet.next()){
                Competiteur competiteur  =  new Competiteur();
                competiteur.setId(resultSet.getInt("id"));
                competiteur.setNom(resultSet.getString("nom"));
                competiteur.setPrenom(resultSet.getString("prenom"));
                competiteur.setAge(resultSet.getInt("age"));
                competiteur.setPoids(resultSet.getString("poids"));
                competiteur.setSexe(resultSet.getString("sexe"));
                competiteur.setClub(resultSet.getString("club"));

                list_competiteur.add(competiteur);

                System.out.println("LISTE :  "+competiteur);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return list_competiteur;
    }

    // TODO : charge data pour tableview
    @FXML
    public  void chargerDonnee(){
        ObservableList<Competiteur> list_comp =  getCompetiteur();

        id_col.setCellValueFactory(new PropertyValueFactory<Competiteur, Integer>("id"));
        nom_col.setCellValueFactory(new PropertyValueFactory<Competiteur, String>("nom"));
        prenom_col.setCellValueFactory(new PropertyValueFactory<Competiteur, String>("prenom"));
        age_col.setCellValueFactory(new PropertyValueFactory<Competiteur, Integer>("age"));
        poids_col.setCellValueFactory(new PropertyValueFactory<Competiteur, String>("poids"));
        sexe_col.setCellValueFactory(new PropertyValueFactory<Competiteur, String>("sexe"));
        club_col.setCellValueFactory(new PropertyValueFactory<Competiteur, String>("club"));

        table_data.setItems(list_comp);
    }

    // TODO : Mettre dans controller Partenaire

    @FXML
    private void uploadLogo() throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extensionFilterJpg = new FileChooser.ExtensionFilter("IMAGE FILE (*.jpg)","*.jpg");
        FileChooser.ExtensionFilter extensionFilterPng = new FileChooser.ExtensionFilter("IMAGE FILE (*.png)","*.png");
        FileChooser.ExtensionFilter extensionFilterJpeg = new FileChooser.ExtensionFilter("IMAGE FILE (*.jpeg)","*.jpeg");

        fileChooser.getExtensionFilters().addAll(extensionFilterJpg,extensionFilterPng, extensionFilterJpeg);

        File logo_file = fileChooser.showOpenDialog(stage);
        logo_name.setText(logo_file.getName());
        System.out.println("IMAGE : "+logo_file.getName());

        FileInputStream fileInputStream = new FileInputStream(logo_file);

        String query_insert_part = "INSERT INTO partenaire (nom_partenaire, logo, contact) VALUES (?,?,?)";

        try {
            preparedStatement = connection.prepareStatement(query_insert_part);
            preparedStatement.setString(1, nom_part_field.getText());
            preparedStatement.setBinaryStream(2,(InputStream) fileInputStream, (int) logo_file.length());
            preparedStatement.setString(3, contact_part_field.getText());

            preparedStatement.execute();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            //Logger.getLogger()
        }

    }

    //  Affiche  partenaire
    public void affichePartenaire(){
        String  query_part = "SELECT * FROM partenaire";
        try {
            preparedStatement = connection.prepareStatement(query_part);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Partenaire partenaire = new Partenaire();
                partenaire.setNom_partenaire(resultSet.getString("nom_partenaire"));
                partenaire.setLogo(resultSet.getBytes("logo"));
                partenaire.setContact(resultSet.getString("contact"));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

//    public void insertPartenaire() {
//        String query_insert_part = "INSERT INTO partenaire (nom_partenaire, logo, contact) VALUES (?,?,?)";
//
//        try {
//            preparedStatement = connection.prepareStatement(query_insert_part);
//            preparedStatement.setString(1, nom_part_field.getText());
//            preparedStatement.setByte(2, );
//        } catch (SQLException sqlException) {
//            sqlException.printStackTrace();
//            //Logger.getLogger()
//        }
//    }
//

    // TODO : Mettre dans Controlleur pour pese

    public void getSelectedValueOnCell(){
        if (table_data.getSelectionModel().getSelectedItem() != null){
            Competiteur competiteur = table_data.getSelectionModel().getSelectedItem();
            label_info_nom.setText(competiteur.getNom());
            label_info_prenom.setText(competiteur.getPrenom());
            label_info_age.setText(competiteur.getAge() + " Ans");
            label_info_sexe.setText(competiteur.getSexe());
            label_info_club.setText(competiteur.getClub());
            label_poids_donnee.setText(competiteur.getPoids());
        }
    }

    // verif poids pese
    @FXML
    public void verifPoids(){
        String poids_donnee = label_poids_donnee.getText();
        //int poids = Integer.parseInt(poids_actuel);
        String poids_donnee_int = poids_donnee.replaceAll("[^0-9]", "");
        System.out.println("POIDS DONNEE : " + poids_donnee_int);
        int poids_donnee_int_val = Integer.parseInt(poids_donnee_int);

        String poids_actuel = field_poids_actuel.getText();
        int poids_actuel_int_val = Integer.parseInt(poids_actuel);

        //poids = poids_actuel_int_val;

        if (poids_donnee_int_val > poids_actuel_int_val){
            label_info_poids.setText("POIDS DONNEE PLUS GRAND");
            field_poids_actuel.clear();
        } else if (poids_actuel_int_val > poids_donnee_int_val){
            label_info_poids.setText("POIDS ACTUEL PLUS GRAND");
            field_poids_actuel.clear();
        }
        //return poids;
    }

    //  Assigner categorie
    private void assignerCategorie(){

    }
}
