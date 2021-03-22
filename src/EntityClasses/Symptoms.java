/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityClasses;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Faiaz Sharaf Uddin
 */
@Entity
@Table(name = "symptoms")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Symptoms.findAll", query = "SELECT s FROM Symptoms s"),
    @NamedQuery(name = "Symptoms.findBySymptomsId", query = "SELECT s FROM Symptoms s WHERE s.symptomsId = :symptomsId"),
    @NamedQuery(name = "Symptoms.findByFever", query = "SELECT s FROM Symptoms s WHERE s.fever = :fever"),
    @NamedQuery(name = "Symptoms.findByConjunctivalCongestion", query = "SELECT s FROM Symptoms s WHERE s.conjunctivalCongestion = :conjunctivalCongestion"),
    @NamedQuery(name = "Symptoms.findByNasalCongestion", query = "SELECT s FROM Symptoms s WHERE s.nasalCongestion = :nasalCongestion"),
    @NamedQuery(name = "Symptoms.findByHeadache", query = "SELECT s FROM Symptoms s WHERE s.headache = :headache"),
    @NamedQuery(name = "Symptoms.findByDryCough", query = "SELECT s FROM Symptoms s WHERE s.dryCough = :dryCough"),
    @NamedQuery(name = "Symptoms.findByPharyngodynia", query = "SELECT s FROM Symptoms s WHERE s.pharyngodynia = :pharyngodynia"),
    @NamedQuery(name = "Symptoms.findByProductiveCough", query = "SELECT s FROM Symptoms s WHERE s.productiveCough = :productiveCough"),
    @NamedQuery(name = "Symptoms.findByHemoptysis", query = "SELECT s FROM Symptoms s WHERE s.hemoptysis = :hemoptysis"),
    @NamedQuery(name = "Symptoms.findByFatigue", query = "SELECT s FROM Symptoms s WHERE s.fatigue = :fatigue"),
    @NamedQuery(name = "Symptoms.findByBreatheShortness", query = "SELECT s FROM Symptoms s WHERE s.breatheShortness = :breatheShortness"),
    @NamedQuery(name = "Symptoms.findByDiarrhea", query = "SELECT s FROM Symptoms s WHERE s.diarrhea = :diarrhea"),
    @NamedQuery(name = "Symptoms.findByNausea", query = "SELECT s FROM Symptoms s WHERE s.nausea = :nausea"),
    @NamedQuery(name = "Symptoms.findByMyalgia", query = "SELECT s FROM Symptoms s WHERE s.myalgia = :myalgia"),
    @NamedQuery(name = "Symptoms.findByArthalgia", query = "SELECT s FROM Symptoms s WHERE s.arthalgia = :arthalgia"),
    @NamedQuery(name = "Symptoms.findByChills", query = "SELECT s FROM Symptoms s WHERE s.chills = :chills"),
    @NamedQuery(name = "Symptoms.findByChestPain", query = "SELECT s FROM Symptoms s WHERE s.chestPain = :chestPain")})
public class Symptoms implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "symptoms_id")
    private Integer symptomsId;
    @Basic(optional = false)
    @Column(name = "fever")
    private boolean fever;
    @Basic(optional = false)
    @Column(name = "conjunctival_congestion")
    private boolean conjunctivalCongestion;
    @Basic(optional = false)
    @Column(name = "nasal_congestion")
    private boolean nasalCongestion;
    @Basic(optional = false)
    @Column(name = "headache")
    private boolean headache;
    @Basic(optional = false)
    @Column(name = "dry_cough")
    private boolean dryCough;
    @Basic(optional = false)
    @Column(name = "pharyngodynia")
    private boolean pharyngodynia;
    @Basic(optional = false)
    @Column(name = "productive_cough")
    private boolean productiveCough;
    @Basic(optional = false)
    @Column(name = "hemoptysis")
    private boolean hemoptysis;
    @Basic(optional = false)
    @Column(name = "fatigue")
    private boolean fatigue;
    @Basic(optional = false)
    @Column(name = "breathe_shortness")
    private boolean breatheShortness;
    @Basic(optional = false)
    @Column(name = "diarrhea")
    private boolean diarrhea;
    @Basic(optional = false)
    @Column(name = "nausea")
    private boolean nausea;
    @Basic(optional = false)
    @Column(name = "myalgia")
    private boolean myalgia;
    @Basic(optional = false)
    @Column(name = "arthalgia")
    private boolean arthalgia;
    @Basic(optional = false)
    @Column(name = "chills")
    private boolean chills;
    @Basic(optional = false)
    @Column(name = "chest_pain")
    private boolean chestPain;
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Patient patientId;

    public Symptoms() {
    }

    public Symptoms(Integer symptomsId) {
        this.symptomsId = symptomsId;
    }

    public Symptoms(Integer symptomsId, boolean fever, boolean conjunctivalCongestion, boolean nasalCongestion, boolean headache, boolean dryCough, boolean pharyngodynia, boolean productiveCough, boolean hemoptysis, boolean fatigue, boolean breatheShortness, boolean diarrhea, boolean nausea, boolean myalgia, boolean arthalgia, boolean chills, boolean chestPain) {
        this.symptomsId = symptomsId;
        this.fever = fever;
        this.conjunctivalCongestion = conjunctivalCongestion;
        this.nasalCongestion = nasalCongestion;
        this.headache = headache;
        this.dryCough = dryCough;
        this.pharyngodynia = pharyngodynia;
        this.productiveCough = productiveCough;
        this.hemoptysis = hemoptysis;
        this.fatigue = fatigue;
        this.breatheShortness = breatheShortness;
        this.diarrhea = diarrhea;
        this.nausea = nausea;
        this.myalgia = myalgia;
        this.arthalgia = arthalgia;
        this.chills = chills;
        this.chestPain = chestPain;
    }

    public Integer getSymptomsId() {
        return symptomsId;
    }

    public void setSymptomsId(Integer symptomsId) {
        this.symptomsId = symptomsId;
    }

    public boolean getFever() {
        return fever;
    }

    public void setFever(boolean fever) {
        this.fever = fever;
    }

    public boolean getConjunctivalCongestion() {
        return conjunctivalCongestion;
    }

    public void setConjunctivalCongestion(boolean conjunctivalCongestion) {
        this.conjunctivalCongestion = conjunctivalCongestion;
    }

    public boolean getNasalCongestion() {
        return nasalCongestion;
    }

    public void setNasalCongestion(boolean nasalCongestion) {
        this.nasalCongestion = nasalCongestion;
    }

    public boolean getHeadache() {
        return headache;
    }

    public void setHeadache(boolean headache) {
        this.headache = headache;
    }

    public boolean getDryCough() {
        return dryCough;
    }

    public void setDryCough(boolean dryCough) {
        this.dryCough = dryCough;
    }

    public boolean getPharyngodynia() {
        return pharyngodynia;
    }

    public void setPharyngodynia(boolean pharyngodynia) {
        this.pharyngodynia = pharyngodynia;
    }

    public boolean getProductiveCough() {
        return productiveCough;
    }

    public void setProductiveCough(boolean productiveCough) {
        this.productiveCough = productiveCough;
    }

    public boolean getHemoptysis() {
        return hemoptysis;
    }

    public void setHemoptysis(boolean hemoptysis) {
        this.hemoptysis = hemoptysis;
    }

    public boolean getFatigue() {
        return fatigue;
    }

    public void setFatigue(boolean fatigue) {
        this.fatigue = fatigue;
    }

    public boolean getBreatheShortness() {
        return breatheShortness;
    }

    public void setBreatheShortness(boolean breatheShortness) {
        this.breatheShortness = breatheShortness;
    }

    public boolean getDiarrhea() {
        return diarrhea;
    }

    public void setDiarrhea(boolean diarrhea) {
        this.diarrhea = diarrhea;
    }

    public boolean getNausea() {
        return nausea;
    }

    public void setNausea(boolean nausea) {
        this.nausea = nausea;
    }

    public boolean getMyalgia() {
        return myalgia;
    }

    public void setMyalgia(boolean myalgia) {
        this.myalgia = myalgia;
    }

    public boolean getArthalgia() {
        return arthalgia;
    }

    public void setArthalgia(boolean arthalgia) {
        this.arthalgia = arthalgia;
    }

    public boolean getChills() {
        return chills;
    }

    public void setChills(boolean chills) {
        this.chills = chills;
    }

    public boolean getChestPain() {
        return chestPain;
    }

    public void setChestPain(boolean chestPain) {
        this.chestPain = chestPain;
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
        hash += (symptomsId != null ? symptomsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Symptoms)) {
            return false;
        }
        Symptoms other = (Symptoms) object;
        if ((this.symptomsId == null && other.symptomsId != null) || (this.symptomsId != null && !this.symptomsId.equals(other.symptomsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EntityClasses.Symptoms[ symptomsId=" + symptomsId + " ]";
    }
    
}
