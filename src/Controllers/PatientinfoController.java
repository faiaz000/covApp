/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Reusables.SharedData;
import Reusables.PatientData;
import EntityClasses.Patient;
import EntityClasses.Symptoms;
import EntityClasses.Treatment;
import Reusables.TreatmentData;
import com.jfoenix.controls.JFXTextField;
import covapp.SceneChanger;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
public class PatientinfoController implements Initializable {

    private Integer selectedPatientId;

    final EntityManagerFactory emf = Persistence.createEntityManagerFactory("CovAppPU");
    final EntityManager entityManager = emf.createEntityManager();
    private Patient selectedPatient;
    @FXML
    private Button back_button, logout, symptoms, treatment, outcome, status, etreatment_button;

    @FXML
    private JFXTextField id, name, sex, age, marriage, children;
    private String role;
    @FXML
    private Label user_name;
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        user_name.setText(SharedData.getUser());
        role = SharedData.getRole();

        selectedPatientId = PatientData.getD_Id();
        if (selectedPatientId != null) {
            //query data 
            selectedPatient = entityManager.find(Patient.class, selectedPatientId);
            PatientData patientData = new PatientData();
            patientData.setPatient(selectedPatient);
            showInfo();
            
            if(checkSymptomsRecorded()){
                symptoms.setText("Symptoms");
            }
            else{
                symptoms.setText("Add Symptoms");
            }
        }
        
        if (role.matches("nurse")|| role.matches("Nurse")) {
            
            etreatment_button.setVisible(false);
            etreatment_button.setDisable(true);  
        }
        else if (role.matches("doctor") || role.matches("Doctor")){
            
         
            etreatment_button.setVisible(true);
            etreatment_button.setDisable(false);  
        
        }
        
        
    }

    private void showInfo() {

        id.setText(selectedPatient.getId().toString());
        name.setText(selectedPatient.getFullName());
        marriage.setText(selectedPatient.getMaritalStatus());
        age.setText(Integer.toString(selectedPatient.getAge()));
        sex.setText(selectedPatient.getSex());
        children.setText(Integer.toString(selectedPatient.getChildrenNo()));

    }

    @FXML
    private void onSymptomsClick(ActionEvent event) throws IOException {

        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene(event, "Symptoms.fxml", "Symptoms");
    }

    @FXML
    private void onMedicalHistoryClick(ActionEvent event) throws IOException {

        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene(event, "medicalhistory.fxml", "Symptoms");
    }

    @FXML
    private void onBack(ActionEvent event) throws IOException {

        previousScene(event);

    }

    @FXML
    private void onReportButtonClick(ActionEvent event) throws IOException {
        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene(event, "reports.fxml", "Reports");
    }

    @FXML
    private void onLogOut(ActionEvent event) throws IOException {

        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene(event, "Main.fxml", "Main");

    }

    @FXML
    private void previousScene(ActionEvent event) throws IOException {
        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene(event, "doctorpanel.fxml", role + " Panel");

    }

    @FXML
    private void onMedicalHistory(ActionEvent event) throws IOException {

        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene(event, "medicalhistory.fxml", " Patient's medical History");

    }

    @FXML
    private void onOutcomeClick(ActionEvent event) throws IOException {

        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene(event, "outcome.fxml", "Outcome Panel");
    }

    @FXML
    private void onStatusClick(ActionEvent event) throws IOException {

        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene(event, "status.fxml", "Patient's Current State");

    }

    @FXML
    private void onTreatment(ActionEvent event) throws IOException {
        PatientData patientData = new PatientData();
        patientData.setTreatmentState(false);
        int result = checkTreatmentExist();
        if (result > 0) {
      
            TreatmentData treatmentData = new TreatmentData();
            treatmentData.setPreviousPrescription(true);
            SceneChanger sceneChanger = new SceneChanger();
            sceneChanger.changeScene(event, "existingtreatment.fxml", "Treatment Panel");

        } else {
            if (role.matches("doctor") || role.matches("Doctor")) {
                
                SceneChanger sceneChanger = new SceneChanger();
                sceneChanger.changeScene(event, "treatment.fxml", "Treatment Panel");
            }
            else{
          
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                
                alert.setTitle("No Treatment");
                alert.setContentText("No prescription is recorded for this patient");
                alert.show();
            }

        }

    }

    @FXML
    private void onETreatment(ActionEvent event) throws IOException {
        PatientData patientData = new PatientData();
        patientData.setTreatmentState(true);
        int result = checkTreatmentExist();
        if (result > 0) {
            TreatmentData treatmentData = new TreatmentData();
            treatmentData.setPreviousPrescription(true);
            SceneChanger sceneChanger = new SceneChanger();
            sceneChanger.changeScene(event, "existingtreatment.fxml", "Treatment Panel");

        } else {

            SceneChanger sceneChanger = new SceneChanger();
            sceneChanger.changeScene(event, "treatment.fxml", "Treatment Panel");

        }

    }

    private int checkTreatmentExist() {
        int id = selectedPatientId;
        Query query = entityManager.createNativeQuery("Select * from treatment  where  patient_id = ? ", Treatment.class);
        query.setParameter(1, id);
        /* TreatmentData treatmentData = new TreatmentData();
        treatmentData.setTreatmentLists(query.getResultList());*/

        int result = query.getResultList().size();

        return result;

    }
    
    private boolean checkSymptomsRecorded(){
        boolean success = true;
        Query query = entityManager.createNativeQuery("Select * from symptoms  where  patient_id = ?",Symptoms.class);
        
        query.setParameter(1, selectedPatient);
        
        int size = query.getResultList().size();
        success = size>0? true: false;
        return success;
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
