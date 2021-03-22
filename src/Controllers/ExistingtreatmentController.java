/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import EntityClasses.MedicalTests;
import EntityClasses.Medicine;
import EntityClasses.Treatment;
import Reusables.PatientData;
import Reusables.SharedData;
import Reusables.TreatmentComboClass;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import covapp.SceneChanger;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JOptionPane;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author Faiaz Sharaf Uddin
 */
public class ExistingtreatmentController implements Initializable {

    @FXML
    private Label user_name;

    @FXML
    private Button logout;

    @FXML
    private Button back_button;

    @FXML
    private Label patient_id_label;

    @FXML
    private Label patient_name_label;

    @FXML
    private TableView<Medicine> table_view;

    @FXML
    private TableColumn<Medicine, String> t_medicineName;

    @FXML
    private TableColumn<Medicine, String> t_medType;

    @FXML
    private TableColumn<Medicine, String> t_amount;

    @FXML
    private TableColumn<Medicine, String> t_morning;

    @FXML
    private TableColumn<Medicine, String> t_noon;

    @FXML
    private TableColumn<Medicine, String> t_night;

    @FXML
    private TableColumn<Medicine, Integer> t_duration;

    @FXML
    private ListView<String> test_list;

    @FXML
    private TextArea comments;

    @FXML
    private JFXButton new_treatment;

    @FXML
    private Label age_label;
    @FXML
    private JFXComboBox<TreatmentComboClass> prescription_dates;

    @FXML
    private Label prescription_date;
    List<Treatment> treatmentList = new ArrayList<>();
    final EntityManagerFactory emf = Persistence.createEntityManagerFactory("CovAppPU");
    final EntityManager entityManager = emf.createEntityManager();
    private Treatment selectedTreatment;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //  TreatmentData treatmentData = new TreatmentData();
        definetable();
        treatmentList = getTreatmentList();//TreatmentData.getTreatmentlist();//();
        setDateComboBox();
        int size = treatmentList.size();
        int selectedTreatmentId = treatmentList.get(size - 1).getId();

        getMedicineListFromDb(selectedTreatmentId);
        getTestListFromDb(selectedTreatmentId);
        
        setUIElements();

    }

    private void setDateComboBox() {
        ObservableList<TreatmentComboClass> comboClass = FXCollections.observableArrayList();

        treatmentList.forEach(prescription -> {

            String datetSring = formatDateCombobox(prescription.getDate());
            comboClass.add(new TreatmentComboClass(prescription.getId(), datetSring));

        });
        prescription_dates.setItems(comboClass);
        int size = comboClass.size() - 1;

        prescription_dates.setValue(comboClass.get(size));

    }

    private String formatDateCombobox(Date treatmentDate) {
        String formattedDate = "";
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        formattedDate = dateFormat.format(treatmentDate).toString();
        return formattedDate;
    }

    private void definetable() {
        t_medicineName.setCellValueFactory(new PropertyValueFactory<>("name"));
        t_medicineName.setStyle("-fx-alignment: CENTER");
        t_medType.setCellValueFactory(new PropertyValueFactory<>("type"));
        t_medType.setStyle("-fx-alignment: CENTER");
        t_amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        t_amount.setStyle("-fx-alignment: CENTER");
        t_morning.setCellValueFactory(cellData -> {
            boolean value = cellData.getValue().getDay();
            String valueAsString = value ? "Yes" : "No";
            return new ReadOnlyStringWrapper(valueAsString);
        });
        t_noon.setCellValueFactory(cellData -> {
            boolean value = cellData.getValue().getNoon();
            String valueAsString = value ? "Yes" : "No";
            return new ReadOnlyStringWrapper(valueAsString);
        });
        t_night.setCellValueFactory(cellData -> {
            boolean value = cellData.getValue().getNight();
            String valueAsString = value ? "Yes" : "No";
            return new ReadOnlyStringWrapper(valueAsString);
        });
        t_duration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        t_duration.setStyle("-fx-alignment: CENTER");
    }

    private List<Treatment> getTreatmentList() {
        int id = PatientData.getPatient().getId();
        Query teatmentquery = entityManager.createNativeQuery("Select * from treatment  where  patient_id = ? ", Treatment.class);
        teatmentquery.setParameter(1, id);

        List<Treatment> list = teatmentquery.getResultList();
        return list;

    }

    @FXML
    void onBack(ActionEvent event) throws IOException {
        String role = SharedData.getRole();
        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene(event, "patientinfo.fxml", role + " Panel");
    }

    @FXML
    void onLogOut(ActionEvent event) throws IOException {
        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene(event, "Main.fxml", "Main");
    }

    @FXML
    private void addNewPrescription(ActionEvent event) throws IOException {
        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene(event, "treatment.fxml", "Main");
    }

    private void getMedicineListFromDb(int selectedTreatmentId) {
        Query query = entityManager.createNativeQuery("Select * from medicine  where  treatment_id = ? ", Medicine.class);
        query.setParameter(1, selectedTreatmentId);
        List<Medicine> result = query.getResultList();
        if (result.size() > 0) {
            table_view.getItems().clear();
            table_view.getItems().addAll(result);
        }
    }

    private void getTestListFromDb(int selectedTreatmentId) {
        Query query = entityManager.createNativeQuery("Select id,name  from medical_tests  where  treatment_id = ? ", MedicalTests.class);
        query.setParameter(1, selectedTreatmentId);
        List<MedicalTests> result = query.getResultList();
        if (result.size() > 0) {        
            result.forEach(item -> {
                test_list.getItems().add(item.getName());
            });
        }
    }

    @FXML
    private void onDateSelect(ActionEvent event) {
        
        TreatmentComboClass treatmentComboClass = prescription_dates.getSelectionModel().getSelectedItem();
        getMedicineListFromDb(treatmentComboClass.getId());
        getTestListFromDb(treatmentComboClass.getId());
    }

    private void setUIElements() {
        user_name.setText(SharedData.getUser());
        patient_id_label.setText("Patient Id: " + PatientData.getPatient().getId());
        patient_name_label.setText("Patient Name: " + PatientData.getPatient().getFullName());
        age_label.setText(Integer.toString(PatientData.getPatient().getAge()));
        String role = SharedData.getRole();
      
        if(role.matches("nurse") || role.matches("Nurse")){
            
            new_treatment.setVisible(false);
            new_treatment.setDisable(true);
        }

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
