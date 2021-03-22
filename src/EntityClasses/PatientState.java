/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityClasses;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Faiaz Sharaf Uddin
 */
@Entity
@Table(name = "patient_state")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PatientState.findAll", query = "SELECT p FROM PatientState p"),
    @NamedQuery(name = "PatientState.findById", query = "SELECT p FROM PatientState p WHERE p.id = :id"),
    @NamedQuery(name = "PatientState.findByCovid", query = "SELECT p FROM PatientState p WHERE p.covid = :covid"),
    @NamedQuery(name = "PatientState.findByTemperature", query = "SELECT p FROM PatientState p WHERE p.temperature = :temperature"),
    @NamedQuery(name = "PatientState.findByIncubation", query = "SELECT p FROM PatientState p WHERE p.incubation = :incubation"),
    @NamedQuery(name = "PatientState.findByIcu", query = "SELECT p FROM PatientState p WHERE p.icu = :icu"),
    @NamedQuery(name = "PatientState.findByVentilation", query = "SELECT p FROM PatientState p WHERE p.ventilation = :ventilation"),
    @NamedQuery(name = "PatientState.findByDate", query = "SELECT p FROM PatientState p WHERE p.date = :date"),
    @NamedQuery(name = "PatientState.findByCurrentState", query = "SELECT p FROM PatientState p WHERE p.currentState = :currentState"),
    @NamedQuery(name = "PatientState.findBySugarLevel", query = "SELECT p FROM PatientState p WHERE p.sugarLevel = :sugarLevel")})
public class PatientState implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "covid")
    private boolean covid;
    @Basic(optional = false)
    @Column(name = "temperature")
    private float temperature;
    @Basic(optional = false)
    @Column(name = "incubation")
    private boolean incubation;
    @Basic(optional = false)
    @Column(name = "icu")
    private boolean icu;
    @Basic(optional = false)
    @Column(name = "ventilation")
    private boolean ventilation;
    @Basic(optional = false)
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Basic(optional = false)
    @Column(name = "current_state")
    private String currentState;
    @Basic(optional = false)
    @Column(name = "sugar_level")
    private float sugarLevel;
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Patient patientId;

    public PatientState() {
    }

    public PatientState(Integer id) {
        this.id = id;
    }

    public PatientState(Integer id, boolean covid, float temperature, boolean incubation, boolean icu, boolean ventilation, Date date, String currentState, float sugarLevel) {
        this.id = id;
        this.covid = covid;
        this.temperature = temperature;
        this.incubation = incubation;
        this.icu = icu;
        this.ventilation = ventilation;
        this.date = date;
        this.currentState = currentState;
        this.sugarLevel = sugarLevel;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean getCovid() {
        return covid;
    }

    public void setCovid(boolean covid) {
        this.covid = covid;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public boolean getIncubation() {
        return incubation;
    }

    public void setIncubation(boolean incubation) {
        this.incubation = incubation;
    }

    public boolean getIcu() {
        return icu;
    }

    public void setIcu(boolean icu) {
        this.icu = icu;
    }

    public boolean getVentilation() {
        return ventilation;
    }

    public void setVentilation(boolean ventilation) {
        this.ventilation = ventilation;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public float getSugarLevel() {
        return sugarLevel;
    }

    public void setSugarLevel(float sugarLevel) {
        this.sugarLevel = sugarLevel;
    }

    public Patient getPatientId() {
        return patientId;
    }

    public void setPatientId(Patient patientId) {
        this.patientId = patientId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PatientState)) {
            return false;
        }
        PatientState other = (PatientState) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EntityClasses.PatientState[ id=" + id + " ]";
    }
    
}
