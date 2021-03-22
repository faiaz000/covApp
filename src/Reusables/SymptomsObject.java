/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reusables;

/**
 *
 * @author Faiaz Sharaf Uddin
 */
public class SymptomsObject {
   private String symptoms = "";
   private Integer frequency  = 0 ;

    public void setSymptoms(String sym) {
        this.symptoms = sym;
    }
    
    public String getSymptoms(){
    
        return symptoms;
    }
    
    public void setFrequency(Integer freq){
    
        this.frequency = freq;
    }
    public Integer getFrequency (){
        
        return frequency;
    }
    public SymptomsObject(String sym, Integer freq){
        
        this.frequency =freq;
        this.symptoms = sym;
    }
    
    
}
