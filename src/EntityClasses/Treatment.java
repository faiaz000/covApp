/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityClasses;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Faiaz Sharaf Uddin
 */
@Entity
@Table(name = "treatment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Treatment.findAll", query = "SELECT t FROM Treatment t"),
    @NamedQuery(name = "Treatment.findById", query = "SELECT t FROM Treatment t WHERE t.id = :id"),
    @NamedQuery(name = "Treatment.findByDate", query = "SELECT t FROM Treatment t WHERE t.date = :date"),
    @NamedQuery(name = "Treatment.findByComments", query = "SELECT t FROM Treatment t WHERE t.comments = :comments"),
    @NamedQuery(name = "Treatment.findByEtreatment", query = "SELECT t FROM Treatment t WHERE t.etreatment = :etreatment")})
public class Treatment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Basic(optional = false)
    @Column(name = "comments")
    private String comments;
    @Basic(optional = false)
    @Column(name = "etreatment")
    private boolean etreatment;
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Patient patientId;
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Users doctorId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "treatmentId")
    private Collection<Medicine> medicineCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "treatmentId")
    private Collection<MedicalTests> medicalTestsCollection;

    public Treatment() {
    }

    public Treatment(Integer id) {
        this.id = id;
    }

    public Treatment(Integer id, Date date, String comments, boolean etreatment) {
        this.id = id;
        this.date = date;
        this.comments = comments;
        this.etreatment = etreatment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public boolean getEtreatment() {
        return etreatment;
    }

    public void setEtreatment(boolean etreatment) {
        this.etreatment = etreatment;
    }

    public Patient getPatientId() {
        return patientId;
    }

    public void setPatientId(Patient patientId) {
        this.patientId = patientId;
    }

    public Users getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Users doctorId) {
        this.doctorId = doctorId;
    }

    @XmlTransient
    public Collection<Medicine> getMedicineCollection() {
        return medicineCollection;
    }

    public void setMedicineCollection(Collection<Medicine> medicineCollection) {
        this.medicineCollection = medicineCollection;
    }

    @XmlTransient
    public Collection<MedicalTests> getMedicalTestsCollection() {
        return medicalTestsCollection;
    }

    public void setMedicalTestsCollection(Collection<MedicalTests> medicalTestsCollection) {
        this.medicalTestsCollection = medicalTestsCollection;
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
        if (!(object instanceof Treatment)) {
            return false;
        }
        Treatment other = (Treatment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EntityClasses.Treatment[ id=" + id + " ]";
    }
    
}
