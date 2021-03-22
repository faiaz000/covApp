/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reusables;

import EntityClasses.Medicine;
import java.util.ArrayList;
import java.util.Observable;

import javafx.collections.ObservableList;


/**
 *
 * @author Faiaz Sharaf Uddin
 */
public class MedicineObject {
    private String medicineName;
    private String medicineType;
    private String medicineAmount;
    private int medicineDuration;
    private boolean morning;
    private boolean noon;
    private boolean night;

    public MedicineObject(String name, String type,String amount, int duration, boolean morning, boolean noon, boolean night) {
        this.medicineName = name;
        this.medicineType = type;
        this.medicineAmount = amount;
        this.medicineDuration = duration;
        this.morning = morning;
        this.night = night;
        this.noon = noon;
    }
    public MedicineObject() {
  
    }
    
   
    
    public void setMedicineName(String name){
        medicineName = name;
    } 
    public void setMedicineType(String type){
        medicineType = type;
    }
     
    public void setMedicineAmount(String amount){
        medicineAmount = amount;
    }
    
    public void setMedicineDuration(int duration){
        medicineDuration = duration;
    }
    
    public void setMorning(boolean day){
       morning = day;
    }
    public void setNoon(boolean v_noon){
       noon = v_noon;
    }
     public void setNight(boolean v_night){
       noon = v_night;
    }
     
     public String getMedicineName(){
         return medicineName;
     }
     public String getMedicineType(){
         return medicineType;
     }
     public String getMedicineAmount(){
         return medicineAmount;
     }
     public int getMedicineDuration(){
         return medicineDuration;
     }
     public boolean getMorning(){
         return morning;
     }
     public boolean getNoon(){
         return noon;
     }
     public boolean getNight(){
         return night;
     }
     
    
}
