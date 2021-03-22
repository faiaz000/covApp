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
@Table(name = "medical_history")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MedicalHistory.findAll", query = "SELECT m FROM MedicalHistory m"),
    @NamedQuery(name = "MedicalHistory.findById", query = "SELECT m FROM MedicalHistory m WHERE m.id = :id"),
    @NamedQuery(name = "MedicalHistory.findByDiabetes", query = "SELECT m FROM MedicalHistory m WHERE m.diabetes = :diabetes"),
    @NamedQuery(name = "MedicalHistory.findByCopd", query = "SELECT m FROM MedicalHistory m WHERE m.copd = :copd"),
    @NamedQuery(name = "MedicalHistory.findByCysticFibrosis", query = "SELECT m FROM MedicalHistory m WHERE m.cysticFibrosis = :cysticFibrosis"),
    @NamedQuery(name = "MedicalHistory.findByPulmonaryFibrosis", query = "SELECT m FROM MedicalHistory m WHERE m.pulmonaryFibrosis = :pulmonaryFibrosis"),
    @NamedQuery(name = "MedicalHistory.findByHypertension", query = "SELECT m FROM MedicalHistory m WHERE m.hypertension = :hypertension"),
    @NamedQuery(name = "MedicalHistory.findByCerebrovascularDisease", query = "SELECT m FROM MedicalHistory m WHERE m.cerebrovascularDisease = :cerebrovascularDisease"),
    @NamedQuery(name = "MedicalHistory.findByCardiovascularDisease", query = "SELECT m FROM MedicalHistory m WHERE m.cardiovascularDisease = :cardiovascularDisease"),
    @NamedQuery(name = "MedicalHistory.findByHepatitisB", query = "SELECT m FROM MedicalHistory m WHERE m.hepatitisB = :hepatitisB"),
    @NamedQuery(name = "MedicalHistory.findByMalignancy", query = "SELECT m FROM MedicalHistory m WHERE m.malignancy = :malignancy"),
    @NamedQuery(name = "MedicalHistory.findByChronicKidneyDisease", query = "SELECT m FROM MedicalHistory m WHERE m.chronicKidneyDisease = :chronicKidneyDisease"),
    @NamedQuery(name = "MedicalHistory.findByImmunodeficiency", query = "SELECT m FROM MedicalHistory m WHERE m.immunodeficiency = :immunodeficiency"),
    @NamedQuery(name = "MedicalHistory.findByHighCholesterol", query = "SELECT m FROM MedicalHistory m WHERE m.highCholesterol = :highCholesterol"),
    @NamedQuery(name = "MedicalHistory.findByNeurologicDisease", query = "SELECT m FROM MedicalHistory m WHERE m.neurologicDisease = :neurologicDisease"),
    @NamedQuery(name = "MedicalHistory.findByBloodDisorder", query = "SELECT m FROM MedicalHistory m WHERE m.bloodDisorder = :bloodDisorder"),
    @NamedQuery(name = "MedicalHistory.findByCancer", query = "SELECT m FROM MedicalHistory m WHERE m.cancer = :cancer"),
    @NamedQuery(name = "MedicalHistory.findByObesity", query = "SELECT m FROM MedicalHistory m WHERE m.obesity = :obesity"),
    @NamedQuery(name = "MedicalHistory.findByAlcohol", query = "SELECT m FROM MedicalHistory m WHERE m.alcohol = :alcohol"),
    @NamedQuery(name = "MedicalHistory.findByTobacco", query = "SELECT m FROM MedicalHistory m WHERE m.tobacco = :tobacco"),
    @NamedQuery(name = "MedicalHistory.findByDrugs", query = "SELECT m FROM MedicalHistory m WHERE m.drugs = :drugs"),
    @NamedQuery(name = "MedicalHistory.findByHeight", query = "SELECT m FROM MedicalHistory m WHERE m.height = :height"),
    @NamedQuery(name = "MedicalHistory.findByWeight", query = "SELECT m FROM MedicalHistory m WHERE m.weight = :weight"),
    @NamedQuery(name = "MedicalHistory.findByAsthma", query = "SELECT m FROM MedicalHistory m WHERE m.asthma = :asthma")})
public class MedicalHistory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "diabetes")
    private boolean diabetes;
    @Basic(optional = false)
    @Column(name = "copd")
    private boolean copd;
    @Basic(optional = false)
    @Column(name = "cystic_fibrosis")
    private boolean cysticFibrosis;
    @Basic(optional = false)
    @Column(name = "pulmonary_fibrosis")
    private boolean pulmonaryFibrosis;
    @Basic(optional = false)
    @Column(name = "hypertension")
    private boolean hypertension;
    @Basic(optional = false)
    @Column(name = "cerebrovascular_disease")
    private boolean cerebrovascularDisease;
    @Basic(optional = false)
    @Column(name = "cardiovascular_disease")
    private boolean cardiovascularDisease;
    @Basic(optional = false)
    @Column(name = "hepatitis_b")
    private boolean hepatitisB;
    @Basic(optional = false)
    @Column(name = "malignancy")
    private boolean malignancy;
    @Basic(optional = false)
    @Column(name = "chronic_kidney_disease")
    private boolean chronicKidneyDisease;
    @Basic(optional = false)
    @Column(name = "immunodeficiency")
    private boolean immunodeficiency;
    @Basic(optional = false)
    @Column(name = "high_cholesterol")
    private boolean highCholesterol;
    @Basic(optional = false)
    @Column(name = "neurologic_disease")
    private boolean neurologicDisease;
    @Basic(optional = false)
    @Column(name = "blood_disorder")
    private boolean bloodDisorder;
    @Basic(optional = false)
    @Column(name = "cancer")
    private boolean cancer;
    @Basic(optional = false)
    @Column(name = "obesity")
    private boolean obesity;
    @Basic(optional = false)
    @Column(name = "alcohol")
    private boolean alcohol;
    @Basic(optional = false)
    @Column(name = "tobacco")
    private boolean tobacco;
    @Basic(optional = false)
    @Column(name = "drugs")
    private boolean drugs;
    @Basic(optional = false)
    @Column(name = "height")
    private float height;
    @Basic(optional = false)
    @Column(name = "weight")
    private float weight;
    @Basic(optional = false)
    @Column(name = "asthma")
    private boolean asthma;
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Patient patientId;

    public MedicalHistory() {
    }

    public MedicalHistory(Integer id) {
        this.id = id;
    }

    public MedicalHistory(Integer id, boolean diabetes, boolean copd, boolean cysticFibrosis, boolean pulmonaryFibrosis, boolean hypertension, boolean cerebrovascularDisease, boolean cardiovascularDisease, boolean hepatitisB, boolean malignancy, boolean chronicKidneyDisease, boolean immunodeficiency, boolean highCholesterol, boolean neurologicDisease, boolean bloodDisorder, boolean cancer, boolean obesity, boolean alcohol, boolean tobacco, boolean drugs, float height, float weight, boolean asthma) {
        this.id = id;
        this.diabetes = diabetes;
        this.copd = copd;
        this.cysticFibrosis = cysticFibrosis;
        this.pulmonaryFibrosis = pulmonaryFibrosis;
        this.hypertension = hypertension;
        this.cerebrovascularDisease = cerebrovascularDisease;
        this.cardiovascularDisease = cardiovascularDisease;
        this.hepatitisB = hepatitisB;
        this.malignancy = malignancy;
        this.chronicKidneyDisease = chronicKidneyDisease;
        this.immunodeficiency = immunodeficiency;
        this.highCholesterol = highCholesterol;
        this.neurologicDisease = neurologicDisease;
        this.bloodDisorder = bloodDisorder;
        this.cancer = cancer;
        this.obesity = obesity;
        this.alcohol = alcohol;
        this.tobacco = tobacco;
        this.drugs = drugs;
        this.height = height;
        this.weight = weight;
        this.asthma = asthma;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean getDiabetes() {
        return diabetes;
    }

    public void setDiabetes(boolean diabetes) {
        this.diabetes = diabetes;
    }

    public boolean getCopd() {
        return copd;
    }

    public void setCopd(boolean copd) {
        this.copd = copd;
    }

    public boolean getCysticFibrosis() {
        return cysticFibrosis;
    }

    public void setCysticFibrosis(boolean cysticFibrosis) {
        this.cysticFibrosis = cysticFibrosis;
    }

    public boolean getPulmonaryFibrosis() {
        return pulmonaryFibrosis;
    }

    public void setPulmonaryFibrosis(boolean pulmonaryFibrosis) {
        this.pulmonaryFibrosis = pulmonaryFibrosis;
    }

    public boolean getHypertension() {
        return hypertension;
    }

    public void setHypertension(boolean hypertension) {
        this.hypertension = hypertension;
    }

    public boolean getCerebrovascularDisease() {
        return cerebrovascularDisease;
    }

    public void setCerebrovascularDisease(boolean cerebrovascularDisease) {
        this.cerebrovascularDisease = cerebrovascularDisease;
    }

    public boolean getCardiovascularDisease() {
        return cardiovascularDisease;
    }

    public void setCardiovascularDisease(boolean cardiovascularDisease) {
        this.cardiovascularDisease = cardiovascularDisease;
    }

    public boolean getHepatitisB() {
        return hepatitisB;
    }

    public void setHepatitisB(boolean hepatitisB) {
        this.hepatitisB = hepatitisB;
    }

    public boolean getMalignancy() {
        return malignancy;
    }

    public void setMalignancy(boolean malignancy) {
        this.malignancy = malignancy;
    }

    public boolean getChronicKidneyDisease() {
        return chronicKidneyDisease;
    }

    public void setChronicKidneyDisease(boolean chronicKidneyDisease) {
        this.chronicKidneyDisease = chronicKidneyDisease;
    }

    public boolean getImmunodeficiency() {
        return immunodeficiency;
    }

    public void setImmunodeficiency(boolean immunodeficiency) {
        this.immunodeficiency = immunodeficiency;
    }

    public boolean getHighCholesterol() {
        return highCholesterol;
    }

    public void setHighCholesterol(boolean highCholesterol) {
        this.highCholesterol = highCholesterol;
    }

    public boolean getNeurologicDisease() {
        return neurologicDisease;
    }

    public void setNeurologicDisease(boolean neurologicDisease) {
        this.neurologicDisease = neurologicDisease;
    }

    public boolean getBloodDisorder() {
        return bloodDisorder;
    }

    public void setBloodDisorder(boolean bloodDisorder) {
        this.bloodDisorder = bloodDisorder;
    }

    public boolean getCancer() {
        return cancer;
    }

    public void setCancer(boolean cancer) {
        this.cancer = cancer;
    }

    public boolean getObesity() {
        return obesity;
    }

    public void setObesity(boolean obesity) {
        this.obesity = obesity;
    }

    public boolean getAlcohol() {
        return alcohol;
    }

    public void setAlcohol(boolean alcohol) {
        this.alcohol = alcohol;
    }

    public boolean getTobacco() {
        return tobacco;
    }

    public void setTobacco(boolean tobacco) {
        this.tobacco = tobacco;
    }

    public boolean getDrugs() {
        return drugs;
    }

    public void setDrugs(boolean drugs) {
        this.drugs = drugs;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public boolean getAsthma() {
        return asthma;
    }

    public void setAsthma(boolean asthma) {
        this.asthma = asthma;
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
        if (!(object instanceof MedicalHistory)) {
            return false;
        }
        MedicalHistory other = (MedicalHistory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EntityClasses.MedicalHistory[ id=" + id + " ]";
    }
    
}
