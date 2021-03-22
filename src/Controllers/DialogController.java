/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import EntityClasses.Outcome;
import Reusables.SharedData;
import EntityClasses.PatientState;
import EntityClasses.Users;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 *
 * @author Faiaz Sharaf Uddin
 */
public class DialogController implements Initializable {

    @FXML
    private Label dlg_covid;
    
    @FXML 
    private Label user_name;

    @FXML
    private Label dlg_current_state;

    @FXML
    private Label dlg_icu;

    @FXML
    private Label dlg_incubation;

    @FXML
    private Label dlg_sugar;

    @FXML
    private Label dlg_temperature;

    @FXML
    private Label dlg_ventilation;
    
    // Outcome
    @FXML
    private Label dlg_ards;

    @FXML
    private Label dlg_cardiac;

    @FXML
    private Label dlg_heart;

    @FXML
    private Label dlg_pneumonia;

    @FXML
    private Label dlg_sInfection;

    @FXML
    private Label dlg_stroke;
    @FXML 
    private Label role, user_id, department,user_email,user_phone;
            
    
    String covidStatus, icu, ventilation, sugar, temperature, incubation, currentState;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
        if(SharedData.getModal().matches("Status")){
            setPatientState();
        }
        else if(SharedData.getModal().matches("Admin")){
            setUsers();
        }
        else if (SharedData.getModal().matches("Outcome")){
             setOutcome();
        }
        
    }
    
    private void setPatientState(){
      
        PatientState patientState = StatusObject.getPatientStatus();

        covidStatus = patientState.getCovid() ? "Positive" : "Negative";
        dlg_covid.setText(covidStatus);
        incubation = patientState.getIncubation()? "Yes" : "No";
        dlg_incubation.setText(incubation);
        icu = patientState.getIcu()? "Yes" : "NO";
        dlg_icu.setText(icu);
        ventilation = patientState.getVentilation()? "Yes" : "No";
        dlg_ventilation.setText(ventilation);
        sugar = String.valueOf(patientState.getSugarLevel());
        dlg_sugar.setText(sugar);
        temperature = String.valueOf(patientState.getTemperature());
        dlg_temperature.setText(temperature);
        currentState = patientState.getCurrentState();
        dlg_current_state.setText(currentState);
    }
    
    private void setOutcome(){
        String ards , cardiac , heart, sInfection, stroke, pneumonia;
        
        Outcome outcome = StatusObject.getPatientOutcome();
        
        ards = outcome.getArds() ? "YES" : "NO";
        dlg_ards.setText(ards);
        cardiac = outcome.getCardiacArrest() ? "YES" : "NO";
        dlg_cardiac.setText(cardiac);
        pneumonia = outcome.getPneumonia()? "YES" : "NO";
        dlg_pneumonia.setText(pneumonia);
        heart = outcome.getHeartAttack()? "YES" : "NO";
        dlg_heart.setText(heart);
        sInfection = outcome.getSecondaryInfection()? "YES" : "NO";
        dlg_sInfection.setText(sInfection);
        stroke = outcome.getStroke()? "YES" : "NO";
        dlg_stroke.setText(stroke);
    
    }
    
    private void setUsers(){
        Users users = new Users();
        users = SharedData.getUserObject();
        
        user_name.setText(users.getFullName());
        user_id.setText(users.getUserid());
        role.setText(users.getRole());
        department.setText(users.getDepartment());
        user_email.setText(users.getEmail());
        user_phone.setText(users.getPhone());
    
    }
}
