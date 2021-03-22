/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Reusables.PatientData;
import EntityClasses.Address;
import EntityClasses.Patient;
import Reusables.SharedData;
import com.jfoenix.controls.JFXComboBox;
import covapp.SceneChanger;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.time.ZoneId;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author Faiaz Sharaf Uddin
 */
public class AddpatientController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button save_button, edit_button, back_button;
    @FXML
    private TextField first_name, middle_name, last_name, age_field, occupation_field, children_no;
    @FXML
    private TextField house, road, country, state, city, zip, phone, email, ssn_field, area;
    @FXML
    private DatePicker dob, admission_date, release_date;
    private Date temp_dob, temp_addmission, temp_release;
    @FXML
    private ComboBox<String> sex_combobox, marital_status;

    @FXML
    JFXComboBox filter_combobox;
    @FXML
    private Label user_name;

    private String firstName, middleName, lastName, maritalStatus, occupation, phoneNo;
    private String emailId, sex, pHouse, pRoad, pCountry, pState, pCity, pZip, ssn, pArea;

    private int age, children, temp_age, temp_children;
    private Date birthDate, admissionDate, releaseDate;

    ObservableList<String> gender = FXCollections.observableArrayList("Male", "Female", "Undefined");
    ObservableList<String> marriage = FXCollections.observableArrayList("Unmarried", "Married");

    private String temp_fname, temp_mname, temp_lname, temp_sex, temp_mstatus, temp_phone, temp_occupation, tempArea;
    private String temp_road, temp_house, temp_zip, temp_city, temp_country, temp_state, temp_ssn, temp_email;

    final EntityManagerFactory emf = Persistence.createEntityManagerFactory("CovAppPU");
    final EntityManager entityManager = emf.createEntityManager();
    private Patient patientModel;

    private Integer selectedPatientId;
    private Patient selectedPatient;
    String user;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // clearValidationLabels();
        user = SharedData.getUserObject().getUserid();
        user_name.setText(user);
        clearFields();
        edit_button.setDisable(true);
        selectedPatientId = PatientData.getId();
        if (selectedPatientId != null) {
            //query data 
            selectedPatient = entityManager.find(Patient.class, selectedPatientId);
            patientValues();

            save_button.setDisable(true);
            save_button.setVisible(false);
            edit_button.setVisible(true);
            edit_button.setDisable(false);

        }

        sex_combobox.setItems(gender);
        sex_combobox.setPromptText("Select Sex");
        marital_status.setItems(marriage);
        marital_status.setPromptText("Select Status");
    }

    @FXML
    private void addPatient(ActionEvent event) throws IOException {
        Boolean success = inputValidations();

        if (success) {

            getPatientFromInput();
            Address tempAddress = new Address();
            tempAddress.setCity(pCity);
            tempAddress.setHouse(pHouse);
            tempAddress.setRoad(pRoad);
            tempAddress.setState(pState);
            tempAddress.setCountry(pCountry);
            tempAddress.setZip(pZip);
            tempAddress.setArea(pArea);
            entityManager.getTransaction().begin();
            entityManager.persist(tempAddress);
            entityManager.getTransaction().commit();
            //Address address_id = addAddress();
            patientModel = new Patient();
            patientModel.setFirstName(firstName);
            if (!middleName.isEmpty()) {
                patientModel.setMiddleName(middleName);
            }
            patientModel.setLastName(lastName);
            patientModel.setDob(birthDate);
            patientModel.setAge(age);
            patientModel.setSex(sex);
            patientModel.setChildrenNo(children);
            patientModel.setOccupation(occupation);
            patientModel.setSecurityNumber(ssn);
            patientModel.setMaritalStatus(maritalStatus);
            // patientModel.setAddressId(address_id);
            patientModel.setAddmissionDate(admissionDate);
            if (releaseDate != null) {
                patientModel.setReleaseDate(releaseDate);
            }
            patientModel.setAddressId(tempAddress);
            patientModel.setPhone(phoneNo);
            patientModel.setEmail(emailId);
            // if(!entityManager.isOpen()){
            entityManager.getTransaction().begin();
            // }

            entityManager.persist(patientModel);
            entityManager.getTransaction().commit();
            entityManager.close();
            previousScene(event);
        }

    }

    private void getPatientFromInput() {

        firstName = first_name.getText();
        lastName = last_name.getText();
        middleName = middle_name.getText();
        age = Integer.parseInt(age_field.getText());
        birthDate = java.sql.Date.valueOf(dob.getValue());
        admissionDate = java.sql.Date.valueOf(admission_date.getValue());;
        if (release_date.getValue() != null) {
            releaseDate = java.sql.Date.valueOf(release_date.getValue());
        }
        children = Integer.parseInt(children_no.getText());
        sex = sex_combobox.getValue().toString();
        maritalStatus = marital_status.getValue().toString();
        phoneNo = phone.getText();
        emailId = email.getText();
        occupation = occupation_field.getText();
        ssn = ssn_field.getText();
        pHouse = house.getText();
        pRoad = road.getText();
        pZip = zip.getText();
        pCountry = country.getText();
        pState = state.getText();
        pCity = city.getText();
        pArea = area.getText();

    }

    private Boolean inputValidations() {

        Boolean success = true;

        if (first_name.getText().isEmpty()) {

            validationAlertBox("Empty Field", "First Name is required");
            success = false;
        }
        if (last_name.getText().isEmpty()) {
            validationAlertBox("Empty Field", "Last Name is required");
            success = false;
        }
        if (age_field.getText().isEmpty()) {
            validationAlertBox("Empty Field", "Age is required");
            success = false;
        }
        else{
            if(chekcageField()){
                success = true;
            }
            else{
                validationAlertBox("Incorrect Input", "Number must be inserted!");
                success = false;
            }
        
        }
        if (children_no.getText().isEmpty()) {
            validationAlertBox("Empty Field", "Children no is required");
            success = false;
        }
        if (sex_combobox.getSelectionModel().isEmpty()) {
            validationAlertBox("No Selection", "Patient's Sex must be selected");
            success = false;
        }
        if (marital_status.getSelectionModel().isEmpty()) {
            validationAlertBox("No Selection", "Patient's Sex must be selected");
            success = false;
        }
        if (occupation_field.getText().isEmpty()) {
            validationAlertBox("Empty Field", "Occupation is required");
            success = false;
        }
        if (phone.getText().isEmpty()) {

            validationAlertBox("Empty Field", "Phone Number is required");
            success = false;
        }
        if (email.getText().isEmpty()) {
            validationAlertBox("Empty Field", "Email Number is required");
            success = false;
        }
        if (ssn_field.getText().isEmpty()) {

            validationAlertBox("Empty Field", "Social Security Number is required");
            success = false;
        }
        if (house.getText().isEmpty()) {
            validationAlertBox("Empty Field", "House number is required");
            success = false;
        }
        if (road.getText().isEmpty()) {
            validationAlertBox("Empty Field", "Road Name or Number is required");
            success = false;
        }
        if (area.getText().isEmpty()) {
            validationAlertBox("Empty Field", "Area is required");
            success = false;
        }
        if (zip.getText().isEmpty()) {
            validationAlertBox("Empty Field", "ZIP/Postal is required");
            success = false;
        }
        if (country.getText().isEmpty()) {
            validationAlertBox("Empty Field", "Country is required");
            success = false;
        }
        if (city.getText().isEmpty()) {
            validationAlertBox("Empty Field", "City is required");
            success = false;
        }
        if (state.getText().isEmpty()) {
            validationAlertBox("Empty Field", "State is required");
            success = false;
        }
        if (dob.getValue() == null) {
            validationAlertBox("No Date Selected", "Birth Date must be selected");
            success = false;
        }
        if (admission_date.getValue() == null) {
            validationAlertBox("No Date Selected", "Admission Date must be selected");
            success = false;
        }

        return success;
    }

    @FXML
    private void onBack(ActionEvent event) throws IOException {
        clearFields();
        previousScene(event);

    }

    private void previousScene(ActionEvent event) throws IOException {
        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene(event, "receptionpanel.fxml", "Reception Panel");
    }

    private void patientValues() {

        setTempPatientValues();

        first_name.setText(temp_fname);
        last_name.setText(temp_lname);
        if (!temp_mname.isEmpty()) {
            middle_name.setText(temp_mname);
        }

        dob.setValue(selectedPatient.getDob().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        
        age_field.setText(String.valueOf(temp_age));
        children_no.setText(String.valueOf(temp_children));
        sex_combobox.getSelectionModel().select(temp_sex);
        marital_status.getSelectionModel().select(temp_mstatus);
        email.setText(temp_email);
        phone.setText(temp_phone);
        ssn_field.setText(temp_ssn);
        occupation_field.setText(temp_occupation);
        admission_date.setValue(temp_addmission.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        house.setText(temp_house);
        road.setText(temp_road);
        zip.setText(temp_zip);
        country.setText(temp_country);
        state.setText(temp_state);
        area.setText(tempArea);
        city.setText(temp_city);
        if (temp_release != null) {
            release_date.setValue(temp_release.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }

    }

    private void setTempPatientValues() {

        temp_fname = selectedPatient.getFirstName();
        temp_lname = selectedPatient.getLastName();
        temp_mname = selectedPatient.getMiddleName();
        temp_dob =selectedPatient.getAddmissionDate(); //selectedPatient.getDob();
        temp_addmission = selectedPatient.getAddmissionDate();
        temp_release = selectedPatient.getReleaseDate();
        temp_age = selectedPatient.getAge();
        temp_children = selectedPatient.getChildrenNo();
        temp_sex = selectedPatient.getSex();
        temp_mstatus = selectedPatient.getMaritalStatus();
        temp_email = selectedPatient.getEmail();
        temp_occupation = selectedPatient.getOccupation();
        temp_phone = selectedPatient.getPhone();
        temp_ssn = selectedPatient.getSecurityNumber();
        temp_house = selectedPatient.getAddressId().getHouse();
        temp_road = selectedPatient.getAddressId().getRoad();
        temp_zip = selectedPatient.getAddressId().getZip();
        temp_city = selectedPatient.getAddressId().getCity();
        temp_country = selectedPatient.getAddressId().getCountry();
        temp_state = selectedPatient.getAddressId().getState();
        tempArea = selectedPatient.getAddressId().getArea();

        //System.out.println(temp_dob.toString());
    }

    @FXML
    private void onEdit(ActionEvent event) throws IOException {

        if (inputValidations()) {
            Address selectedAddress = entityManager.find(Address.class, selectedPatient.getAddressId().getId());
            getPatientFromInput();
            setTempPatientValues();
            entityManager.getTransaction().begin();
            if (!temp_fname.matches(firstName)) {
                selectedPatient.setFirstName(firstName);
            }
            if (!temp_lname.matches(lastName)) {
                selectedPatient.setLastName(lastName);
            }
            if (!temp_mname.matches(middleName)) {
                selectedPatient.setMiddleName(middleName);
            }
            if (age != temp_age) {
                selectedPatient.setAge(temp_age);
            }
            if (children != temp_children) {
                selectedPatient.setAge(temp_age);
            }
            if (!maritalStatus.matches(temp_mstatus)) {
                selectedPatient.setMaritalStatus(maritalStatus);
            }
            if (!sex.matches(temp_sex)) {
                selectedPatient.setSex(sex);
            }
            if (!ssn.matches(temp_ssn)) {
                selectedPatient.setSecurityNumber(ssn);
            }
            if (!occupation.matches(temp_occupation)) {
                selectedPatient.setOccupation(occupation);
            }
            if (!phoneNo.matches(temp_phone)) {
                selectedPatient.setPhone(phoneNo);
            }
            if (!emailId.matches(temp_email)) {
                selectedPatient.setEmail(emailId);
            }
            if (birthDate != temp_dob) {
                selectedPatient.setDob(birthDate);
            }
            if (admissionDate != temp_addmission) {
                selectedPatient.setAddmissionDate(admissionDate);
            }
            if ((release_date != null) && (releaseDate != temp_dob)) {
                selectedPatient.setReleaseDate(releaseDate);
            }
            if (!pHouse.matches(temp_house)) {
                selectedAddress.setHouse(pHouse);
            }
            if (!pRoad.matches(temp_road)) {
                selectedAddress.setRoad(pRoad);
            }
            if (!pArea.matches(tempArea)) {
                selectedAddress.setArea(pArea);
            }
            if (!pZip.matches(temp_zip)) {
                selectedAddress.setZip(pZip);
            }
            if (!pCity.matches(temp_city)) {
                selectedAddress.setCity(pCity);
            }
            if (!pState.matches(temp_state)) {
                selectedAddress.setState(pState);
            }
            if (!pCountry.matches(temp_country)) {
                selectedAddress.setCountry(pCountry);
            }

            entityManager.getTransaction().commit();
            entityManager.close();
            previousScene(event);
        }
    }

    @FXML
    private void onLogOut(ActionEvent event) throws IOException {

        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene(event, "Main.fxml", "Main");

    }

    private void clearFields() {

        first_name.setText("");
        // JOptionPane.showMessageDialog(null, first_name.getText());

        middle_name.setText("");
        last_name.setText("");
        age_field.setText("");
        house.setText("");
        road.setText("");
        zip.setText("");
        country.setText("");
        city.setText("");
        state.setText("");
        ssn_field.setText("");
        phone.setText("");
        email.setText("");
        area.setText("");
        sex_combobox.getSelectionModel().clearSelection();
        sex_combobox.setPromptText("Select Sex");
        marital_status.getSelectionModel().clearSelection();
        marital_status.setPromptText("Select Status");
        admission_date.setValue(null);
        dob.setValue(null);
        occupation_field.setText("");
        children_no.setText("");
        /*  */
    }

    private void pushNotification(String text) {
        Notifications not = Notifications.create();
        not.position(Pos.CENTER);
        not.hideAfter(Duration.seconds(2));
        not.text(text);
        not.title("Notifiction");
        not.show();
    }

    private void validationAlertBox(String title, String contentText) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.getDialogPane().setStyle(" -fx-font-weight: bold");
        alert.showAndWait();

    }
    private boolean chekcageField(){
    String regex = "\\d+";
    
    boolean b = age_field.getText().matches(regex);
//System.out.println(b);
    return b;
    }

}
