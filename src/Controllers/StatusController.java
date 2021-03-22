/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import EntityClasses.Outcome;
import Reusables.SharedData;
import Reusables.PatientData;
import EntityClasses.Patient;
import EntityClasses.PatientState;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import covapp.SceneChanger;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.table.TableFilter;

/**
 * FXML Controller class
 *
 * @author Faiaz Sharaf Uddin
 */
public class StatusController implements Initializable {

    @FXML
    private Label covid_status_text;

    @FXML
    private CheckBox icu_checkbox;

    @FXML
    private CheckBox incubation_checkbox;

    @FXML
    private Label stroke_label;

    @FXML
    private Label heart_attack_label;

    @FXML
    private CheckBox ventilation_checkbox;

    @FXML
    private JFXComboBox<String> current_state;

    @FXML
    private CheckBox covid_checkbox;

    @FXML
    private JFXTextField sugar_level;

    @FXML
    private JFXTextField temperature;

    @FXML
    private JFXButton save_button;

    @FXML
    private JFXButton edit_button;

    @FXML
    private Label user_name;

    @FXML
    private Button logout;
    @FXML
    private TableView<PatientState> tableView;
    @FXML
    private TableColumn<PatientState, Integer> t_patientId;

    @FXML
    private TableColumn<PatientState, String> t_covidStatus;

    @FXML
    private TableColumn<PatientState, String> t_currentState;

    @FXML
    private TableColumn<PatientState, String> t_icu;

    @FXML
    private TableColumn<PatientState, String> t_incubation;

    @FXML
    private TableColumn<PatientState, Float> t_sugar;

    @FXML
    private TableColumn<PatientState, Float> t_temperature;

    @FXML
    private TableColumn<PatientState, String> t_ventilation;

    @FXML
    private TableColumn<PatientState, String> t_date;

    @FXML
    private Button back_button;

    private final ObservableList<String> statusItems = FXCollections.observableArrayList("Recovered", "Recovering", "Dead");

    private PatientState currentState;

    private String role;

    final EntityManagerFactory emf = Persistence.createEntityManagerFactory("CovAppPU");
    final EntityManager entityManager = emf.createEntityManager();

    private Integer selectedPatientId;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        current_state.setItems(statusItems);
        current_state.setPromptText("Select Status");
 
        edit_button.setDisable(true);
        edit_button.setVisible(false);
        statusTable();

        user_name.setText(SharedData.getUser());
        role = SharedData.getRole();
        selectedPatientId = PatientData.getD_Id();
        if (selectedPatientId != null) {
            //query data 
            getUpdatedTable();
            @SuppressWarnings("unchecked")
            TableFilter<PatientState> tf = TableFilter.forTableView(tableView).apply();
        }
        if (role.matches("nurse")) {
            // symptoms.setText("Add Symptoms"); 
            edit_button.setDisable(true);
            edit_button.setVisible(false);
            save_button.setVisible(false);
            save_button.setDisable(true);
        }

    }

    private void statusTable() {
        t_patientId.setCellValueFactory(cellData -> new SimpleObjectProperty<Integer>(
                cellData.getValue().getPatientId().getId()
        ));
        t_patientId.setStyle("-fx-alignment: CENTER");
        t_icu.setCellValueFactory(cellData -> {
            boolean value = cellData.getValue().getIcu();
            String valueAsString = value ? "Yes" : "No";
            return new ReadOnlyStringWrapper(valueAsString);
        });
        t_icu.setStyle("-fx-alignment: CENTER");
        t_incubation.setCellValueFactory(cellData -> {
            boolean value = cellData.getValue().getIncubation();
            String valueAsString = value ? "Yes" : "No";
            return new ReadOnlyStringWrapper(valueAsString);
        });
        t_incubation.setStyle("-fx-alignment: CENTER");

        t_ventilation.setCellValueFactory(cellData -> {
            boolean value = cellData.getValue().getVentilation();
            String valueAsString = value ? "Yes" : "No";
            return new ReadOnlyStringWrapper(valueAsString);
        });
        t_ventilation.setStyle("-fx-alignment: CENTER");
        t_covidStatus.setCellValueFactory(cellData -> {
            boolean value = cellData.getValue().getIcu();
            String valueAsString = value ? "Positive" : "Negative";
            return new ReadOnlyStringWrapper(valueAsString);
        });
        t_covidStatus.setStyle("-fx-alignment: CENTER");
        t_sugar.setCellValueFactory(new PropertyValueFactory<>("sugarLevel"));
        t_sugar.setStyle("-fx-alignment: CENTER");
        t_temperature.setCellValueFactory(new PropertyValueFactory<>("temperature"));
        t_temperature.setStyle("-fx-alignment: CENTER");
        t_currentState.setCellValueFactory(new PropertyValueFactory<>("currentState"));
        t_currentState.setStyle("-fx-alignment: CENTER");

        t_date.setCellValueFactory(cellData -> {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            return new ReadOnlyStringWrapper(formatter.format(cellData.getValue().getDate()));
        });
        t_date.setStyle("-fx-alignment: CENTER");
        
    }

    private void getUpdatedTable() {
        List<PatientState> selectedStateList = getPatientState();
        if (selectedStateList.size() > 0) {
            tableView.setItems(FXCollections.observableList(selectedStateList));
        }
    }

    @FXML
    void onSave(ActionEvent event) {
        boolean success = checkValidations();
        if (success) {
            setStatus();
            try {
                SharedData sharedData = new SharedData();
                sharedData.setModal("Status");
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Views/statusdialog.fxml"));

                Pane confirmPane = fxmlLoader.load();

                Dialog dialog = new Dialog();
                dialog.setDialogPane((DialogPane) confirmPane);
                dialog.getDialogPane().setStyle(" -fx-font-weight: bold");

                Optional<ButtonType> selection = dialog.showAndWait();
                if (selection.get() == ButtonType.YES) {
                    entityManager.getTransaction().begin();
                    entityManager.persist(currentState);
                    entityManager.getTransaction().commit();
                    getUpdatedTable();
                }

            } catch (Exception e) {
            }
        }
    }

    @FXML
    void onBack(ActionEvent event) throws IOException {
        previousScene(event);
    }

    @FXML
    private void onLogOut(ActionEvent event) throws IOException {
        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene(event, "Main.fxml", "Main");
    }

    private void previousScene(ActionEvent event) throws IOException {
        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene(event, "patientinfo.fxml", "Patient Info");
    }

    private boolean checkValidations() {
        boolean success = true;
        String title = "Validation Error";
        if (current_state.getValue() == null) {
            success = false;
            success = validationAlertBox(Alert.AlertType.ERROR, title, "Patient's current state must be selected");
        }
        if (!covid_checkbox.isSelected()) {
           
            success = validationAlertBox(Alert.AlertType.CONFIRMATION, title, "Are you sure the patient doesn't have covid ?");
        }
        if (sugar_level.getText().isEmpty()) {
            success = validationAlertBox(Alert.AlertType.ERROR, title, "Sugar Level cannot be empty");
        } else {

            if (!sugar_level.getText().matches("\\d*\\.?\\d+")) {
                success = validationAlertBox(Alert.AlertType.ERROR, title, "Value of Sugar Level cannot contain alphabets! It should only contain number");
            }
        }
        if (temperature.getText().isEmpty()) {
            success = validationAlertBox(Alert.AlertType.ERROR, title, "Temperature cannot be empty");
        } else {
            if (!temperature.getText().matches("\\d*\\.?\\d+")) {
                success = false;
                validationAlertBox(Alert.AlertType.ERROR, title, "Value of Temperature cannot contain alphabets! It should only contain number");
            }
        }
        return success;
    }

    private boolean validationAlertBox(Alert.AlertType alertType, String title, String contentText) {

        boolean success = false;
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.getDialogPane().setStyle(" -fx-font-weight: bold");

        if (alertType.equals(Alert.AlertType.ERROR)) {
            alert.showAndWait();
        } else if (alertType.equals(Alert.AlertType.CONFIRMATION)) {
            alert.getButtonTypes().remove(0, 2);
            alert.getButtonTypes().add(ButtonType.YES);
            alert.getButtonTypes().add(ButtonType.NO);

            Optional<ButtonType> selection = alert.showAndWait();

            if (selection.get() == ButtonType.YES || selection.get() == ButtonType.OK) {
                success = true;
            } else if (selection.get() == ButtonType.CANCEL || selection.get() == ButtonType.NO) {
                success = false;
            }
        }

        return success;

    }

    private void setStatus() {
        //To change body of generated methods, choose Tools | Templates.
        Date in = new Date();
        LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());
        Date presentDate = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
        
        PatientState patientState = new PatientState();
        patientState.setCovid(covid_checkbox.isSelected());
        patientState.setCurrentState(current_state.getValue());
        patientState.setIcu(icu_checkbox.isSelected());
        patientState.setIncubation(incubation_checkbox.isSelected());
        patientState.setTemperature(Float.valueOf(temperature.getText()));
        patientState.setSugarLevel(Float.valueOf(sugar_level.getText()));
        patientState.setVentilation(ventilation_checkbox.isSelected());
        Patient patient = entityManager.find(Patient.class, selectedPatientId);
        patientState.setPatientId(patient);
        patientState.setDate(presentDate);
        StatusObject stat = new StatusObject();
        stat.setPatientStus(patientState);
        currentState = patientState;
    }

    private List<PatientState> getPatientState() {

        Query query = entityManager.createNativeQuery("Select * from patient_state WHERE patient_id=?", PatientState.class);
        query.setParameter(1, selectedPatientId);

        @SuppressWarnings("unchecked")// To Stop Warning for the List object type
        List<PatientState> result = query.getResultList();
        return result;
    }
    
    @FXML
    private void getSelectedRow(MouseEvent event) {

        PatientState tableRow = (PatientState) tableView.getSelectionModel().getSelectedItem();
        covid_checkbox.setSelected(tableRow.getCovid());
        icu_checkbox.setSelected(tableRow.getIcu());
        incubation_checkbox.setSelected(tableRow.getIncubation());
        ventilation_checkbox.setSelected(tableRow.getVentilation());
        sugar_level.setText(String.valueOf(tableRow.getSugarLevel()));
        temperature.setText(String.valueOf(tableRow.getTemperature()));
        current_state.setValue(tableRow.getCurrentState());

       

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
