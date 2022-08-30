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
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.app.attila.model.Athlete;
import org.app.attila.util.DatabaseConnection;
import org.app.attila.util.SqliteConnection;
import org.app.attila.util.ValueRange;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AthleteController {

    @FXML
    private Button btn_enregistrer;

    @FXML
    private Button btn_import;

    @FXML
    private RadioButton radio_a;

    @FXML
    private RadioButton radio_b;

    @FXML
    private RadioButton radio_c;

    @FXML
    private RadioButton radio_gi;

    @FXML
    private RadioButton radio_nogi;

    @FXML
    private ComboBox<?> select_ceinture;

    @FXML
    private ComboBox<?> select_sexe;

    @FXML
    private TableView<Athlete> table_athlete;

    @FXML
    private TextField txtfield_club;

    @FXML
    private TextField txtfield_nom;

    @FXML
    private TextField txtfield_poids;

    @FXML
    private TextField txtfield_prenom;

    @FXML
    private Label label_fichier;

    @FXML
    private Button btn_retour;

    // TODO : Controller pour tab
    @FXML
    private Label label_psk;

    @FXML
    private TextField txt_field_pp;

    @FXML
    private Button btn_get_data;

    @FXML
    private Label label_athlete_age;

    @FXML
    private Label label_athlete_genre;

    @FXML
    private Label label_athlete_grade;

    @FXML
    private Label label_athlete_info;

    @FXML
    private Button btn_get_ath_data;

    @FXML
    private Button btn_goto_cat;

    Stage stage;
    Scene scene;
    Parent root;

    Athlete athlete = null;
    File file;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Connection connection = DatabaseConnection.getConnection();

    // Tab Categorie
    ObservableList<Athlete> athleteObservableList = FXCollections.observableArrayList();


    @FXML
    private void backToDashboard(ActionEvent event) throws IOException {
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

    @FXML
    private void goToCategorie(ActionEvent event) throws IOException {
        if (event.getSource() == btn_goto_cat){
            stage = (Stage) btn_goto_cat.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/fxml/categorie-view.fxml"));
        }
        Scene scene  = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /*
     *  Chargement fichier
     */
    public void loadFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("OUVRIR FICHIER");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel File", "*.xlsx"));
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            file = selectedFile;
            label_fichier.setText(file.toString());
            List<Athlete> athlete_data = readFile(file);
            athleteObservableList.addAll(athlete_data);
        }


        table_athlete.setItems(athleteObservableList);

        TableColumn<Athlete, Integer> col_numero = new TableColumn("NUMERO");
        TableColumn<Athlete, String> col_club = new TableColumn("CLUB");
        TableColumn<Athlete, String> col_nom = new TableColumn("NOM");
        TableColumn<Athlete, String> col_prenom = new TableColumn("PRENOM");
        TableColumn<Athlete, String> col_sexe = new TableColumn("GENRE");
        TableColumn<Athlete, String> col_age = new TableColumn("AGE");
        TableColumn<Athlete, String> col_ceinture = new TableColumn("CEINTURE");
        TableColumn<Athlete, String> col_poids = new TableColumn("POIDS");
        TableColumn<Athlete, String> col_gi = new TableColumn("GI");
        TableColumn<Athlete, String> col_nogi = new TableColumn("NOGI");


        col_numero.setCellValueFactory(new PropertyValueFactory("id"));
        col_club.setCellValueFactory(new PropertyValueFactory("club"));
        col_nom.setCellValueFactory(new PropertyValueFactory("nom"));
        col_prenom.setCellValueFactory(new PropertyValueFactory("prenom"));
        col_sexe.setCellValueFactory(new PropertyValueFactory("sexe"));
        col_age.setCellValueFactory(new PropertyValueFactory("age"));
        col_ceinture.setCellValueFactory(new PropertyValueFactory("ceinture"));
        col_poids.setCellValueFactory(new PropertyValueFactory("poids"));
        col_gi.setCellValueFactory(new PropertyValueFactory("combat_gi"));
        col_nogi.setCellValueFactory(new PropertyValueFactory("combat_nogi"));

        table_athlete.getColumns().setAll(
                col_numero,
                col_club,
                col_nom,
                col_prenom,
                col_sexe,
                col_age,
                col_ceinture,
                col_poids,
                col_gi,
                col_nogi
        );
    }

    /*
    *   Lire fichier excel & exract data
    */
    private List<Athlete> readFile(File file) {
        List<Athlete> athlete_list = new ArrayList();
        try {
            DataFormatter dataFormatter = new DataFormatter();
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i < sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                Athlete athlete1 = new Athlete();

                for (int j = 0; j < row.getLastCellNum(); j++) {
                    Cell cell = row.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    switch (j){
                        case 0:
                            if (cell != null){
                                athlete1.setId((int) cell.getNumericCellValue());
                                System.out.println("Data excel: " + cell.getNumericCellValue());
                                break;
                            }  else {
                                System.out.println("NULL");
                            }

                        case 1:
                            if (cell != null) {
                                athlete1.setClub(cell.getStringCellValue());
                                System.out.println("Data excel : " + cell.getStringCellValue());
                                break;
                            }  else {
                                System.out.println("NULL");
                            }
                        case 2:
                            athlete1.setNom(cell.getStringCellValue());
                            System.out.println("Data excel : " + cell.getStringCellValue());
                            break;
                        case 3:
                            athlete1.setPrenom(cell.getStringCellValue());
                            System.out.println("Data excel : " + cell.getStringCellValue());
                            break;
                        case 4:
                            athlete1.setSexe(cell.getStringCellValue());
                            System.out.println("Data excel : " + cell.getStringCellValue());
                            break;
                        case 5:
                            athlete1.setAge(cell.getStringCellValue());
                            System.out.println("Data excel : " + cell.getStringCellValue());
                            break;
                        case 6:
                            athlete1.setCeinture(cell.getStringCellValue());
                            System.out.println("Data excel: " + cell.getStringCellValue());
                            break;
                        case 7:
                            athlete1.setPoids(cell.getStringCellValue());
                            System.out.println("Data excel : " + cell.getStringCellValue());
                            break;
                        case 8:
                            if (cell != null) {
                                athlete1.setCombat_gi(cell.getStringCellValue());
                                System.out.println("Data excel : " + cell.getStringCellValue());
                                break;
                            } else {
                                System.out.println("NULL");
                            }
                        case 9:
                            if (cell != null) {
                                athlete1.setCombat_nogi(cell.getStringCellValue());
                                System.out.println("Data excel : " + cell.getStringCellValue());
                                break;
                            } else {
                                System.out.println("NULL");
                            }
                    }
                }
                athlete_list.add(athlete1);
            }

            if (athlete_list.add(athlete) == true){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("SUCCESS");
                alert.setContentText("Donnee ajouter");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR");
                alert.setContentText("Donnee non ajouter");
                alert.showAndWait();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        System.out.println("DATA SHOWING : " + athlete_list);
        return athlete_list;
    }

    /*
    *   Recuperation Poids
    */
    public void getAthleteInfo()  {
        if (table_athlete.getSelectionModel().getSelectedItem() != null) {
            Athlete athlete = table_athlete.getSelectionModel().getSelectedItem();
            label_psk.setText(athlete.getPoids());
            label_athlete_age.setText(athlete.getAge());
            label_athlete_genre.setText(athlete.getSexe());
            label_athlete_grade.setText(athlete.getCeinture());
        }
    }

    /*
    *   Verification Poids et assign categorie
    */
    public void verifInfo() {
        String poids_sans_kimono = label_psk.getText();
        if (label_psk.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNING");
            alert.setContentText("Veuillez entree la poids");
            alert.showAndWait();
        }
        String psk_int = poids_sans_kimono.replaceAll("[^0-9]","");
        int psk_int_val = Integer.parseInt(psk_int);

        String poids_pese =  txt_field_pp.getText();
        int pp_int_val = Integer.parseInt(poids_pese);

        table_athlete.getSelectionModel().getSelectedItem().setPoids(poids_pese + " Kg");

        if(true){
            table_athlete.refresh();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("SUCCESS");
            alert.setContentText("Donnee mis a jour");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNING");
            alert.setContentText("Donnee non mis a jour");
            alert.showAndWait();
        }

        // TODO : Test  pour assigner categorie

        // Parse age to int
        String age_athlete_string = label_athlete_age.getText().replaceAll("[^0-9]", "");
        int age_athlete_int = Integer.parseInt(age_athlete_string);

        System.out.println("ATHLETE AGE : " + age_athlete_int);

    /*
        if (range_enfant.valueInRange(age_athlete_int) &&
            label_athlete_genre.getText() == "Homme" &&
            label_athlete_grade.getText() == "BLANC") {
            label_athlete_info.setText("CATEGORIE ENFANT : " + poids_pese + " KG " + label_athlete_genre + " GRADE : " + label_athlete_grade);
        } else if (range_jeune.valueInRange(age_athlete_int)) {
            label_athlete_info.setText("CATEGORIE JEUNE");
        } else if (range_adulte.valueInRange(age_athlete_int)) {
            label_athlete_info.setText("CATEGORIE ADULTE");
        } else {
            label_athlete_info.setText("AUTRE CATEGORIE");
        }
    */
    }

   /*
   *    TableView to Database
   */
    public void saveAthlete() {
       Athlete athlete_selected = table_athlete.getSelectionModel().getSelectedItem();

       String query_athlete = "INSERT INTO `athlete` (club, nom, prenom, genre, age, ceinture, poids, gi, nogi) VALUES (?,?,?,?,?,?,?,?,?,?)";
       try {
           PreparedStatement preparedStatement = connection.prepareStatement(query_athlete);
           preparedStatement.setString(1,athlete_selected.getClub());
           preparedStatement.setString(2,athlete_selected.getNom());
           preparedStatement.setString(3,athlete_selected.getPrenom());
           preparedStatement.setString(4,athlete_selected.getSexe());
           preparedStatement.setString(5,athlete_selected.getAge());
           preparedStatement.setString(6,athlete_selected.getCeinture());
           preparedStatement.setString(7,athlete_selected.getPoids());
           preparedStatement.setString(8,athlete_selected.getCombat_gi());
           preparedStatement.setString(9,athlete_selected.getCombat_nogi());
           preparedStatement.setString(10,athlete_selected.getCombat_nogi());
           //preparedStatement.executeUpdate();
           if (preparedStatement.executeUpdate()  == 0) {
               Alert alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setTitle("WARNING");
               alert.setContentText("Donnee non  inserer, Veuillez Reessayer !!");
               alert.showAndWait();
           } else {
               Alert alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setTitle("INFORMATION");
               alert.setContentText("Donnee inserer !!");
               alert.showAndWait();
           }
       } catch (SQLException sqlException) {
           sqlException.printStackTrace();
       }
    }

    /*
    *   Get athlete data
    */
    public void getAthleteData() throws SQLException {
        String query_athlete_data = "SELECT * FROM athlete";

        try {
            PreparedStatement statement = connection.prepareStatement(query_athlete_data);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String club = rs.getString("club");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String genre = rs.getString("genre");
                String age = rs.getString("age");
                String ceinture = rs.getString("ceinture");
                String poids = rs.getString("poids");

                System.out.println("ATHLETE DATA :"  + club +" - "+ nom +" - "+ prenom +" - "+ genre +" - "+ age +" - "+ ceinture +" - "+ poids);
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

    }
}
