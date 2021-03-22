/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import EntityClasses.Outcome;
import EntityClasses.PatientState;

/**
 *
 * @author Faiaz Sharaf Uddin
 */
public class StatusObject {
   private static PatientState status = new PatientState();
   private  static Outcome outcome = new Outcome();
    
   void setPatientStus(PatientState state){
        StatusObject.status = state;
    }
    
   public static PatientState getPatientStatus(){
    
        return status; 
    }
   void setPatientOutcome(Outcome outcomeObject){
        StatusObject.outcome= outcomeObject;
    }
   public static Outcome getPatientOutcome(){
    
        return outcome; 
    }
   
}
