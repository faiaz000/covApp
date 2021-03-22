/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Reusables.SharedData;
import Reusables.PatientData;
import DatabaseMysql.DbConnection;
import EntityClasses.Patient;
import EntityClasses.Signs;
import EntityClasses.Symptoms;
import covapp.SceneChanger;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;

import javafx.scene.control.ToggleGroup;
import javafx.util.Duration;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author Faiaz Sharaf Uddin
 */
public class SymptomsController implements Initializable {

    private Integer selectedPatientId;
    final EntityManagerFactory emf = Persistence.createEntityManagerFactory("CovAppPU");
    final EntityManager entityManager = emf.createEntityManager();
    private Patient selectedPatient;
    private String role;

    @FXML
    private Label id_label, name_label, user_name, productive_cough_label;
    @FXML
    private Button logout;
    @FXML
    private RadioButton headache_yes, headache_no, c_congestion_yes, c_congestion_no, pharyngodinia_yes, pharyngodinia_no, n_congestion_yes, n_congestion_no;
    @FXML
    private RadioButton hemoptysis_yes, hemoptysis_no, fatigue_yes, fatigue_no, breathe_yes, breathe_no, diarrhea_yes, diarrhea_no, nausea_yes, nausea_no, fever_yes, fever_no, chills_yes, chills_no;
    @FXML
    private RadioButton arthalgia_yes, arthalgia_no, chest_pain_yes, chest_pain_no, t_congestion_yes, t_congestion_no, tonsil_yes, tonsil_no, swelling_yes, swelling_no, lymph_yes;
    @FXML
    private RadioButton unconsciousness_yes, unconsciousness_no, p_cough_yes, p_cough_no, d_cough_yes, d_cough_no, myalgia_yes, myalgia_no, lymph_no, rashes_yes, rashes_no;
    @FXML
    private ToggleGroup headache, conjunctival_congestion, nasal_congestion, dry_cough, pharyngodinia, hemoptysis, fatigue, breathe_shortness, diarrhea, productive_cough;
    @FXML
    private ToggleGroup nausea, myalgia, arthalgia, chills, chest_pain, throat_congestion, tonsil, swelling, lymph_node, rashes, unconsciousness, fever;
    @FXML
    private Label headache_label, c_congestion_label, n_congestion_label, dry_cough_label, pharyngodinia_label, hemoptysis_label, fatigue_label, breathe_label, diarrhea_label, fever_label;
    @FXML
    private Label nausea_label, myalgia_label, arthalgia_label, chills_label, chest_pain_label, t_congestion_label, tonsil_label, swelling_label, lymph_label, rashes_label, unconsciousness_label;
    private String commonValidation = "No option selected";
    private boolean headacheValue, cCongestion, nCongestion, dryCough, pharyngodiniaValue, hemoptysisValue, fatigueValue, breathShortness, diarrheaValue, feverValue, productiveCough;
    private boolean nauseaValue, myalgiaValue, arthalgiaValue, chillsValue, chestPain, throatCongestion, tonsilsValue, swellingValue, lymphNode, unconsciousnessValue, rashesValue;
    //private boolean s_1,s2,s3,s4;
    @FXML
    private Button save_button, edit_button;
    
    private ResultSet symptomsData;
    
   // public  List<String, Object> toggleList;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        user_name.setText(SharedData.getUser());
        role = SharedData.getRole();
        if (role.matches("doctor")) {
            save_button.setDisable(true);
            save_button.setVisible(false);
            edit_button.setDisable(false);
            edit_button.setVisible(true);
        }
        //selectedPatientId = PatientData.getD_Id();
        selectedPatient = PatientData.getPatient();
        if (selectedPatient != null) {
            //query data 
            selectedPatientId = selectedPatient.getId();
            id_label.setText(selectedPatient.getId().toString());
            name_label.setText(selectedPatient.getFullName());
            try {
                symptomsData = checkSymptomsData(selectedPatientId);

                if (symptomsData.next()) {
                    save_button.setDisable(true);
                    save_button.setVisible(false);
                    edit_button.setVisible(true);
                    edit_button.setDisable(false);

                    setFieldValues(symptomsData);
                } else {
                    JOptionPane.showMessageDialog(null, "No symptoms recorded, Please add symptoms");
                }
            } catch (SQLException ex) {
                Logger.getLogger(SymptomsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @FXML
    private void onBack(ActionEvent event) throws IOException {
        previousScene(event);
    }

    @FXML
    private void onLogOut(ActionEvent event) throws IOException {

        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene(event, "Main.fxml", "Main");

    }

    private void previousScene(ActionEvent event) throws IOException {
       SceneChanger sceneChanger = new SceneChanger();
       sceneChanger.changeScene(event, "patientinfo.fxml", "Patient Info" );
    }


    @FXML
    private void onSave(ActionEvent event) throws IOException {
        boolean success = validations();

        if (success) {
            getInputValues();

            entityManager.getTransaction().begin();
            //selectedPatient = entityManager.find(Patient.class, selectedPatientId);
            Signs signs = getSigns();
            
            Symptoms symptoms = new Symptoms();

            symptoms = getSymptoms();

            entityManager.persist(symptoms);
            entityManager.persist(signs);
            entityManager.getTransaction().commit();
            entityManager.close();
            pushNotification("Saved Succesfully");
            //previousScene(event);
        }

    }

    private boolean validations() {
        boolean success = true;
        if (headache.getSelectedToggle() == null) {
            headache_label.setText(commonValidation);
            success = false;
        }
        if (conjunctival_congestion.getSelectedToggle() == null) {
            c_congestion_label.setText(commonValidation);
            success = false;
        }
        if (nasal_congestion.getSelectedToggle() == null) {
            n_congestion_label.setText(commonValidation);
            success = false;
        }
        if (dry_cough.getSelectedToggle() == null) {

            dry_cough_label.setText(commonValidation);
            success = false;
        }
        if (pharyngodinia.getSelectedToggle() == null) {
            pharyngodinia_label.setText(commonValidation);
            success = false;
        }
        if (hemoptysis.getSelectedToggle() == null) {
            hemoptysis_label.setText(commonValidation);
            success = false;
        }
        if (fatigue.getSelectedToggle() == null) {
            fatigue_label.setText(commonValidation);
            success = false;
        }
        if (breathe_shortness.getSelectedToggle() == null) {
            breathe_label.setText(commonValidation);
            success = false;
        }
        if (diarrhea.getSelectedToggle() == null) {
            diarrhea_label.setText(commonValidation);
            success = false;
        }
        if (nausea.getSelectedToggle() == null) {
            nausea_label.setText(commonValidation);
            success = false;
        }
        if (myalgia.getSelectedToggle() == null) {
            myalgia_label.setText(commonValidation);
            success = false;
        }
        if (arthalgia.getSelectedToggle() == null) {
            arthalgia_label.setText(commonValidation);
            success = false;
        }
        if (chills.getSelectedToggle() == null) {
            chills_label.setText(commonValidation);
            success = false;
        }
        if (chest_pain.getSelectedToggle() == null) {
            chest_pain_label.setText(commonValidation);
            success = false;
        }
        if (throat_congestion.getSelectedToggle() == null) {
            t_congestion_label.setText(commonValidation);
            success = false;
        }
        if (tonsil.getSelectedToggle() == null) {
            tonsil_label.setText(commonValidation);
            success = false;
        }
        if (swelling.getSelectedToggle() == null) {
            swelling_label.setText(commonValidation);
            success = false;
        }
        if (lymph_node.getSelectedToggle() == null) {
            lymph_label.setText(commonValidation);
            success = false;
        }
        if (rashes.getSelectedToggle() == null) {
            rashes_label.setText(commonValidation);
            success = false;
        }
        if (unconsciousness.getSelectedToggle() == null) {
            unconsciousness_label.setText(commonValidation);
            success = false;
        }
        if (fever.getSelectedToggle() == null) {
            fever_label.setText(commonValidation);
            success = false;
        }
        if (productive_cough.getSelectedToggle() == null) {
            productive_cough_label.setText(commonValidation);
            success = false;
        }

        return success;
    }

    private void getInputValues() {
        headacheValue = convertToBoolean(((RadioButton) headache.getSelectedToggle()).getText());

        cCongestion = convertToBoolean(((RadioButton) conjunctival_congestion.getSelectedToggle()).getText());
        //JOptionPane.showMessageDialog(null, cCongestion);
        nCongestion = convertToBoolean(((RadioButton) nasal_congestion.getSelectedToggle()).getText());
        dryCough = convertToBoolean(((RadioButton) dry_cough.getSelectedToggle()).getText());
        pharyngodiniaValue = convertToBoolean(((RadioButton) pharyngodinia.getSelectedToggle()).getText());
        hemoptysisValue = convertToBoolean(((RadioButton) hemoptysis.getSelectedToggle()).getText());
        fatigueValue = convertToBoolean(((RadioButton) fatigue.getSelectedToggle()).getText());
        breathShortness = convertToBoolean(((RadioButton) breathe_shortness.getSelectedToggle()).getText());
        diarrheaValue = convertToBoolean(((RadioButton) diarrhea.getSelectedToggle()).getText());
        nauseaValue = convertToBoolean(((RadioButton) nausea.getSelectedToggle()).getText());
        myalgiaValue = convertToBoolean(((RadioButton) myalgia.getSelectedToggle()).getText());
        arthalgiaValue = convertToBoolean(((RadioButton) arthalgia.getSelectedToggle()).getText());
        chillsValue = convertToBoolean(((RadioButton) chills.getSelectedToggle()).getText());
        chestPain = convertToBoolean(((RadioButton) chest_pain.getSelectedToggle()).getText());
        throatCongestion = convertToBoolean(((RadioButton) throat_congestion.getSelectedToggle()).getText());
        tonsilsValue = convertToBoolean(((RadioButton) tonsil.getSelectedToggle()).getText());
        swellingValue = convertToBoolean(((RadioButton) swelling.getSelectedToggle()).getText());
        throatCongestion = convertToBoolean(((RadioButton) throat_congestion.getSelectedToggle()).getText());
        lymphNode = convertToBoolean(((RadioButton) lymph_node.getSelectedToggle()).getText());
        unconsciousnessValue = convertToBoolean(((RadioButton) unconsciousness.getSelectedToggle()).getText());
        productiveCough = convertToBoolean(((RadioButton) productive_cough.getSelectedToggle()).getText());
        feverValue = convertToBoolean(((RadioButton) fever.getSelectedToggle()).getText());
        rashesValue = convertToBoolean(((RadioButton) rashes.getSelectedToggle()).getText());
        
        

    }

    private boolean convertToBoolean(String value) {
        if (value.matches("Yes")) {
            return true;
        } else {
            return false;
        }
    }

    private Signs getSigns() {
        Signs signs = new Signs();
        signs.setPatientId(selectedPatient);
        signs.setSwelling(swellingValue);
        signs.setThroatCongestion(throatCongestion);
        signs.setTonsil(tonsilsValue);
        signs.setLymphNodeEnlargement(lymphNode);
        signs.setUnconsciousness(unconsciousnessValue);
        signs.setRashes(rashesValue);

        return signs;
    }

    private Symptoms getSymptoms() {

        Symptoms symptoms = new Symptoms();
        symptoms.setHeadache(headacheValue);
        symptoms.setConjunctivalCongestion(cCongestion);
        symptoms.setNasalCongestion(nCongestion);
        symptoms.setDryCough(dryCough);
        symptoms.setPharyngodynia(pharyngodiniaValue);
        symptoms.setFever(feverValue);
        symptoms.setProductiveCough(productiveCough);
        symptoms.setHemoptysis(hemoptysisValue);
        symptoms.setFatigue(fatigueValue);
        symptoms.setBreatheShortness(breathShortness);
        symptoms.setDiarrhea(diarrheaValue);
        symptoms.setNausea(nauseaValue);
        symptoms.setArthalgia(arthalgiaValue);
        symptoms.setMyalgia(myalgiaValue);
        symptoms.setChills(chillsValue);
        symptoms.setChestPain(chestPain);
        symptoms.setPatientId(selectedPatient);

        return symptoms;
    }

    private ResultSet checkSymptomsData(Integer p_Id) throws SQLException {
        Connection conn = DbConnection.getconnection();
        String query = "SELECT * FROM symptoms, signs WHERE symptoms.patient_id = ? AND signs.patient_id = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, p_Id);
        statement.setInt(2, p_Id);

        ResultSet resultSet = statement.executeQuery();

        return resultSet;
        //To change body of generated methods, choose Tools | Templates.
    }

    private void setFieldValues(ResultSet symptomsData) throws SQLException {

        if (symptomsData.getBoolean("headache")) {
            headache.selectToggle(headache_yes);
        } else {
            headache.selectToggle(headache_no);
        }
        if (symptomsData.getBoolean("arthalgia")) {
            arthalgia.selectToggle(arthalgia_yes);
        } else {
            arthalgia.selectToggle(arthalgia_no);
        }
        if (symptomsData.getBoolean("conjunctival_congestion")) {
            conjunctival_congestion.selectToggle(c_congestion_yes);
        } else {
            conjunctival_congestion.selectToggle(c_congestion_no);
        }
        if (symptomsData.getBoolean("nasal_congestion")) {
            conjunctival_congestion.selectToggle(n_congestion_yes);
        } else {
            nasal_congestion.selectToggle(n_congestion_no);
        }
        if (symptomsData.getBoolean("breathe_shortness")) {
            breathe_shortness.selectToggle(breathe_yes);
        } else {
            breathe_shortness.selectToggle(breathe_no);
        }
        if (symptomsData.getBoolean("chest_pain")) {
            chest_pain.selectToggle(chest_pain_yes);
        } else {
            chest_pain.selectToggle(chest_pain_no);
        }
        if (symptomsData.getBoolean("productive_cough")) {
            productive_cough.selectToggle(p_cough_yes);
        } else {
            productive_cough.selectToggle(p_cough_no);
        }
        if (symptomsData.getBoolean("dry_cough")) {
            dry_cough.selectToggle(d_cough_yes);
        } else {
            dry_cough.selectToggle(d_cough_no);
        }
        if (symptomsData.getBoolean("pharyngodynia")) {
            pharyngodinia.selectToggle(pharyngodinia_yes);
        } else {
            pharyngodinia.selectToggle(pharyngodinia_no);
        }
        if (symptomsData.getBoolean("fatigue")) {
            fatigue.selectToggle(fatigue_yes);
        } else {
            fatigue.selectToggle(fatigue_yes);
        }
        if (symptomsData.getBoolean("nausea")) {
            nausea.selectToggle(nausea_yes);
        } else {
            nausea.selectToggle(nausea_no);
        }
        if (symptomsData.getBoolean("diarrhea")) {
            diarrhea.selectToggle(diarrhea_yes);
        } else {
            diarrhea.selectToggle(diarrhea_no);
        }
        if (symptomsData.getBoolean("fever")) {
            fever.selectToggle(fever_yes);
        } else {
            fever.selectToggle(fever_no);
        }
        if (symptomsData.getBoolean("myalgia")) {
            myalgia.selectToggle(myalgia_yes);
        } else {
            myalgia.selectToggle(myalgia_no);
        }
        if (symptomsData.getBoolean("chills")) {

            chills.selectToggle(chills_yes);
        } else {
            chills.selectToggle(chills_no);
        }
        if (symptomsData.getBoolean("hemoptysis")) {

            hemoptysis.selectToggle(hemoptysis_yes);
        } else {
            hemoptysis.selectToggle(hemoptysis_no);
        }
        if (symptomsData.getBoolean("throat_congestion")) {

            throat_congestion.selectToggle(t_congestion_yes);
        } else {
            throat_congestion.selectToggle(t_congestion_no);
        }
        if (symptomsData.getBoolean("lymph_node_enlargement")) {

            lymph_node.selectToggle(lymph_yes);
        } else {
            lymph_node.selectToggle(lymph_no);
        }
        if (symptomsData.getBoolean("tonsil")) {

            tonsil.selectToggle(tonsil_yes);
        } else {
            tonsil.selectToggle(tonsil_no);
        }
        if (symptomsData.getBoolean("swelling")) {

            swelling.selectToggle(swelling_yes);
        } else {
            swelling.selectToggle(swelling_yes);
        }
        if (symptomsData.getBoolean("rashes")) {

            rashes.selectToggle(rashes_yes);
        } else {
            rashes.selectToggle(rashes_no);
        }
        if (symptomsData.getBoolean("unconsciousness")) {

            unconsciousness.selectToggle(unconsciousness_yes);
        } else {
            unconsciousness.selectToggle(unconsciousness_no);
        }

    }

    @FXML
    private void onEdit(ActionEvent event) throws SQLException, IOException {
       
        getInputValues();
        Symptoms toBeUpdatedSymptoms = entityManager.find(Symptoms.class, symptomsData.getInt("symptoms_id"));
        Signs toBeUpdatedSigns = entityManager.find(Signs.class, symptomsData.getInt("signs_id"));
        entityManager.getTransaction().begin();
        //update signs
        toBeUpdatedSigns.setTonsil(tonsilsValue);
        toBeUpdatedSigns.setRashes(rashesValue);
        toBeUpdatedSigns.setSwelling(swellingValue);
        toBeUpdatedSigns.setLymphNodeEnlargement(lymphNode);
        toBeUpdatedSigns.setThroatCongestion(throatCongestion);
        toBeUpdatedSigns.setUnconsciousness(unconsciousnessValue);
        
        //update Symptoms
        toBeUpdatedSymptoms.setArthalgia(arthalgiaValue);
        toBeUpdatedSymptoms.setBreatheShortness(breathShortness);
        toBeUpdatedSymptoms.setHeadache(headacheValue);
        toBeUpdatedSymptoms.setConjunctivalCongestion(cCongestion);
        toBeUpdatedSymptoms.setNasalCongestion(nCongestion);
        toBeUpdatedSymptoms.setPharyngodynia(pharyngodiniaValue);
        toBeUpdatedSymptoms.setProductiveCough(productiveCough);
        toBeUpdatedSymptoms.setDryCough(dryCough);
        toBeUpdatedSymptoms.setHemoptysis(hemoptysisValue);
        toBeUpdatedSymptoms.setFatigue(fatigueValue);
        toBeUpdatedSymptoms.setDiarrhea(diarrheaValue);
        toBeUpdatedSymptoms.setNausea(nauseaValue);
        toBeUpdatedSymptoms.setMyalgia(myalgiaValue);
        toBeUpdatedSymptoms.setChestPain(chestPain);
        toBeUpdatedSymptoms.setChills(chillsValue);
        toBeUpdatedSymptoms.setFever(feverValue);
        
        entityManager.getTransaction().commit();
        
        entityManager.close();
        pushNotification("Edit Succesfully");
       // previousScene(event);
  
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
