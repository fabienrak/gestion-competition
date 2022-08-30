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
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.app.attila.model.Competiteur;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class CompetiteurController {

    File file;

    @FXML
    private Button btn_upload;

    @FXML
    private Button btn_retour;

    @FXML
    private Label label_fichier;

    @FXML
    private TextField search_field;

    @FXML
    private Button btn_add_user;

    @FXML
    private TableView<Competiteur> table_data;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private TableColumn tb_col_club;
    private TableColumn tb_col_nom;
    private TableColumn tb_col_prenom;
    private TableColumn tb_col_age;
    private TableColumn tb_col_sexe;
    private TableColumn tb_col_poids;


    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    //Connection connection = DatabaseConnection.getConnection();

    Competiteur competiteur =  null;

    ObservableList<Competiteur> personObservableList = FXCollections.observableArrayList();

    public void loadFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("OUVRIR FICHIER");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel File","*.xlsx"));
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile !=  null){
            file = selectedFile;
            label_fichier.setText(file.toString());
            List<Competiteur> personList = readFile(file);
            personObservableList.addAll(personList);
        }

        table_data.setItems(personObservableList);
        TableColumn<Competiteur, Integer> tb_col_id = new TableColumn("NUMERO");
        TableColumn<Competiteur, String> tb_col_club = new TableColumn("CLUB");
        TableColumn<Competiteur, String> tb_col_nom = new TableColumn("NOM");
        TableColumn<Competiteur, String> tb_col_prenom = new TableColumn("PRENOM");
        TableColumn<Competiteur, Integer> tb_col_age = new TableColumn("AGE");
        TableColumn<Competiteur, String> tb_col_sexe = new TableColumn("SEXE");
        TableColumn<Competiteur, String> tb_col_poids = new TableColumn("POIDS");


        tb_col_id.setCellValueFactory(new PropertyValueFactory("id"));
        tb_col_nom.setCellValueFactory(new PropertyValueFactory("nom"));
        tb_col_prenom.setCellValueFactory(new PropertyValueFactory("prenom"));
        tb_col_age.setCellValueFactory(new PropertyValueFactory("age"));
        tb_col_sexe.setCellValueFactory(new PropertyValueFactory("sexe"));
        tb_col_poids.setCellValueFactory(new PropertyValueFactory("poids"));
        tb_col_club.setCellValueFactory(new PropertyValueFactory("club"));

        table_data.getColumns().setAll(tb_col_id, tb_col_nom, tb_col_prenom,tb_col_age, tb_col_sexe, tb_col_poids, tb_col_club );

    }

    @FXML
    private void showData(){
        for (Competiteur competiteur : table_data.getItems()) {
            String formated = String.format("%s %s (%s)",
                    competiteur.getNom(),
                    competiteur.getPrenom(),
                    competiteur.getClub(),
                    competiteur.getAge(),
                    competiteur.getSexe(),
                    competiteur.getPoids()
            );
            System.out.println("FORMATTED : "+formated);
        }
    }

    public void saveToDatabase() {

//        String data = column.getCellObservableValue()
    }

    /*
     *  TODO : Lire fichier excel et uploader dans la base de donnee
     */
    private List<Competiteur> readFile(File file) {

        List<Competiteur> personData = new ArrayList();
        try {

            DataFormatter dataFormatter = new DataFormatter();
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 0; i < sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                Competiteur person = new Competiteur();

                for (int j = 0; j < row.getLastCellNum(); j++) {
                    Cell cell = row.getCell(j);

                    switch (j) {
                        case 0:
                            Double id = cell.getNumericCellValue();
                            person.setId(id.intValue());
                            //person.setId();
                            System.out.println("ROW ID : " + i + " CELL ID: " + j + " - " + cell.getNumericCellValue());
                            break;
                        case 1:
                            person.setNom(cell.getStringCellValue());
                            //preparedStatement.setString(1,row.getCell(1).getStringCellValue());
                            System.out.println("ROW NOM : " + i + " CELL NOM: " + j + " - " + cell.getStringCellValue());
                            break;
                        case 2:
                            person.setPrenom(cell.getStringCellValue());
                            //preparedStatement.setString(2,row.getCell(2).getStringCellValue());
                            System.out.println("ROW PRENOM : " + i + " CELL PRENOM : " + j + " - " + cell.getStringCellValue());
                            break;
                        case 3:
                            Double age = cell.getNumericCellValue();
                            person.setAge(age.intValue());
                            //preparedStatement.setInt(3, (int) row.getCell(3).getNumericCellValue());
                            System.out.println("ROW AGE : " + i + " CELL AGE : " + j + " - " + cell.getNumericCellValue());
                            break;
                        case 4:
                            person.setSexe(cell.getStringCellValue());
                            //preparedStatement.setString(4, row.getCell(4).getStringCellValue());
                            System.out.println("ROW SEXE : " + i + " CELL SEXE : " + j + " - " + cell.getStringCellValue());
                            break;
                        case 5:
                            person.setPoids(cell.getStringCellValue());
                            //preparedStatggement.setString(5,row.getCell(5).getStringCellValue());
                            System.out.println("ROW POIDS : " + i + " CELL POIDS : " + j + " - " + cell.getStringCellValue());
                            break;
                        case 6:
                            person.setClub(cell.getStringCellValue());
                            //preparedStatement.setString(6,row.getCell(6).getStringCellValue());
                            System.out.println("ROW : CLUB " + i + " CELL : CLUB " + j + " - " + cell.getStringCellValue());
                            break;
//                        case 7:
//                            person.setDate_naissance(cell.getDateCellValue());
//                            if (DateUtil.isCellDateFormatted(cell)){
//                                System.out.println(cell.getDateCellValue());
//                            } else {
//                                System.out.println(cell.getNumericCellValue());
//                            }
//                            //System.out.println("ROW : " + i + " CELL : "+ j + " - "  + cell.getStringCellValue());
//                            break;
                    }
                    //preparedStatement.execute();
                }
                personData.add(person);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        return personData;
    }

    private boolean searchFindsOrder(Competiteur competiteur, String searchText){
        return (
                competiteur.getNom().toLowerCase().contains(searchText.toLowerCase()) ||
                competiteur.getPrenom().toLowerCase().contains(searchText.toLowerCase()) ||
                competiteur.getClub().toLowerCase().contains(searchText.toLowerCase())
        );
    }

    private ObservableList<Competiteur> filterList(List<Competiteur> listComp, String searchText){
        List<Competiteur> filteredList = new ArrayList<>();
        for (Competiteur competiteur : listComp){
            if (searchFindsOrder(competiteur, searchText)) filteredList.add(competiteur);
        }
        return FXCollections.observableList(filteredList);
    }

    private Predicate<Competiteur> createPredicate(String searchText){
        return competiteur -> {
            if (searchText  == null || searchText.isEmpty()) return true;
            return searchFindsOrder(competiteur, searchText);
        };
    }

    @FXML
    private void backToDashboard(ActionEvent event) throws IOException {

        if (event.getSource() == btn_retour){
            stage = (Stage) btn_retour.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/fxml/dashboard-view.fxml"));
        }
        Scene  scene  = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // Action bouton ajout athletes
    @FXML
    private void gotoAddUser(ActionEvent event) throws IOException {

        if (event.getSource() == btn_add_user){
            stage = (Stage) btn_add_user.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/fxml/personne-view.fxml"));
        }
        Scene scene  = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
