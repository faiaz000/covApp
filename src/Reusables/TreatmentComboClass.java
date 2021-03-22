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
public class TreatmentComboClass {
 private int id;
private String dateString;

public TreatmentComboClass(int id, String dateString) {
    super();
    this.id = id;
    this.dateString = dateString;
}

public int getId() {
    return id;
}

public void setId(int id) {
    this.id = id;
}

public String getDateString() {
    return dateString;
}

public void setDateString(String dateString) {
    this.dateString = dateString;
}
@Override
public String toString() {
    return this.dateString;
}
}
