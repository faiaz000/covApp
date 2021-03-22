/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reusables;

import EntityClasses.Patient;

/**
 *
 * @author Faiaz Sharaf Uddin
 */
public class PatientData {

    private static Integer patientId;
    private static Integer d_patientId;
    private static Patient patient;
    private static boolean emergency;

    public void setId(Integer id) {

        PatientData.patientId = id;
        //patientId = id;
    }

    public static Integer getId() {

        return patientId;
    }

    public void setD_Id(Integer id) {

        PatientData.d_patientId = id;
        //patientId = id;
    }

    public static Integer getD_Id() {

        return d_patientId;
    }

    public void setPatient(Patient patientObject) {
        PatientData.patient = patientObject;
    }

    public static Patient getPatient() {
        return patient;
    }

    public void setTreatmentState(boolean emergency) {
        this.emergency = emergency;
    }
    
    public static boolean getTreatmentState(){
    
        return emergency;
    }
}
