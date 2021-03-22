/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Reusables.SharedData;
import Reusables.PatientData;
import EntityClasses.MedicalHistory;
import EntityClasses.Patient;
import com.jfoenix.controls.JFXTextField;
import covapp.SceneChanger;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
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
public class MedicalhistoryController implements Initializable {

    @FXML
    private RadioButton diabetes_yes;

    @FXML
    private ToggleGroup diabetes;

    private boolean diabetesValue;
    @FXML
    private RadioButton diabetes_no;

    @FXML
    private RadioButton copd_yes;

    private boolean copdValue;
    @FXML
    private ToggleGroup copd;

    @FXML
    private RadioButton copd_no;

    @FXML
    private RadioButton c_fibrosis_yes;

    @FXML
    private ToggleGroup cystic_fibrosis;

    private boolean cysticFibrosis;

    @FXML
    private RadioButton hyperttension_yes;

    @FXML
    private ToggleGroup hypertension;
    private boolean hyperTension;

    @FXML
    private RadioButton cerebrovascular_yes;

    @FXML
    private ToggleGroup cerebrovascular;

    private boolean cerebroVascular;

    @FXML
    private RadioButton cardiovascular_yes;

    @FXML
    private ToggleGroup cardiovascular;
    private boolean cardioVascular;

    @FXML
    private RadioButton hepatitisb_yes;

    @FXML
    private ToggleGroup hepatitisb;

    private boolean hepatitisB;

    @FXML
    private RadioButton malignancy_yes;

    @FXML
    private ToggleGroup malignancy;

    private boolean malignancyValue;

    @FXML
    private RadioButton c_fibrosis_no;
    @FXML
    private RadioButton hyperttension_no;

    @FXML
    private RadioButton cerebrovascular_no;

    @FXML
    private RadioButton cardiovascular_no;

    @FXML
    private RadioButton hepatitisb_no;

    @FXML
    private RadioButton malignancy_no;

    @FXML
    private RadioButton chronic_kidney_yes;

    private boolean chronicKidney;

    @FXML
    private ToggleGroup chronic_kidney;

    @FXML
    private RadioButton immunodefficiency_yes;

    @FXML
    private ToggleGroup immunodefficiency;

    private boolean immunoDefficiency, asthmaValue;

    @FXML
    private RadioButton chronic_kidney_no;

    @FXML
    private RadioButton immunodefficiency_no;

    @FXML
    private Label diabetes_label;

    @FXML
    private Label copd_label;

    @FXML
    private Label c_fibrosis_label;

    @FXML
    private Label hypertension_label;

    @FXML
    private Label cerebrovascular_label;

    @FXML
    private Label cardiovascular_label;

    @FXML
    private Label hepatitisb_label;

    @FXML
    private Label malignancy_label;

    @FXML
    private Label chronic_kidney_label;

    @FXML
    private Label immunodefficiency_label;

    @FXML
    private RadioButton p_fibrosis_yes;

    @FXML
    private ToggleGroup pulmonary_fibrosis;

    private boolean pulmonaryFibrosis, cancerValue;

    @FXML
    private RadioButton p_fibrosis_no;

    @FXML
    private Label p_fibrosis_label;

    @FXML
    private RadioButton blood_disorder_yes;

    @FXML
    private ToggleGroup blood_disorder;

    @FXML
    private RadioButton cancer_yes;

    @FXML
    private ToggleGroup cancer;

    @FXML
    private RadioButton obesity_yes;

    @FXML
    private ToggleGroup obesity;

    @FXML
    private RadioButton blood_disorder_no;

    @FXML
    private RadioButton cancer_no;

    @FXML
    private RadioButton obesity_no;

    @FXML
    private RadioButton alcohol_yes;

    @FXML
    private ToggleGroup alcohol;

    @FXML
    private RadioButton tobacco_yes;

    @FXML
    private ToggleGroup tobacco;

    @FXML
    private RadioButton drugs_yes;

    @FXML
    private ToggleGroup drugs;

    @FXML
    private RadioButton asthma_yes;

    @FXML
    private ToggleGroup asthma;

    @FXML
    private RadioButton alcohol_no;

    @FXML
    private RadioButton tobacco_no;

    @FXML
    private RadioButton drugs_no;

    @FXML
    private RadioButton asthma_no;

    @FXML
    private Label blood_disorder_label;

    @FXML
    private Label cancer_label;

    @FXML
    private Label obesity_label;

    @FXML
    private Label alcohol_label;

    private boolean alcoholValue, drugsValue, obesityValue, tobaccoValue;

    @FXML
    private Label tobacco_label;

    @FXML
    private Label drugs_label;

    @FXML
    private Label asthma_label;

    @FXML
    private RadioButton neorologic_disease_yes;

    @FXML
    private ToggleGroup neorologic_disease;

    private boolean neorologicDisease, bloodDisorder;

    @FXML
    private RadioButton neorologic_disease_no;

    @FXML
    private ToggleGroup myalneorologic_diseasegia;

    @FXML
    private Label neorologic_disease_label;

    @FXML
    private RadioButton high_cholestrol_yes;

    private boolean highCholestrol;
    @FXML
    private ToggleGroup high_cholestrol;

    @FXML
    private RadioButton high_cholestrol_no;

    @FXML
    private Label high_cholestrol_label;

    @FXML
    private Button save_button;

    @FXML
    private Label user_name;

    @FXML
    private Button logout;

    @FXML
    private Button back_button;

    @FXML
    private Label id_label;

    @FXML
    private Label name_label, height_label, weight_label;

    @FXML
    private Button edit_button;

    @FXML
    private JFXTextField height;

    float heightValue, weightValue;

    @FXML
    private JFXTextField weight;

    private String commonValidation = "No Input Value";
    /**
     * Initializes the controller class.
     */
    Patient selectedPatient;
    Integer selectedPatientId;
    final EntityManagerFactory emf = Persistence.createEntityManagerFactory("CovAppPU");
    final EntityManager entityManager = emf.createEntityManager();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        user_name.setText(SharedData.getUser());
        selectedPatientId = PatientData.getD_Id();
        if (selectedPatientId != null) {
            try {
                //query data
                selectedPatient = PatientData.getPatient(); //entityManager.find(Patient.class, selectedPatientId);
                id_label.setText(selectedPatient.getId().toString());
                name_label.setText(selectedPatient.getFullName());
                List<MedicalHistory> medicalHistory = getMedicalHistory(selectedPatientId);
                if(medicalHistory.size()>0){
                    edit_button.setVisible(true);
                    edit_button.setDisable(false);
                    save_button.setDisable(true);
                    save_button.setVisible(false);
                    setFieldValues( medicalHistory.get(0));
                }
                else{
                     JOptionPane.showMessageDialog(null, "No medical history recorded! Please add medical records");
                }
                    
            } catch (SQLException ex) {
                Logger.getLogger(MedicalhistoryController.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        }
    }

    private void getInputValues() {

        diabetesValue = convertToBoolean(((Labeled) diabetes.getSelectedToggle()).getText());

        copdValue = convertToBoolean(((Labeled) copd.getSelectedToggle()).getText());

        cysticFibrosis = convertToBoolean(((Labeled) cystic_fibrosis.getSelectedToggle()).getText());
        pulmonaryFibrosis = convertToBoolean(((Labeled) pulmonary_fibrosis.getSelectedToggle()).getText());
        hyperTension = convertToBoolean(((Labeled) hypertension.getSelectedToggle()).getText());
        hepatitisB = convertToBoolean(((Labeled) hepatitisb.getSelectedToggle()).getText());
        cardioVascular = convertToBoolean(((Labeled) cardiovascular.getSelectedToggle()).getText());
        cerebroVascular = convertToBoolean(((Labeled) cerebrovascular.getSelectedToggle()).getText());
        malignancyValue = convertToBoolean(((Labeled) malignancy.getSelectedToggle()).getText());
        chronicKidney = convertToBoolean(((Labeled) chronic_kidney.getSelectedToggle()).getText());
        immunoDefficiency = convertToBoolean(((Labeled) immunodefficiency.getSelectedToggle()).getText());
        highCholestrol = convertToBoolean(((Labeled) high_cholestrol.getSelectedToggle()).getText());
        neorologicDisease = convertToBoolean(((Labeled) neorologic_disease.getSelectedToggle()).getText());
        bloodDisorder = convertToBoolean(((Labeled) blood_disorder.getSelectedToggle()).getText());
        cancerValue = convertToBoolean(((Labeled) cancer.getSelectedToggle()).getText());
        obesityValue = convertToBoolean(((Labeled) obesity.getSelectedToggle()).getText());
        alcoholValue = convertToBoolean(((Labeled) alcohol.getSelectedToggle()).getText());
        tobaccoValue = convertToBoolean(((Labeled) tobacco.getSelectedToggle()).getText());
        drugsValue = convertToBoolean(((Labeled) drugs.getSelectedToggle()).getText());
        asthmaValue = convertToBoolean(((Labeled) asthma.getSelectedToggle()).getText());
        heightValue = Float.parseFloat(height.getText());
        weightValue = Float.parseFloat(weight.getText());

    }

    private boolean convertToBoolean(String value) {
        if (value.matches("Yes")) {
            return true;
        } else {
            return false;
        }
    }

    private boolean validations() {
        boolean success = true;

        if (diabetes.getSelectedToggle() == null) {
            diabetes_label.setText(commonValidation);
            success = false;
        }
        if (copd.getSelectedToggle() == null) {
            copd_label.setText(commonValidation);
            success = false;
        }
        if (cystic_fibrosis.getSelectedToggle() == null) {

            c_fibrosis_label.setText(commonValidation);
            success = false;
        }
        if (pulmonary_fibrosis.getSelectedToggle() == null) {
            p_fibrosis_label.setText(commonValidation);
            success = false;
        }
        if (hypertension.getSelectedToggle() == null) {
            hypertension_label.setText(commonValidation);
            success = false;
        }
        if (hepatitisb.getSelectedToggle() == null) {
            hepatitisb_label.setText(commonValidation);
            success = false;
        }
        if (cardiovascular.getSelectedToggle() == null) {
            cardiovascular_label.setText(commonValidation);
            success = false;
        }
        if (cerebrovascular.getSelectedToggle() == null) {
            cerebrovascular_label.setText(commonValidation);
            success = false;
        }
        if (chronic_kidney.getSelectedToggle() == null) {
            chronic_kidney_label.setText(commonValidation);
            success = false;
        }
        if (immunodefficiency.getSelectedToggle() == null) {
            immunodefficiency_label.setText(commonValidation);
            success = false;
        }
        if (high_cholestrol.getSelectedToggle() == null) {
            high_cholestrol_label.setText(commonValidation);
            success = false;
        }
        if (blood_disorder.getSelectedToggle() == null) {
            blood_disorder_label.setText(commonValidation);
            success = false;
        }
        if (neorologic_disease.getSelectedToggle() == null) {
            neorologic_disease_label.setText(commonValidation);
            success = false;
        }
        if (obesity.getSelectedToggle() == null) {
            obesity_label.setText(commonValidation);
            success = false;
        }
        if (alcohol.getSelectedToggle() == null) {
            alcohol_label.setText(commonValidation);
            success = false;
        }
        if (tobacco.getSelectedToggle() == null) {
            tobacco_label.setText(commonValidation);
            success = false;
        }
        if (drugs.getSelectedToggle() == null) {
            drugs_label.setText(commonValidation);
            success = false;
        }
        if (asthma.getSelectedToggle() == null) {
            asthma_label.setText(commonValidation);
            success = false;
        }
        if (cancer.getSelectedToggle() == null) {
            cancer_label.setText(commonValidation);
            success = false;
        }
        if (height.getText().isEmpty()) {
            height_label.setText(commonValidation);
            success = false;
        }
        if (weight.getText().isEmpty()) {
            weight_label.setText(commonValidation);
            success = false;
        }

        return success;
    }

    private MedicalHistory setMedicalHistory() {

        MedicalHistory medicalHistory = new MedicalHistory();

        medicalHistory.setAlcohol(alcoholValue);
        medicalHistory.setAsthma(asthmaValue);
        medicalHistory.setBloodDisorder(bloodDisorder);
        medicalHistory.setCancer(cancerValue);
        medicalHistory.setCardiovascularDisease(cardioVascular);
        medicalHistory.setCerebrovascularDisease(cerebroVascular);
        medicalHistory.setChronicKidneyDisease(chronicKidney);
        medicalHistory.setCopd(copdValue);
        medicalHistory.setCysticFibrosis(cysticFibrosis);
        medicalHistory.setDiabetes(diabetesValue);
        medicalHistory.setDrugs(drugsValue);
        medicalHistory.setHeight(heightValue);
        medicalHistory.setHepatitisB(hepatitisB);
        medicalHistory.setHighCholesterol(highCholestrol);
        medicalHistory.setHypertension(hyperTension);
        medicalHistory.setImmunodeficiency(immunoDefficiency);
        medicalHistory.setMalignancy(malignancyValue);
        medicalHistory.setNeurologicDisease(neorologicDisease);
        medicalHistory.setObesity(obesityValue);
        medicalHistory.setPulmonaryFibrosis(pulmonaryFibrosis);
        medicalHistory.setTobacco(tobaccoValue);
        medicalHistory.setWeight(weightValue);

        medicalHistory.setPatientId(selectedPatient);

        return medicalHistory;
    }
    
     
    private List<MedicalHistory> getMedicalHistory(Integer id){
         
        Query query =  entityManager.createNativeQuery("Select * from medical_history WHERE patient_id=?", MedicalHistory.class);
        query.setParameter(1, id);
        
        @SuppressWarnings("unchecked")// To Stop Warning for the List object type
        List<MedicalHistory> result = query.getResultList();
        return result;
           
         
    }

    @FXML
    private void onBack(ActionEvent event) throws IOException {

        previousScene(event);

    }

    @FXML
    void onEdit(ActionEvent event) {

    }

    @FXML
    void onSave(ActionEvent event) throws IOException {
        boolean success = validations();

        if (success) {
            getInputValues();

            entityManager.getTransaction().begin();
            //selectedPatient = entityManager.find(Patient.class, selectedPatientId);
            MedicalHistory medicalHistory = setMedicalHistory();

            entityManager.persist(medicalHistory);

            entityManager.getTransaction().commit();
            entityManager.close();
            previousScene(event);
        }
    }

    private void setFieldValues(MedicalHistory medicalHistory) throws SQLException {

        if (medicalHistory.getAlcohol()) {
            alcohol.selectToggle(alcohol_yes);
        } else {
            alcohol.selectToggle(alcohol_no);
        }
        if (medicalHistory.getAsthma()) {
            asthma.selectToggle(asthma_yes);
        } else {
            asthma.selectToggle(asthma_no);
        }
        if (medicalHistory.getBloodDisorder()) {
            blood_disorder.selectToggle(blood_disorder_yes);
        } else {
            blood_disorder.selectToggle(blood_disorder_no);
        }
        if (medicalHistory.getCancer()) {
            cancer.selectToggle(cancer_yes);
        } else {
            cancer.selectToggle(cancer_no);
        }
        if (medicalHistory.getCardiovascularDisease()) {
            cardiovascular.selectToggle(cardiovascular_yes);
        } else {
            cardiovascular.selectToggle(cardiovascular_no);
        }
        if (medicalHistory.getCerebrovascularDisease()) {
            cerebrovascular.selectToggle(cerebrovascular_yes);
        } else {
            cerebrovascular.selectToggle(cerebrovascular_no);
        }
        if (medicalHistory.getCopd()) {
            copd.selectToggle(copd_yes);
        } else {
            copd.selectToggle(copd_no);
        }
        if (medicalHistory.getChronicKidneyDisease()) {
            chronic_kidney.selectToggle(chronic_kidney_yes);
        } else {
            chronic_kidney.selectToggle(chronic_kidney_no);
        }
        if (medicalHistory.getCysticFibrosis()) {
            cystic_fibrosis.selectToggle(c_fibrosis_yes);
        } else {
            cystic_fibrosis.selectToggle(c_fibrosis_no);
        }
        if (medicalHistory.getDiabetes()) {
            diabetes.selectToggle(diabetes_yes);
        } else {
            diabetes.selectToggle(diabetes_no);
        }
        if (medicalHistory.getDrugs()) {
            drugs.selectToggle(drugs_yes);
        } else {
            drugs.selectToggle(drugs_no);
        }
        if (medicalHistory.getHepatitisB()) {
            hepatitisb.selectToggle(hepatitisb_yes);
        } else {
            hepatitisb.selectToggle(hepatitisb_no);
        }
        if (medicalHistory.getHighCholesterol()) {
            high_cholestrol.selectToggle(high_cholestrol_yes);
        } else {
            high_cholestrol.selectToggle(high_cholestrol_no);
        }
        if (medicalHistory.getHypertension()) {
            hypertension.selectToggle(hyperttension_yes);
        } else {
            hypertension.selectToggle(hyperttension_no);
        }
        if (medicalHistory.getImmunodeficiency()) {
            immunodefficiency.selectToggle(immunodefficiency_yes);
        } else {
            immunodefficiency.selectToggle(immunodefficiency_no);
        }
        if (medicalHistory.getMalignancy()) {
            malignancy.selectToggle(malignancy_yes);
        } else {
            malignancy.selectToggle(malignancy_no);
        }
        if (medicalHistory.getNeurologicDisease()) {
            neorologic_disease.selectToggle(neorologic_disease_yes);
        } else {
            neorologic_disease.selectToggle(neorologic_disease_no);
        }
        if (medicalHistory.getObesity()) {
            obesity.selectToggle(obesity_yes);
        } else {
            obesity.selectToggle(obesity_no);
        }
        if (medicalHistory.getPulmonaryFibrosis()) {
            pulmonary_fibrosis.selectToggle(p_fibrosis_yes);
        } else {
            pulmonary_fibrosis.selectToggle(p_fibrosis_no);
        }
        if (medicalHistory.getTobacco()) {
            tobacco.selectToggle(tobacco_yes);
        } else {
            tobacco.selectToggle(tobacco_no);
        }
        height.setText(Float.toString(medicalHistory.getHeight()));
        weight.setText(Float.toString(medicalHistory.getWeight()));
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
     private void pushNotification( String text){
         Notifications not = Notifications.create(); 
            not.position(Pos.CENTER);
            not.hideAfter(Duration.seconds(2));
            not.text(text);
            not.title("Notifiction");          
            not.show();
    }

}
