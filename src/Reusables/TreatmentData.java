/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reusables;

import EntityClasses.Treatment;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Faiaz Sharaf Uddin
 */
public class TreatmentData {

    public static List<Treatment> treatmentList = new ArrayList<Treatment>();
    public static boolean previousPrescription = false;

    public void setTreatmentLists(List<Treatment> prescriptions) {

        this.treatmentList = prescriptions;

    }

    public static List<Treatment> getTreatmentlist() {

        return treatmentList;
    }

    public void setPreviousPrescription(boolean exists) {
        previousPrescription = exists;
    }

    public static boolean getExistence() {

        return previousPrescription;
    }

}
