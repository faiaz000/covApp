/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Reusables.SharedData;
import Reusables.PatientData;
import EntityClasses.Outcome;
import EntityClasses.Patient;
import com.jfoenix.controls.JFXButton;
import covapp.SceneChanger;
import java.io.IOException;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.table.TableFilter;

/**
 * FXML Controller class
 *
 * @author Faiaz Sharaf Uddin
 */
public class OutcomeController implements Initializable {

    /**
     * Initializes the controller class.
     */
    final EntityManagerFactory emf = Persistence.createEntityManagerFactory("CovAppPU");
    final EntityManager entityManager = emf.createEntityManager();
    private Integer selectedPatientId;
    private String role;
    private Patient selectedPatient;
    private Outcome outcome;
    @FXML
    private CheckBox ards_checkbox;

    @FXML
    private Label ards_label;
    @FXML
    private TableView<Outcome> tableView;
    @FXML
    private CheckBox pneumonia_checkbox;

    @FXML
    private CheckBox cardiac_arrest_checkbox;

    @FXML
    private CheckBox secondary_infection_checkbox;

    @FXML
    private CheckBox stroke_checkbox;

    @FXML
    private CheckBox heart_attack_checkbox;

    @FXML
    private Label pneumonia_label;

    @FXML
    private Label cardiac_arrest_label;

    @FXML
    private Label secondary_infection_label;

    @FXML
    private Label stroke_label;

    @FXML
    private Label heart_attack_label;

    @FXML
    private JFXButton save_button;

    @FXML
    private JFXButton edit_button;

    @FXML
    private Label user_name;

    @FXML
    private Button logout;

    @FXML
    private Button back_button;

    @FXML
    private TableColumn<Outcome, Integer> t_patientId;

    @FXML
    private TableColumn<Outcome, String> t_ards;

    @FXML
    private TableColumn<Outcome, String> t_pneuomonia;

    @FXML
    private TableColumn<Outcome, String> t_cardiacArrest;

    @FXML
    private TableColumn<Outcome, String> t_sInfection;

    @FXML
    private TableColumn<Outcome, String> t_stroke;

    @FXML
    private TableColumn<Outcome, String> t_heartAttack;

    @FXML
    private TableColumn<Outcome, String> t_date;

    @FXML
    void onBack(ActionEvent event) throws IOException {
        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene(event, "patientinfo.fxml", role + " Panel");
    }

    @FXML
    void onLogOut(ActionEvent event) throws IOException {
        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene(event, "Main.fxml", "Main");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        edit_button.setDisable(true);
        edit_button.setVisible(false);
        outcomeTable();

        user_name.setText(SharedData.getUser());
        role = SharedData.getRole();
        selectedPatientId = PatientData.getD_Id();
        if (selectedPatientId != null) {
            //query data 
            getUpdatedTable();
            @SuppressWarnings("unchecked")
            TableFilter<Outcome> tf=TableFilter.forTableView(tableView).apply();
        }
        if (role.matches("nurse")) {
            // symptoms.setText("Add Symptoms"); 
            edit_button.setDisable(true);
            edit_button.setVisible(false);
            save_button.setVisible(false);
            save_button.setDisable(true);
        }
    }

    private void outcomeTable() {

        t_patientId.setCellValueFactory(cellData -> new SimpleObjectProperty<Integer>(
                cellData.getValue().getPatientId().getId()
        ));
        t_patientId.setStyle("-fx-alignment: CENTER");
        t_ards.setCellValueFactory(cellData -> {
            boolean ardsValue = cellData.getValue().getArds();
            String ardsAsString = ardsValue ? "Yes" : "No";
            return new ReadOnlyStringWrapper(ardsAsString);
        });
        t_ards.setStyle("-fx-alignment: CENTER");
        t_cardiacArrest.setCellValueFactory(cellData -> {
            boolean cardiacArrestValue = cellData.getValue().getCardiacArrest();
            String cardiacAsString = cardiacArrestValue ? "Yes" : "No";
            return new ReadOnlyStringWrapper(cardiacAsString);
        });
        t_cardiacArrest.setStyle("-fx-alignment: CENTER");
        t_heartAttack.setCellValueFactory(cellData -> {
            boolean heartAttackValue = cellData.getValue().getHeartAttack();
            String heartAttackAsString = heartAttackValue ? "Yes" : "No";
            return new ReadOnlyStringWrapper(heartAttackAsString);
        });
        t_heartAttack.setStyle("-fx-alignment: CENTER");
        t_pneuomonia.setCellValueFactory(cellData -> {
            boolean pneumoniaValue = cellData.getValue().getPneumonia();
            String pneumoniaAsString = pneumoniaValue ? "Yes" : "No";
            return new ReadOnlyStringWrapper(pneumoniaAsString);
        });
        t_pneuomonia.setStyle("-fx-alignment: CENTER");
        t_sInfection.setCellValueFactory(cellData -> {
            boolean sInfectionValue = cellData.getValue().getSecondaryInfection();
            String sInfectionAsString = sInfectionValue ? "Yes" : "No";
            return new ReadOnlyStringWrapper(sInfectionAsString);
        });
        t_sInfection.setStyle("-fx-alignment: CENTER");
        t_stroke.setCellValueFactory(cellData -> {
            boolean strokeValue = cellData.getValue().getStroke();
            String strokeAsString = strokeValue ? "Yes" : "No";
            return new ReadOnlyStringWrapper(strokeAsString);
        });
        t_stroke.setStyle("-fx-alignment: CENTER");
        t_date.setCellValueFactory(cellData -> {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            return new ReadOnlyStringWrapper(formatter.format(cellData.getValue().getDate()));
        });
        t_date.setStyle("-fx-alignment: CENTER");

    }

    private void getUpdatedTable() {
        List<Outcome> selectedOutcomesList = getOutcome();
        if (selectedOutcomesList.size() > 0) {
            tableView.setItems(FXCollections.observableList(selectedOutcomesList));
        }
    }

    private void setOutcomeFields(Outcome dbOutcome) {

        ards_checkbox.setSelected(dbOutcome.getArds());
        pneumonia_checkbox.setSelected(dbOutcome.getPneumonia());
        heart_attack_checkbox.setSelected(dbOutcome.getHeartAttack());
        secondary_infection_checkbox.setSelected(dbOutcome.getSecondaryInfection());
        stroke_checkbox.setSelected(dbOutcome.getStroke());
        cardiac_arrest_checkbox.setSelected(dbOutcome.getCardiacArrest());
    }

    @FXML
    private void onSave(ActionEvent event) throws IOException, ParseException {
        boolean success = checkConfirmation();
        if (success) {
            setOutcome();
             try {
                SharedData sharedData = new SharedData();
                sharedData.setModal("Outcome");
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Views/outcomedialog.fxml"));

                Pane confirmPane = fxmlLoader.load();

                Dialog dialog = new Dialog();
                dialog.setDialogPane((DialogPane) confirmPane);
                dialog.getDialogPane().setStyle(" -fx-font-weight: bold");

                Optional<ButtonType> selection = dialog.showAndWait();
                if (selection.get() == ButtonType.YES) {
                    entityManager.getTransaction().begin();
                    entityManager.persist(outcome);
                    entityManager.getTransaction().commit();
                    getUpdatedTable();
                    //entityManager.close();
                }

            } catch (Exception e) {
            }
          /*  entityManager.getTransaction().begin();
            entityManager.persist(outcome);
            entityManager.getTransaction().commit();
            getUpdatedTable();*/
        }
    }

    private boolean checkConfirmation() {
        boolean success = true;
        if (!ards_checkbox.isSelected() && !cardiac_arrest_checkbox.isSelected() && !pneumonia_checkbox.isSelected()
                && !secondary_infection_checkbox.isSelected() && !heart_attack_checkbox.isSelected()
                && !stroke_checkbox.isSelected()) {
            success = false;
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Patient's Outcome");
            alert.setContentText("Patient doesn't have any defined ourcomes. Are you sure you want to save Data");

            Optional<ButtonType> selection = alert.showAndWait();

            if (selection.get() == ButtonType.YES || selection.get() == ButtonType.OK) {
                success = true;
            } else if (selection.get() == ButtonType.CANCEL || selection.get() == ButtonType.NO) {
                success = false;
            }
        }
        return success;
    }

    private void setOutcome() throws ParseException {

        Date in = new Date();
        LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());
        Date presentDate = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
        outcome = new Outcome();
        outcome.setArds(ards_checkbox.isSelected());
        outcome.setCardiacArrest(cardiac_arrest_checkbox.isSelected());
        outcome.setHeartAttack(heart_attack_checkbox.isSelected());
        outcome.setPneumonia(pneumonia_checkbox.isSelected());
        outcome.setSecondaryInfection(secondary_infection_checkbox.isSelected());
        outcome.setStroke(stroke_checkbox.isSelected());
   
        Patient patient = entityManager.find(Patient.class, selectedPatientId);
        outcome.setPatientId(patient);
        outcome.setDate(presentDate);
        
         StatusObject statusOutcome = new StatusObject();
         statusOutcome.setPatientOutcome(outcome);
          
    }

    private void previousScene(ActionEvent event) throws IOException {

        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene(event, "patientinfo.fxml", "Patient Info");

    }

    private List<Outcome> getOutcome() {

        Query query = entityManager.createNativeQuery("Select * from outcome WHERE patient_id=?", Outcome.class);
        query.setParameter(1, selectedPatientId);

        @SuppressWarnings("unchecked")// To Stop Warning for the List object type
        List<Outcome> result = query.getResultList();
    
        return result;
    }

    @FXML
    private void getSelectedRow(MouseEvent event) {

        Outcome tableRow = (Outcome) tableView.getSelectionModel().getSelectedItem();
        ards_checkbox.setSelected(tableRow.getArds());
        pneumonia_checkbox.setSelected(tableRow.getPneumonia());
        heart_attack_checkbox.setSelected(tableRow.getHeartAttack());
        secondary_infection_checkbox.setSelected(tableRow.getSecondaryInfection());
        cardiac_arrest_checkbox.setSelected(tableRow.getCardiacArrest());
        stroke_checkbox.setSelected(tableRow.getStroke());

        if (role.matches("doctor")) {
            enableEditButton();
        };

    }

  
    private void enableEditButton() {
        edit_button.setVisible(true);
        edit_button.setDisable(false);
        save_button.setDisable(true);
        save_button.setVisible(false);
    }

    private void enableSaveButton() {
        edit_button.setVisible(false);
        edit_button.setDisable(true);
        save_button.setDisable(false);
        save_button.setVisible(true);
    }

    @FXML
    private void clearSelection(ActionEvent event) {

        tableView.getSelectionModel().clearSelection();
        ards_checkbox.setSelected(false);
        pneumonia_checkbox.setSelected(false);
        stroke_checkbox.setSelected(false);
        cardiac_arrest_checkbox.setSelected(false);
        heart_attack_checkbox.setSelected(false);
        secondary_infection_checkbox.setSelected(false);
        if (role.matches("doctor")) {
            enableSaveButton();
        }
    }
    @FXML
    private void onEdit(){
    
    
    }
     private void pushNotification( String text){
         Notifications not = Notifications.create(); 
            not.position(Pos.CENTER);
            not.hideAfter(Duration.seconds(2));
            not.text(text);
            not.title("Notifiction");          
            not.show();
    }

}
