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
public class TestObject {
    private String testName;
    public TestObject(String name) {
        this.testName = name;
        
    }
    public TestObject() {
  
    }
    
     public void setTestName(String name){
        testName = name;
    } 
    public String getTestName(){
       return testName;
    }
}
